package com.ethan.ucenter.pojo.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Images对象", description = "")
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID) // 插入条目时 id 使用雪花算法策略
    private Long id;

    @ApiModelProperty("用户 ID")
    private Long userId;

    @ApiModelProperty("MD5")
    private String md5;

    @ApiModelProperty("图片名")
    private String imageName;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("图片类型（0 用户头像 1 应用图标）")
    private Integer imageType;

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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
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
        return "Images{" +
        "id = " + id +
        ", userId = " + userId +
        ", md5 = " + md5 +
        ", imageName = " + imageName +
        ", imageUrl = " + imageUrl +
        ", imageType = " + imageType +
        ", createTime = " + createTime +
        ", updateTime = " + updateTime +
        "}";
    }
}
