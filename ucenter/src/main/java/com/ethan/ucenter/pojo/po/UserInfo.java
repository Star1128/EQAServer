package com.ethan.ucenter.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
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
@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID) // 插入条目时 id 使用雪花算法策略
    private Long id;

    @ApiModelProperty("用户 ID")
    private Long userId;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("座右铭")
    private String motto;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("公司")
    private String company;

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("地址")
    private String location;

    @ApiModelProperty("创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public UserInfo() {
    }

    public UserInfo(Long userId, String sex, String avatar, String motto, String phone, String company, String position, LocalDate birthday, String location) {
        this.userId = userId;
        this.sex = sex;
        this.avatar = avatar;
        this.motto = motto;
        this.phone = phone;
        this.company = company;
        this.position = position;
        this.birthday = birthday;
        this.location = location;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return "UserInfo{" +
        "id = " + id +
        ", userId = " + userId +
        ", sex = " + sex +
        ", avatar = " + avatar +
        ", motto = " + motto +
        ", phone = " + phone +
        ", email = " + email +
        ", company = " + company +
        ", position = " + position +
        ", birthday = " + birthday +
        ", location = " + location +
        ", createTime = " + createTime +
        ", updateTime = " + updateTime +
        "}";
    }
}
