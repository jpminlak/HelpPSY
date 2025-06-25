package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.DTO.ReplyDTO;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyEntity;
import com.cai.helppsy.accidentBulleinBoard.service.CommentReplyService;
import com.cai.helppsy.memberManager.SignupEntity;
import com.cai.helppsy.memberManager.signupRepository;
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
    private final signupRepository signupRepository; // 회원디비


    // 대댓글 클릭시 대댓글 리스트 가져오기
    @GetMapping("/getReplies")
    @ResponseBody
    public List<ReplyDTO> getReplies(@RequestParam("commentId") Integer commentId){
        List<CommentReplyEntity> replies = commentReplyService.getRepliesByCommentId(commentId);

        List<ReplyDTO> replyDTOs = replies.stream()
                .map(reply -> {
                            SignupEntity signup = signupRepository.findByAlias(reply.getAlias());  // 회원정보 조회
                            String profileImage = (signup != null) ? signup.getProfileImage() : null;

                    return new ReplyDTO(
                            reply.getId(),          // 대댓글 ID
                            reply.getComment(),     // 대댓글 내용
                            reply.getAlias(),       // 대댓글 작성자의 별명
                            reply.getCommentEntity().getId(),  // 원본 댓글의 ID
                            profileImage            // 프로필 이미지 이름
                    );
                })
                .collect(Collectors.toList());

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

        // 작성자 정보 조회 (profileImage 포함)
        SignupEntity signup = signupRepository.findByAlias(Repl.getAlias());
        String profileImage = (signup != null) ? signup.getProfileImage() : null;

        return new ReplyDTO(
                Repl.getId(),
                Repl.getComment(),
                Repl.getAlias(),
                Repl.getCommentEntity().getId(),
                profileImage
        );
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

    // 대댓글 수정하기
    @PostMapping("/reply/update")
    @ResponseBody
    public ReplyDTO replyUpdate(
            @RequestParam("commentReplyId") Integer commentReplyId,
            @RequestParam("commentReplyAlias") String commentReplyAlias,
            @RequestParam("commentReply") String commentReply){
            // 1. Repl에 대댓글 객체 저장후
            CommentReplyEntity Repl = commentReplyService.replyUpdate(commentReplyId,commentReplyAlias,commentReply);

            // 프로필 이미지 조회
            SignupEntity signup = signupRepository.findByAlias(Repl.getAlias());
            String profileImage = (signup != null) ? signup.getProfileImage() : null;

            // DTO에 profileImage 포함해서 반환
            return new ReplyDTO(
                    Repl.getId(),
                    Repl.getComment(),
                    Repl.getAlias(),
                    Repl.getCommentEntity().getId(),
                    profileImage
            );
        }
    }
