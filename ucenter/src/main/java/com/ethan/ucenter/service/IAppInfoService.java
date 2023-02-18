package com.ethan.ucenter.service;

import com.ethan.common.response.ResponseResult;
import com.ethan.ucenter.pojo.po.AppInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ucenter.pojo.vo.AppInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
public interface IAppInfoService extends IService<AppInfo> {

    ResponseResult register(String token, AppInfoVO appInfoVO);

    ResponseResult getUID(String token);
}
