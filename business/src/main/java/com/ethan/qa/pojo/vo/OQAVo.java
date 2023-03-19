package com.ethan.qa.pojo.vo;

import java.util.List;

/**
 * @author Ethan 2023/3/19
 */
public class OQAVo {
    private OQuestionVo question;
    private List<OAnswerVo> answers;

    public OQAVo(OQuestionVo question, List<OAnswerVo> answers) {
        this.question = question;
        this.answers = answers;
    }

    public OQuestionVo getQuestion() {
        return question;
    }

    public void setQuestion(OQuestionVo question) {
        this.question = question;
    }

    public List<OAnswerVo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<OAnswerVo> answers) {
        this.answers = answers;
    }
}
