package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.DTO.RegistrationLikeDTO;
import com.cai.helppsy.accidentBulleinBoard.service.RegistrationLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationLikeController {
    private final RegistrationLikeService likeService;

    // 게시글 좋아요 저장
    @PostMapping("/like")
    @ResponseBody // JSON으로 비동기 처리시 꼭 넣어주기!!!
    public Map<String, Object> like(@RequestParam("type") String type,
                                    @RequestParam("alias") String alias,
                                    @RequestParam("registration_entity_id") Integer registration_entity_id,
                                    @RequestParam("liked") int liked){
        RegistrationLikeDTO likedDto = likeService.addLike(type,alias,registration_entity_id,liked); // 서비스,DTO저장후 변수에 대입
        int likedStatus = likedDto.getLiked(); // 좋아요 체크가 있는지 확인하여 이미지 변환시켜줄 내용
        int likeCount = likeService.LikeCountByPostId(registration_entity_id); // 좋아요 총갯수

        Map<String, Object> result = new HashMap<>();
        result.put("likedStatus", likedStatus);
        result.put("likeCount", likeCount);

        return result;
    }

    // 게시글 페이지 로드시 좋아요 자동갱신
    @PostMapping("/like/revert")
    @ResponseBody
    public Map<String, Object> likerevert(@RequestParam("alias") String alias,
                                          @RequestParam("registration_entity_id") Integer registration_entity_id){
        int likedStatus = likeService.getLike(alias,registration_entity_id); // 좋아요 기록 남기기
        int likeCount = likeService.LikeCountByPostId(registration_entity_id); // 해당댓글 좋아요 총 갯수
        Map<String, Object> result = new HashMap<>(); // String자료형의 Object(모든타입) map으로 key,value형태로 작성 가능
        result.put("liked", likedStatus);
        result.put("likeCount", likeCount);
        return result;
    }
}