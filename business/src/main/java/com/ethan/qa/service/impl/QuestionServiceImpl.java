package com.ethan.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.ELog;
import com.ethan.common.utils.TextUtil;
import com.ethan.qa.config.GlobalConfig;
import com.ethan.qa.mapper.QuestionMapper;
import com.ethan.qa.pojo.po.Question;
import com.ethan.qa.pojo.vo.IQuestionVo;
import com.ethan.qa.pojo.vo.OAnswerVo;
import com.ethan.qa.pojo.vo.OQuestionVo;
import com.ethan.qa.service.IAnswerService;
import com.ethan.qa.service.IDomainService;
import com.ethan.qa.service.IQuestionService;
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
public class QuestionServiceImpl extends BaseServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private IAnswerService mAnswerService;
    @Autowired
    private IDomainService mDomainService;
    @Autowired
    private IUserStarService mUserStarService;
    @Autowired
    @Lazy
    private IUserInfoService mUserInfoService;

    @Override
    public ResponseResult recommendList(long domain, long currentPage, boolean enableAlgorithm) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，一定是 Long 型：）
        ELog.INFO("获取推荐列表 UID ---> " + uid);

        if (enableAlgorithm) {
            // TODO: 2023/2/27 推荐算法，目前是按创建顺序分页查出来
        }

        // 数据库分页查询到原始信息
        List<Question> originList;
        Page<Question> page = new Page<>(currentPage, GlobalConfig.PAGE_SIZE);
        if (domain == GlobalConfig.DOMAIN_ALL) {
            originList = page(page).getRecords();
        } else {
            QueryWrapper<Question> wrapper = new QueryWrapper<>();
            wrapper.eq("domain_id", domain);
            originList = page(page, wrapper).getRecords();
        }

        // 组装额外信息
        List<OQuestionVo> questionList = new ArrayList<>();
        for (Question q : originList) {
            questionList.add(getExtraInfo(q));
        }

        return ResponseResult.SUCCESS(questionList);
    }

    @Override
    public ResponseResult qaInfo(long id) {
        // 获取问题原始信息
        Question origin = getById(id);
        if (origin == null) {
            return ResponseResult.FAIL("问题不存在");
        }
        // 组装额外信息
        OQuestionVo question = getExtraInfo(origin);

        // 获取回答信息
        List<OAnswerVo> answers = mAnswerService.getAnswers(question.getQuestionId());

        // 拼装返回
        List<Object> qa = new ArrayList<>();
        qa.add(question);
        qa.add(answers);
        return new ResponseResult(ResponseState.SUCCESS, qa);
    }

    @Override
    public ResponseResult publishQuestion(Long questionId, IQuestionVo questionVo) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("发布/修改问题 UID ---> " + uid);

        if (TextUtil.isEmpty(questionVo.getTitle())) {
            return ResponseResult.FAIL("标题不可为空");
        }
        if (!mDomainService.hasDomain(questionVo.getDomainId())) {
            return ResponseResult.FAIL("该领域不存在");
        }

        // 如果有 QID，说明是修改问题
        if (questionId != null) {
            // 判断 QID 的所有者是不是 UID
            Question question = getById(questionId);
            if (question.getUserId() != uid) {
                return new ResponseResult(ResponseState.PERMISSION_DENIED);
            }
            // 悬赏金不支持修改，传上来也没用
            question.setTitle(questionVo.getTitle());
            question.setContent(questionVo.getContent());
            question.setDomainId(questionVo.getDomainId());
            // 更新
            if (!updateById(question)) {
                return new ResponseResult(ResponseState.DB_FAIL);
            }
        } else {
            // 如果没有 QID，说明是新建问题
            // 保存
            if (!save(questionVo.toQuestion(uid, uid))) {
                return new ResponseResult(ResponseState.DB_FAIL);
            }
            // 扣悬赏金
            mUserInfoService.withdraw(uid, questionVo.getReward());
        }

        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult deleteQuestion(Long questionId) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("删除问题 UID ---> " + uid);

        // 判断 AID 的所有者是不是 UID 或者是不是管理员
        Question question = getById(questionId);
        if (question.getUserId() != uid && !isAdmin().isSuccess()) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        // 删除（逻辑删除）
        if (!removeById(questionId)) {
            return new ResponseResult(ResponseState.DB_FAIL);
        }
        return ResponseResult.SUCCESS();
    }

    @Override
    public long userQuestionAmount(long uid) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        return count(wrapper);
    }

    @Override
    public ResponseResult userQuestion() {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("获取问题 UID ---> " + uid);

        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        List<OQuestionVo> questionList = new ArrayList<>();
        for (Question q : list(wrapper)) {
            questionList.add(getExtraInfo(q));
        }

        return ResponseResult.SUCCESS(questionList);
    }

    /**
     * 组装问题额外信息，联立查询收藏数、回答数、点赞数
     */
    private OQuestionVo getExtraInfo(Question q) {
        OQuestionVo oQuestionVo = new OQuestionVo(q);
        // 联立查询收藏数
        oQuestionVo.setStarCount(mUserStarService.getQuestionStarCount(q.getQuestionId()));
        oQuestionVo.setAnswerCount(mAnswerService.getAnswersCount(q.getQuestionId()));
        // TODO: 2023/2/28 联立查询评论数
        return oQuestionVo;
    }
}