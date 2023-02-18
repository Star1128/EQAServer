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
@TableName("login_record")
@ApiModel(value = "LoginRecord对象", description = "")
public class LoginRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID) // 插入条目时 id 使用雪花算法策略
    private Long id;

    @ApiModelProperty("用户 ID")
    private Long userId;

    @ApiModelProperty("登录 IP")
    private String loginIp;

    @ApiModelProperty("登录来源")
    private Integer loginFrom;

    @ApiModelProperty("创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(Integer loginFrom) {
        this.loginFrom = loginFrom;
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
        return "LoginRecord{" +
        "id = " + id +
        ", userId = " + userId +
        ", loginIp = " + loginIp +
        ", loginFrom = " + loginFrom +
        ", createTime = " + createTime +
        ", updateTime = " + updateTime +
        "}";
    }
}
