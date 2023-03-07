package com.ethan.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.po.UserStar;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
public interface IUserStarService extends IService<UserStar> {

    /**
     * 查询回答收藏数量
     */
    int getAnswerStarCount(long answerId);

    /**
     * 查询问题收藏数量
     */
    int getQuestionStarCount(long answerId);

    /**
     * 管理收藏的问题
     */
    ResponseResult questionCollect(long questionId, int type);

    /**
     * 管理收藏的回答
     */
    ResponseResult answerCollect(long answerId, int type);

    /**
     * 获取收藏列表
     */
    ResponseResult getList();

}
