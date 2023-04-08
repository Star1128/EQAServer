package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.Answer;
import lombok.Data;

/**
 * @author Ethan 2023/2/28
 */
@Data
public class AnswerI {
    private String content;

    public Answer toAnswer(long qid, long uid, long lastId) {
        Answer answer = new Answer();
        answer.setQuestionId(qid);
        answer.setUserId(uid);
        answer.setLastEditUserIdCopy1(lastId);
        answer.setContent(content);
        return answer;
    }
}
