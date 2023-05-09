package com.ethan.qa.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.CookieUtil;
import com.ethan.common.utils.ELog;
import com.ethan.qa.config.GlobalConfig;
import com.ethan.qa.config.ThreadPool;
import com.ethan.qa.utils.OkHttpUtil;
import com.google.gson.Gson;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Ethan 2023/2/26
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 获取请求实体
     */
    protected HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

    /**
     * 获取响应实体
     */
    protected HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getResponse();
    }

    /**
     * 远程 Token ---> UID 转换
     *
     * @return 发送失败、无响应、账号未登录：返回指定错误码；<br>获取成功：返回 UID
     */
    protected ResponseResult T2U() {
        String token = CookieUtil.getCookie(getRequest(), GlobalConfig.TOKEN);
        return UCProxy(token, GlobalConfig.UC_T2U);
    }

    /**
     * 远程获取用户信息
     *
     * @return 发送失败、无响应、账号未登录：返回指定错误码；<br>获取成功：返回信息
     */
    protected ResponseResult UCInfo() {
        String token = CookieUtil.getCookie(getRequest(), GlobalConfig.TOKEN);
        return UCProxy(token, GlobalConfig.UC_INFO);
    }

    /**
     * 远程判断用户是否为应用管理员
     *
     * @return 发送失败、无响应、账号未登录：返回指定错误码；<br>获取成功：返回信息
     */
    protected ResponseResult isAdmin() {
        String token = CookieUtil.getCookie(getRequest(), GlobalConfig.TOKEN);
        return UCProxy(token, GlobalConfig.UC_IS_ADMIN);
    }

    /**
     * 远程获取用户名
     *
     * @return 发送失败、无响应、账号未登录：返回指定错误码；<br>获取成功：返回信息
     */
    protected ResponseResult getUserName(Long uid) {
        return UCProxy(String.valueOf(uid), GlobalConfig.UC_GET_NAME);
    }

    /**
     * 代理转换，解决同类或子类中调用 @Async 方法失效的问题
     */
    private ResponseResult UCProxy(String param, int type) {
        // 使用代理类
        QuestionServiceImpl service = applicationContext.getBean(QuestionServiceImpl.class);
        try {
            // 结果获取与处理
            String url = "";
            switch (type) {
                case GlobalConfig.UC_T2U:
                    url = GlobalConfig.UC_TOKEN2UID_URL + GlobalConfig.URL_SEPARATOR + "token=" + param;
                    break;
                case GlobalConfig.UC_INFO:
                    url = GlobalConfig.UC_INFO_URL + GlobalConfig.URL_SEPARATOR + "token=" + param;
                    break;
                case GlobalConfig.UC_IS_ADMIN:
                    url = GlobalConfig.UC_IS_ADMIN_URL + GlobalConfig.URL_SEPARATOR + "token=" + param;
                    break;
                case GlobalConfig.UC_GET_NAME:
                    url = GlobalConfig.UC_GET_NAME_URL + param;
                    break;
            }
            return service.UCCore(url).get();
        } catch (InterruptedException | ExecutionException e) {
            ELog.ERROR("异步结果异常");
            e.printStackTrace();
            return new ResponseResult(ResponseState.NETWORK_ERROR);
        }
    }

    /**
     * 负责向统一用户中心请求
     * 异步是为了防止业务主线程卡死，影响接收请求
     */
    @Async("APool")
    protected Future<ResponseResult> UCCore(String url) {
        ELog.DEBUG("执行线程");
        Response response = OkHttpUtil.GETSync(url);
        if (response == null) {
            ELog.ERROR("发送请求失败");
            return ThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.NETWORK_ERROR));
        }
        if (response.body() == null) {
            ELog.ERROR("响应体为空");
            return ThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.UC_SERVICE_ERROR));
        }

        // 成功获取响应后还有有效和无效两种情形，这里直接抛回，由调用方处理（这就是统一使用 ResponseResult 的好处！！！）
        String result = null;
        try {
            result = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        ResponseResult responseResult = gson.fromJson(result, ResponseResult.class);
        return ThreadPool.asyncResultWrapper(responseResult);
    }
}
