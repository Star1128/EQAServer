package com.ethan.ucenter.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
@TableName("app_info")
@ApiModel(value = "AppInfo对象", description = "")
public class AppInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用 ID")
    @TableId(type = IdType.ASSIGN_ID) // 插入条目时 id 使用雪花算法策略
    private Long appId;

    @ApiModelProperty("应用名")
    private String appName;

    @ApiModelProperty("创建者 ID")
    private Long userId;

    @ApiModelProperty("应用类型（0 Ethan 出品 1 普通）")
    private Integer appType;

    @ApiModelProperty("状态（0 可用 1 审核中 2 禁用）")
    private Integer status;

    @ApiModelProperty("应用唯一标识")
    private String appKey;

    @ApiModelProperty("密钥")
    private String appSecret;

    @ApiModelProperty("回调地址")
    private String callbackUrl;

    @ApiModelProperty("图标")
    private String appIcon;

    @ApiModelProperty("描述")
    private String appDes;

    @ApiModelProperty("逻辑删除标记（0 正常 1 删除）")
    private Integer deleted;

    @ApiModelProperty("创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppDes() {
        return appDes;
    }

    public void setAppDes(String appDes) {
        this.appDes = appDes;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
        "appId = " + appId +
        ", appName = " + appName +
        ", userId = " + userId +
        ", appType = " + appType +
        ", status = " + status +
        ", appKey = " + appKey +
        ", appSecret = " + appSecret +
        ", callbackUrl = " + callbackUrl +
        ", appIcon = " + appIcon +
        ", appDes = " + appDes +
        ", deleted = " + deleted +
        ", createTime = " + createTime +
        ", updateTime = " + updateTime +
        "}";
    }
}
