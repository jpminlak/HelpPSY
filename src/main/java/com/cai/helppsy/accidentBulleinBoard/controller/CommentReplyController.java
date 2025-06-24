package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.DTO.ReplyDTO;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyEntity;
import com.cai.helppsy.accidentBulleinBoard.service.CommentReplyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommentReplyController {
    private final CommentReplyService commentReplyService;

    // 대댓글 클릭시 대댓글 리스트 가져오기
    @GetMapping("/getReplies")
    @ResponseBody
    public List<ReplyDTO> getReplies(@RequestParam("commentId") Integer commentId){
        List<CommentReplyEntity> replies = commentReplyService.getRepliesByCommentId(commentId);

        // CommentReplyEntity 리스트를 ReplyDTO 리스트로 변환
        List<ReplyDTO> replyDTOs = replies.stream()
                .map(reply -> new ReplyDTO(
                        reply.getId(),          // 대댓글 ID
                        reply.getComment(),     // 대댓글 내용
                        reply.getAlias(),       // 대댓글 작성자의 별명
                        reply.getCommentEntity().getId()))  // 원본 댓글의 ID
                .collect(Collectors.toList());  // 변환된 ReplyDTO 객체들을 리스트에 수집

        // 변환된 ReplyDTO 리스트를 반환
        return replyDTOs;
    }

    // 대댓글 저장하기
    @PostMapping("/reply")
    @ResponseBody
    public ReplyDTO handleReply(
            @ModelAttribute CommentReplyEntity commentReplyEntity,
            @RequestParam("commentId") Integer commentId){
        // 디버깅시 흐름 따라갈것. -- controller->service단 처리 다름

        // 1. Repl에 대댓글 객체 저장후
        CommentReplyEntity Repl =
                commentReplyService.setCommentReply(commentReplyEntity,commentId);

        // 2. DTO에 담아 클라이언트에게 DTO타입의 JSON 으로 넘김
        // - DTO에 담는 이유는 entity는 저장된 데이터 노출 위험이 있기 떄문에
        // - 또한 DTO에서 JSON으로 넘겨줄시 넘겨주고 싶은 데이터만 DTO수정을 통해 조절가능
        ReplyDTO dto =
                new ReplyDTO(Repl.getId(),Repl.getComment(),Repl.getAlias(), Repl.getCommentEntity().getId());
//        System.out.println("-----------------------제이슨 보낼 양식 여기");
//        System.out.println(dto.getId());
//        System.out.println(dto.getAlias());
//        System.out.println(dto.getComment());
//        System.out.println(dto.getCommentId());
//        System.out.println("-----------------------제이슨 보낼 양식 여기");
        // 반환 DTO JSON구조 예시
        /*
        {
          "id": 5,
          "comment": "이건 대댓글입니다.",
          "alias": "홍길동",
          "commentId": 2
        }
         */
        return dto;  // Ajax 성공 시 호출될 응답
    }

    // 대댓글 삭제하기
    @PostMapping("/replydelete")
    @ResponseBody
    public void replydelete(@RequestParam("replyId") Integer id,
                            @RequestParam("replyAlias") String alias,
                            HttpSession session){
        // 현재 로그인된 세션과 삭제 하고자 하는
        if(session.getAttribute("userAlias").equals(alias)){
            commentReplyService.deleteCommentReply(id);
        }
    }
}
