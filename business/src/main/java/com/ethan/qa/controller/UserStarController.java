package com.ethan.qa.controller;

import com.ethan.common.response.ResponseResult;
import com.ethan.qa.service.IUserStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/star")
public class UserStarController {

    @Autowired
    private IUserStarService mUserStarService;

    @PutMapping("/collect/question")
    public ResponseResult questionCollect(@RequestParam("question_id") long questionId, @RequestParam("type") int type) {
        return mUserStarService.questionCollect(questionId, type);
    }

    @PutMapping("/collect/answer")
    public ResponseResult answerCollect(@RequestParam("answer_id") long answerId, @RequestParam("type") int type) {
        return mUserStarService.answerCollect(answerId, type);
    }

    @GetMapping("/list")
    public ResponseResult getList() {
        return mUserStarService.getList();
    }
}
