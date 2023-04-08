package com.ethan.qa.controller;

import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.vo.QuestionI;
import com.ethan.qa.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private IQuestionService mQuestionService;

    /**
     * 获取推荐列表
     *
     * @param domain 领域ID ，0 为混合
     */
    @GetMapping("/list")
    public ResponseResult recommendList(@RequestParam("domain") long domain, @RequestParam("page") long currentPage, @RequestParam("enable_algorithm") boolean enableAlgorithm) {
        return mQuestionService.recommendList(domain, currentPage, enableAlgorithm);
    }

    @GetMapping("/detail/{question_id}")
    public ResponseResult questionInfo(@PathVariable("question_id") long id) {
        return mQuestionService.qaInfo(id);
    }

    @PostMapping("/publish")
    public ResponseResult publishQuestion(@RequestParam(value = "question_id", required = false) Long questionId, @RequestBody QuestionI QuestionI) {
        return mQuestionService.publishQuestion(questionId, QuestionI);
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteQuestion(@RequestParam("question_id") Long questionId) {
        return mQuestionService.deleteQuestion(questionId);
    }

    @GetMapping("/personal")
    public ResponseResult userQuestion() {
        return mQuestionService.userQuestion();
    }
}
