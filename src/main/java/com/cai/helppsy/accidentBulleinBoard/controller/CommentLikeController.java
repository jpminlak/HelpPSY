package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.DTO.CommentLikeDTO;
import com.cai.helppsy.accidentBulleinBoard.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 좋아요 저장 --  entity,dto 저장 이후 기능에 맞춰 한번의 로직으로 해결
    @PostMapping("/Commentlike")
    @ResponseBody // JSON으로 비동기 처리시 꼭 넣어주기!!! 이거뺴먹어서 2시간 날림
    public Map<String, Object> Commentlike(@RequestParam("type") String type,
                              @RequestParam("CommentUserAlias") String alias,
                              @RequestParam("commentId") Integer commentId,
                              @RequestParam("Commentliked") int liked){


        CommentLikeDTO commentLikeDTO = commentLikeService.addCommentlike(type, alias, commentId,liked);
        int CommentlikedStatus = commentLikeDTO.getLiked(); // 좋아요 상태 여부 0(없음),1(좋아요)
        int CommentlikeCount = commentLikeService.LikeCountByPostId(commentId); // 좋아요 총 개수

        // HashMap으로 결과값 2개를 보내기 (Object는 자바에서 지원하는 모든 자료형을 사용할 수 있음)
        Map<String, Object> result = new HashMap<>();
        result.put("CommentlikedStatus", CommentlikedStatus);
        result.put("CommentlikeCount", CommentlikeCount);
        return  result;
    }

    // 새로고침 시에도 좋아요 상태 유지하기
    @PostMapping("/Commentlike/revert")
    @ResponseBody // JSON으로 비동기 처리시 꼭 넣어주기!!! 이거뺴먹어서 2시간 날림
    public Map<String, Object> Commentlikerevert
            (@RequestParam("CommentUserAlias") String alias,@RequestParam("commentId") Integer commentId)                                           {

        int CommentlikedStatus = commentLikeService.getCommentLike(alias,commentId);
        int CommentlikeCount = commentLikeService.LikeCountByPostId(commentId); // 좋아요 총 개수

        // HashMap으로 결과값 2개를 보내기 (Object는 자바에서 지원하는 모든 자료형을 사용할 수 있음)
        Map<String, Object> result = new HashMap<>();
        result.put("CommentlikedStatus", CommentlikedStatus);
        result.put("CommentlikeCount", CommentlikeCount);
        return  result;
    }

}
