package com.ethan.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.po.Answer;
import com.ethan.qa.pojo.vo.AnswerI;
import com.ethan.qa.pojo.vo.AnswerO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
public interface IAnswerService extends IService<Answer> {

    /**
     * 获取问题下的回答数量
     */
    int getAnswersCount(long questionId);

    /**
     * 获取问题下的回答列表，并进行算法排序
     */
    List<AnswerO> getAnswers(long questionId);

    /**
     * 发布回答
     */
    ResponseResult publishAnswer(long questionId, AnswerI AnswerI);

    /**
     * 修改回答
     */
    ResponseResult editAnswer(Long answerId, AnswerI answerVo);

    /**
     * 回答点赞
     * 没有校验同一用户是否多次点赞，由客户端来防止吧。。我摆烂了
     */
    ResponseResult thumbUp(long answerId);

    /**
     * 回答点踩
     * 没有校验同一用户是否多次点踩，由客户端来防止吧。。我摆烂了
     */
    ResponseResult thumbDown(long answerId);

    /**
     * 打赏
     */
    ResponseResult giveReward(long answerId, int reward);

    /**
     * 获取用户回答数量
     */
    long userAnswerAmount(long uid);

    ResponseResult userAnswer();

    ResponseResult deleteAnswer(Long answerId);
}
