package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.Answer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ethan 2023/2/28
 */
@Data
public class OAnswerVo {
    private Long answerId;
    private Long questionId;
    private Long userId;
    private String userName;
    private Long lastEditUserId;
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
        setAnswerId(answer.getAnswerId());
        setQuestionId(answer.getQuestionId());
        setUserId(answer.getUserId());
        setLastEditUserId(answer.getLastEditUserIdCopy1());
        setContent(answer.getContent());
        setImages(answer.getImages());
        setReward(answer.getReward());
        setVoteCount(answer.getVoteCount());
        setViewCount(answer.getViewCount());
        createTime = answer.getCreateTime();
        updateTime = answer.getUpdateTime();
    }
}
