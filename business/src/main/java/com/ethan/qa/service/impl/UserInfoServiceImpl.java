package com.ethan.qa.service.impl;

import com.ethan.common.response.ResponseResult;
import com.ethan.common.utils.ELog;
import com.ethan.qa.mapper.UserInfoMapper;
import com.ethan.qa.pojo.po.Domain;
import com.ethan.qa.pojo.po.UserInfo;
import com.ethan.qa.pojo.vo.UserInfoO;
import com.ethan.qa.service.IAnswerService;
import com.ethan.qa.service.IDomainService;
import com.ethan.qa.service.IQuestionService;
import com.ethan.qa.service.IUserDomainService;
import com.ethan.qa.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private IUserDomainService mUserDomainService;
    @Autowired
    private IQuestionService mQuestionService;
    @Autowired
    private IAnswerService mAnswerService;
    @Autowired
    private IDomainService mDomainService;

    @Override
    public int getBalance(long uid) {
        UserInfo userInfo = getById(uid);
        return userInfo.getBalance();
    }

    @Override
    public boolean withdraw(long uid, int amount) {
        UserInfo userInfo = getById(uid);
        if (userInfo == null) {
            return false;
        }
        userInfo.setBalance(userInfo.getBalance() - amount);
        return updateById(userInfo);
    }

    @Override
    public boolean deposit(long uid, int amount) {
        UserInfo userInfo = getById(uid);
        if (userInfo == null) {
            return false;
        }
        userInfo.setBalance(userInfo.getBalance() + amount);
        return updateById(userInfo);
    }

    @Override
    public ResponseResult overviewInfo() {
        ResponseResult t2uResult;
        if (!(t2uResult = T2U()).isSuccess()) {
            return t2uResult;
        }
        long uid = Long.parseLong((String) t2uResult.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("获取信息 UID ---> " + uid);

        // 领域数量
        long domainAmount = mUserDomainService.userDomainAmount(uid);
        // 问答数量
        long questionAmount = mQuestionService.userQuestionAmount(uid);
        long answerAmount = mAnswerService.userAnswerAmount(uid);

        // 个人信息
        ResponseResult infoResult;
        if (!(infoResult = UCInfo()).isSuccess()) {
            return infoResult;
        }
        Object userInfo = infoResult.getData();

        // 财务信息
        int balance = getBalance(uid);

        // 组合
        UserInfoO userInfoO = new UserInfoO(domainAmount, questionAmount, answerAmount, userInfo, balance);
        return ResponseResult.SUCCESS(userInfoO);
    }

    @Override
    public void initUserInfo(long uid) {
        if (getById(uid) == null) {
            // 初始化用户信息
            UserInfo info = new UserInfo();
            info.setUserId(uid);
            info.setBalance(0);
            save(info);

            // 初始化用户在各个领域的经验
            for (Domain domain : mDomainService.list()) {
                mUserDomainService.joinDomain(uid, domain.getDomainId());
            }
        }
    }
}
