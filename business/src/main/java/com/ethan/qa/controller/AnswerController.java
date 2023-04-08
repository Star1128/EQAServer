package com.ethan.qa.controller;

import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.vo.AnswerI;
import com.ethan.qa.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private IAnswerService mAnswerService;

    @PostMapping("/publish")
    public ResponseResult publishAnswer(@RequestParam("question_id") long questionId, @RequestBody AnswerI AnswerI) {
        return mAnswerService.publishAnswer(questionId, AnswerI);
    }

    @PostMapping("/edit")
    public ResponseResult editAnswer(@RequestParam("answer_id") Long answerId, @RequestBody AnswerI AnswerI) {
        return mAnswerService.editAnswer(answerId, AnswerI);
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteAnswer(@RequestParam("answer_id") Long answerId) {
        return mAnswerService.deleteAnswer(answerId);
    }

    @PatchMapping("/thumb-up")
    public ResponseResult thumbUp(@RequestParam("answer_id") long answerId) {
        return mAnswerService.thumbUp(answerId);
    }

    @PatchMapping("/thumb-down")
    public ResponseResult thumbDown(@RequestParam("answer_id") long answerId) {
        return mAnswerService.thumbDown(answerId);
    }

    @PatchMapping("/reward")
    public ResponseResult giveReward(@RequestParam("answer_id") long answerId, @RequestParam("reward") int reward) {
        return mAnswerService.giveReward(answerId, reward);
    }

    @GetMapping("/personal")
    public ResponseResult userAnswer() {
        return mAnswerService.userAnswer();
    }
}
