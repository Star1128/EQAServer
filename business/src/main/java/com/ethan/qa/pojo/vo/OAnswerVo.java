package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.Answer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ethan 2023/2/28
 */
@Data
public class OAnswerVo {
    private String answerId;
    private String questionId;
    private String userId;
    private String userName;
    private String lastEditUserId;
    private String lastEditUserName;
    private String content;
    private String images;
    private Integer reward;
    private Integer commentCount; // 暂时不支持
    private Integer starCount;
    private Integer voteCount;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public OAnswerVo(Answer answer) {
        setAnswerId(String.valueOf(answer.getAnswerId()));
        setQuestionId(String.valueOf(answer.getQuestionId()));
        setUserId(String.valueOf(answer.getUserId()));
        setLastEditUserId(String.valueOf(answer.getLastEditUserIdCopy1()));
        setContent(answer.getContent());
        setImages(answer.getImages());
        setReward(answer.getReward());
        setVoteCount(answer.getVoteCount());
        setViewCount(answer.getViewCount());
        createTime = answer.getCreateTime();
        updateTime = answer.getUpdateTime();
    }
}
