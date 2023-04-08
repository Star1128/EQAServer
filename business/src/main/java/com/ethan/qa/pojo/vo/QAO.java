package com.ethan.qa.pojo.vo;

import java.util.List;

/**
 * @author Ethan 2023/3/19
 */
public class QAO {
    private QuestionO question;
    private List<AnswerO> answers;

    public QAO(QuestionO question, List<AnswerO> answers) {
        this.question = question;
        this.answers = answers;
    }

    public QuestionO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionO question) {
        this.question = question;
    }

    public List<AnswerO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerO> answers) {
        this.answers = answers;
    }
}
