package com.ethan.qa.pojo.vo;

import com.ethan.qa.pojo.po.UserDomain;
import lombok.Data;

import java.util.List;

/**
 * @author Ethan 2023/3/3
 */
@Data
public class OUserInfoVo {
    private long domainAmount;
    private long questionAmount;
    private long answerAmount;
    private Object userInfo;
    private int balance;

    public OUserInfoVo(long domainAmount, long questionAmount, long answerAmount, Object userInfo, int balance) {
        this.domainAmount = domainAmount;
        this.questionAmount = questionAmount;
        this.answerAmount = answerAmount;
        this.userInfo = userInfo;
        this.balance = balance;
    }
}
