package com.ethan.ucenter.service.impl;

import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.RedisUtil;
import com.ethan.ucenter.config.BaseConfig;
import com.ethan.ucenter.config.UCThreadPool;
import com.ethan.ucenter.service.ICheckService;
import com.ethan.ucenter.service.ISendService;
import com.ethan.common.utils.ELog;
import com.ethan.ucenter.utils.EmailSender;
import com.ethan.common.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * 业务 Service
 * 非数据 Service
 *
 * @author Ethan 2023/2/12
 */
@Service
public class SendServiceImpl implements ISendService {

    @Autowired
    ICheckService mCheckService;
    @Autowired
    private RedisUtil mRedisUtil;

    /**
     * 异步发送邮件
     * 带 @Async 注解的方法会被发送到线程池执行，但要求必须在当前类SendServiceImpl外部调用
     */
    @Override
    @Async("APool")
    public Future<ResponseResult> sendEmailCode(String email, int type, HttpServletRequest request) {

        ELog.INFO("进入异步线程");

        // 校验邮箱合法性
        if (TextUtil.isEmpty(email)) {
            return UCThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.MSG_CODE_SEND_FAILED.setMsg("邮箱为空")));
        }
        if (!TextUtil.isEmail(email)) {
            return UCThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.MSG_CODE_SEND_FAILED.setMsg("邮箱格式非法")));
        }
        // 邮箱判重
        if (!BaseConfig.EMAIL_LIMIT_SWITCH && mCheckService.checkEmail(email)) {
            return UCThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.EMAIL_EXIST));
        }

        // IP 防御：获取用户 IP，防止某个 IP 恶意狂刷
        if (BaseConfig.IP_DEFENSE_SWITCH) {
            String remoteAddr = request.getRemoteAddr();
            ELog.INFO("请求 IP " + remoteAddr);
            String ipKey = BaseConfig.REDIS_EMAIL_CODE_IP_PREFIX + remoteAddr;
            String ipTimes = (String) mRedisUtil.get(ipKey);
            if (!TextUtil.isEmpty(ipTimes)) {
                int times = Integer.parseInt(ipTimes);
                if (times > BaseConfig.REDIS_EMAIL_CODE_IP_TIMES) {
                    return UCThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.MSG_CODE_SEND_TOO_MUCH));
                } else {
                    mRedisUtil.set(ipKey, String.valueOf(++times), BaseConfig.REDIS_EMAIL_CODE_IP_EXPIRE);
                }
            } else {
                mRedisUtil.set(ipKey, "1", BaseConfig.REDIS_EMAIL_CODE_IP_EXPIRE);
            }
        }

        // 生成验证码并存入 Redis
        Random random = new Random();
        int verifyCode = random.nextInt(BaseConfig.EMAIL_VERIFY_CODE_MAX);
        if (verifyCode < 100000) {
            verifyCode += 100000;
        }
        if (type == BaseConfig.EMAIL_TYPE_SIGNIN) {
            mRedisUtil.set(BaseConfig.REDIS_SIGNIN_EMAIL_CODE_PREFIX + email, String.valueOf(verifyCode), BaseConfig.REDIS_EMAIL_CODE_EXPIRE);
        } else if (type == BaseConfig.EMAIL_TYPE_PWD) {
            mRedisUtil.set(BaseConfig.REDIS_PWD_EMAIL_CODE_PREFIX + email, String.valueOf(verifyCode), BaseConfig.REDIS_EMAIL_CODE_EXPIRE);
        }
        ELog.DEBUG("后台生成验证码 " + verifyCode);

        // 发送邮件
        // 必须先调用 subject()，因为在 subject() 里创建的对象
        if (BaseConfig.EMAIL_SWITCH) {
            try {
                EmailSender.subject(BaseConfig.EMAIL_TITLE)
                        .from(BaseConfig.EMAIL_FROM)
                        .html(generateEmailBody(email, verifyCode))
                        .to(email)
                        .send();
            } catch (MessagingException e) {
                return UCThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.MSG_CODE_SEND_FAILED));
            }
        }
        return UCThreadPool.asyncResultWrapper(new ResponseResult(ResponseState.MSG_CODE_SEND_SUCCESS));
    }

    private String generateEmailBody(String email, int verifyCode) {
        return String.format(BaseConfig.EMAIL_BODY, email, verifyCode);
    }
}
