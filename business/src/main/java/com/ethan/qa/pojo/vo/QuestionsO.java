package com.ethan.qa.pojo.vo;

import java.util.List;

/**
 * @author Ethan 2023/4/8
 */
public class QuestionsO {

    private List<QuestionO> mQuestionInfos;

    public QuestionsO(List<QuestionO> questionInfos) {
        mQuestionInfos = questionInfos;
    }

    public List<QuestionO> getQuestionInfos() {
        return mQuestionInfos;
    }

    public void setQuestionInfos(List<QuestionO> questionInfos) {
        mQuestionInfos = questionInfos;
    }
}
