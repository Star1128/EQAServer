package com.ethan.qa.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@ApiModel(value = "Answer对象", description = "")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID) // 插入条目时 id 使用雪花算法策略
    private Long answerId;

    @ApiModelProperty("问题 ID")
    private Long questionId;

    @ApiModelProperty("回答者 ID")
    private Long userId;

    @ApiModelProperty("最后编辑者 ID")
    private Long lastEditUserIdCopy1;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("图片")
    private String images;

    @ApiModelProperty("分红")
    private Integer reward;

    @ApiModelProperty("推荐权重")
    private Integer recommendScore;

    @ApiModelProperty("得票数")
    private Integer voteCount;

    @ApiModelProperty("浏览量")
    private Integer viewCount;

    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    @ApiModelProperty("创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLastEditUserIdCopy1() {
        return lastEditUserIdCopy1;
    }

    public void setLastEditUserIdCopy1(Long lastEditUserIdCopy1) {
        this.lastEditUserIdCopy1 = lastEditUserIdCopy1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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

    public Integer getRecommendScore() {
        return recommendScore;
    }

    public void setRecommendScore(Integer recommendScore) {
        this.recommendScore = recommendScore;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + answerId +
                ", questionId='" + questionId + '\'' +
                ", userId=" + userId +
                ", lastEditUserIdCopy1=" + lastEditUserIdCopy1 +
                ", content='" + content + '\'' +
                ", images='" + images + '\'' +
                ", reward=" + reward +
                ", recommendScore=" + recommendScore +
                ", voteCount=" + voteCount +
                ", viewCount=" + viewCount +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
