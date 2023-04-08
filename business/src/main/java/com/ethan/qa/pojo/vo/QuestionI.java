package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.Question;
import lombok.Data;

/**
 * @author Ethan 2023/2/28
 */
@Data
public class QuestionI {
    private String title;
    private String content;
    private Long domainId;
    private Integer reward;

    public Question toQuestion(long uid, long lastId) {
        Question question = new Question();
        question.setUserId(uid);
        question.setLastEditUserId(lastId);
        question.setDomainId(domainId);
        question.setTitle(title);
        question.setContent(content);
        question.setReward(reward);
        return question;
    }
}
