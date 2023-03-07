package com.ethan.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.ELog;
import com.ethan.qa.mapper.AnswerMapper;
import com.ethan.qa.pojo.po.Answer;
import com.ethan.qa.pojo.vo.IAnswerVo;
import com.ethan.qa.pojo.vo.OAnswerVo;
import com.ethan.qa.service.IAnswerService;
import com.ethan.qa.service.IUserInfoService;
import com.ethan.qa.service.IUserStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@Service
public class AnswerServiceImpl extends BaseServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Autowired
    private IUserStarService mUserStarService;
    @Autowired
    @Lazy
    private IUserInfoService mUserInfoService;

    @Override
    public int getAnswersCount(long questionId) {
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        return (int) count(wrapper);
    }

    @Override
    public List<OAnswerVo> getAnswers(long questionId) {

        // 数据库查原始信息
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        List<Answer> originList = list(wrapper);
        ELog.INFO(originList.toString());

        // 组装额外信息
        List<OAnswerVo> answerList = new ArrayList<>();
        for (Answer a : originList) {
            answerList.add(getExtraInfo(a));
        }

        // TODO 算法排序

        return answerList;
    }

    @Override
    public ResponseResult publishAnswer(long questionId, IAnswerVo answerVo) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("发布回答 UID ---> " + uid);

        // 保存
        Answer answer = answerVo.toAnswer(questionId, uid, uid);
        if (!save(answer)) {
            return new ResponseResult(ResponseState.DB_FAIL);
        }

        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult editAnswer(Long answerId, IAnswerVo answerVo) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("修改回答 UID ---> " + uid);

        // 判断 AID 的所有者是不是 UID
        Answer answer = getById(answerId);
        if (answer.getUserId() != uid) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }
        answer.setContent(answerVo.getContent());
        // 更新
        if (!updateById(answer)) {
            return new ResponseResult(ResponseState.DB_FAIL);
        }

        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult thumbUp(long answerId) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("点赞 UID ---> " + uid);

        Answer answer = getById(answerId);
        answer.setVoteCount(answer.getVoteCount() + 1);
        updateById(answer);
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult thumbDown(long answerId) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("点踩 UID ---> " + uid);

        Answer answer = getById(answerId);
        if (answer == null) {
            return ResponseResult.FAIL("回答不存在");
        }
        int newCount = answer.getVoteCount() - 1;
        if (newCount < 0) { // 最低为 0
            newCount = 0;
        }
        answer.setVoteCount(newCount);
        updateById(answer);
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult giveReward(long answerId, int reward) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("打赏 UID ---> " + uid);

        // 检查余额
        long balance = mUserInfoService.getBalance(uid);
        if (balance < reward) {
            return new ResponseResult(ResponseState.INSUFFICIENT_BALANCE);
        }

        // 转出
        mUserInfoService.withdraw(uid, reward);

        // 记录
        Answer answer = getById(answerId);
        if (answer == null) {
            return ResponseResult.FAIL("回答不存在");
        }
        answer.setReward(answer.getReward() + reward);
        updateById(answer);

        // 转入
        mUserInfoService.deposit(uid, reward);

        return ResponseResult.SUCCESS();
    }

    @Override
    public long userAnswerAmount(long uid) {
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        return count(wrapper);
    }

    @Override
    public ResponseResult userAnswer() {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("获取回答 UID ---> " + uid);

        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        List<OAnswerVo> answerList = new ArrayList<>();
        for (Answer a : list(wrapper)) {
            answerList.add(getExtraInfo(a));
        }

        return ResponseResult.SUCCESS(answerList);
    }

    @Override
    public ResponseResult deleteAnswer(Long answerId) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("删除回答 UID ---> " + uid);

        // 判断 AID 的所有者是不是 UID 或者是不是管理员
        Answer answer = getById(answerId);
        if (answer.getUserId() != uid && !isAdmin().isSuccess()) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        // 删除（逻辑删除）
        if (!removeById(answerId)) {
            return new ResponseResult(ResponseState.DB_FAIL);
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 组装回答额外信息
     */
    private OAnswerVo getExtraInfo(Answer a) {
        OAnswerVo oAnswerVo = new OAnswerVo(a);
        // 联立查询收藏数
        oAnswerVo.setStarCount(mUserStarService.getAnswerStarCount(a.getAnswerId()));
        // TODO: 2023/2/28 联立查询评论数
        return oAnswerVo;
    }
}
