package com.ethan.qa.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Ethan 2023/4/11
 */
@Data
public class AnswersO {
    private List<AnswerO> mAnswerInfos;

    public AnswersO(List<AnswerO> answerInfos) {
        mAnswerInfos = answerInfos;
    }
}
