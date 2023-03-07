package com.ethan.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.po.Question;
import com.ethan.qa.pojo.vo.IQuestionVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
public interface IQuestionService extends IService<Question> {

    /**
     * 获取首页推荐列表
     *
     * @param domain          领域 ID
     * @param currentPage     第几页
     * @param enableAlgorithm 开启算法推荐
     */
    ResponseResult recommendList(long domain, long currentPage, boolean enableAlgorithm);

    /**
     * 获取问答信息
     */
    ResponseResult qaInfo(long id);

    /**
     * 发布问题
     */
    ResponseResult publishQuestion(Long questionId,IQuestionVo IQuestionVo);

    /**
     * 获取用户提问数量
     */
    long userQuestionAmount(long uid);

    /**
     * 获取用户提问信息
     */
    ResponseResult userQuestion();

    ResponseResult deleteQuestion(Long questionId);
}
