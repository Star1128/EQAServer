package com.ethan.ucenter.pojo.vo;

import com.ethan.ucenter.pojo.po.AppInfo;
import lombok.Data;

/**
 * @author Ethan 2023/2/16
 */
@Data
public class AppInfoVO {
    private String appName;
    private Integer appType;
    private String callbackUrl;
    private String appIcon;
    private String appDes;

    public AppInfo toAppInfo(long uid) {
        AppInfo info = new AppInfo();
        info.setAppName(appName);
        info.setAppDes(appDes);
        info.setAppIcon(appIcon);
        info.setAppType(appType);
        info.setCallbackUrl(callbackUrl);
        info.setUserId(uid);
        return info;
    }
}
