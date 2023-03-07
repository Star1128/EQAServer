package com.ethan.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.utils.ELog;
import com.ethan.qa.config.GlobalConfig;
import com.ethan.qa.mapper.UserStarMapper;
import com.ethan.qa.pojo.po.UserStar;
import com.ethan.qa.service.IUserStarService;
import org.springframework.stereotype.Service;

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
public class UserStarServiceImpl extends BaseServiceImpl<UserStarMapper, UserStar> implements IUserStarService {

    @Override
    public int getAnswerStarCount(long answerId) {
        QueryWrapper<UserStar> wrapper = new QueryWrapper<>();
        wrapper.eq("target_id", answerId);
        return (int) count(wrapper);
    }

    @Override
    public int getQuestionStarCount(long questionId) {
        QueryWrapper<UserStar> wrapper = new QueryWrapper<>();
        wrapper.eq("target_id", questionId);
        return (int) count(wrapper);
    }

    @Override
    public ResponseResult questionCollect(long questionId, int type) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，一定是 Long 型：）
        ELog.INFO("管理收藏的问题 UID ---> " + uid);

        // 收藏
        if (type == GlobalConfig.COLLECT) {
            QueryWrapper<UserStar> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", uid);
            wrapper.eq("target_id", questionId);
            wrapper.eq("type", GlobalConfig.COLLECT_QUESTION); // 其实不需要检查数据表 type 因为 id 是唯一的
            long count = count(wrapper);
            if (count != 0) {
                return ResponseResult.FAIL("该收藏记录已存在");
            }

            UserStar userStar = new UserStar();
            userStar.setUserId(uid);
            userStar.setType(GlobalConfig.COLLECT_QUESTION);
            userStar.setTargetId(questionId);
            save(userStar);
        }
        // 取消收藏
        else {
            QueryWrapper<UserStar> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", uid);
            wrapper.eq("target_id", questionId);
            wrapper.eq("type", GlobalConfig.COLLECT_QUESTION); // 其实不需要检查数据表 type 因为 id 是唯一的
            remove(wrapper);
        }
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult answerCollect(long answerId, int type) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，一定是 Long 型：）
        ELog.INFO("管理收藏的回答 UID ---> " + uid);

        // 收藏
        if (type == GlobalConfig.COLLECT) {
            QueryWrapper<UserStar> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", uid);
            wrapper.eq("target_id", answerId);
            wrapper.eq("type", GlobalConfig.COLLECT_ANSWER); // 其实不需要检查数据表 type 因为 id 是唯一的
            long count = count(wrapper);
            if (count != 0) {
                return ResponseResult.FAIL("该收藏记录已存在");
            }

            UserStar userStar = new UserStar();
            userStar.setUserId(uid);
            userStar.setType(GlobalConfig.COLLECT_ANSWER);
            userStar.setTargetId(answerId);
            save(userStar);
        }
        // 取消收藏
        else {
            QueryWrapper<UserStar> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", uid);
            wrapper.eq("target_id", answerId);
            wrapper.eq("type", GlobalConfig.COLLECT_QUESTION); // 其实不需要检查数据表 type 因为 id 是唯一的
            remove(wrapper);
        }
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult getList() {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，一定是 Long 型：）
        ELog.INFO("获取收藏列表 UID ---> " + uid);

        QueryWrapper<UserStar> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        List<UserStar> list = list(wrapper);

        return ResponseResult.SUCCESS(list);
    }
}
