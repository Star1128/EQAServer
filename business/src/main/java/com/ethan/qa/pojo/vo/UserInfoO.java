package com.ethan.qa.pojo.vo;

import lombok.Data;

/**
 * @author Ethan 2023/3/3
 */
@Data
public class UserInfoO {
    private String domainAmount;
    private String questionAmount;
    private String answerAmount;
    private Object userInfo;
    private int balance;

    public UserInfoO(long domainAmount, long questionAmount, long answerAmount, Object userInfo, int balance) {
        this.domainAmount = String.valueOf(domainAmount);
        this.questionAmount = String.valueOf(questionAmount);
        this.answerAmount = String.valueOf(answerAmount);
        this.userInfo = userInfo;
        this.balance = balance;
    }
}
