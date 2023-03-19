package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.Question;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ethan 2023/2/28
 */
@Data
public class OQuestionVo {
    private String questionId;
    private String userId;
    private String userName;
    private String lastEditUserId;
    private String lastEditUserName;
    private String domainId;
    private String title;
    private String content;
    private String images;
    private Integer reward;
    private Integer answerCount;
    private Integer commentCount; // 暂不支持
    private Integer starCount;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public OQuestionVo(Question question) {
        setQuestionId(String.valueOf(question.getQuestionId()));
        userId = String.valueOf(question.getUserId());
        setLastEditUserId(String.valueOf(question.getLastEditUserId()));
        domainId = String.valueOf(question.getDomainId());
        title = question.getTitle();
        setContent(question.getContent());
        setImages(question.getImages());
        setReward(question.getReward());
        setViewCount(question.getViewCount());
        createTime = question.getCreateTime();
        updateTime = question.getUpdateTime();
    }
}
