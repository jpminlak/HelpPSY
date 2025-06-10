package com.cai.helppsy.accidentBulleinBoard.service;


import com.cai.helppsy.accidentBulleinBoard.DTO.ReplyLikeDTO;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyLikeEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.CommentReplyLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentReplyLikeService {

    private final CommentReplyLikeRepository commentReplyLikeRepository;

    @Transactional
    public ReplyLikeDTO addRelyLike(String type, String alias, Integer replyId, int liked){
        CommentReplyEntity commentReplyEntity = new CommentReplyEntity();
        commentReplyEntity.setId(replyId); // 외래키 지정 준비
        //좋아요 중복 체크를 위해 좋아요 테이블 값 추적
        Optional<CommentReplyLikeEntity> replyLikeCheck = commentReplyLikeRepository.findByAliasAndCommentReplyEntity_Id(alias,replyId);
        if (replyLikeCheck.isEmpty()){ // .isEmpty해당 객체 값이 없다면
            CommentReplyLikeEntity commentReplyLikeEntity = new CommentReplyLikeEntity(); // 새객체에 변수대입
            commentReplyLikeEntity.setType(type);
            commentReplyLikeEntity.setAlias(alias);
            commentReplyLikeEntity.setCommentReplyEntity(commentReplyEntity);
            commentReplyLikeEntity.setLiked(liked);
            CommentReplyLikeEntity LikeSave = commentReplyLikeRepository.save(commentReplyLikeEntity);
            ReplyLikeDTO replyLikeDTO = new ReplyLikeDTO
                    (LikeSave.getId(),LikeSave.getType(),LikeSave.getAlias(),LikeSave.getLiked());
            return replyLikeDTO;
        }else{
            // 객체 값이 있다면 삭제후 이미지 타입 0 반환
            commentReplyLikeRepository.deleteByAliasAndCommentReplyEntity_Id(alias,replyId);
            return new ReplyLikeDTO(0,type,alias,0);
        }
    }

    // 게시글 로드시(새로고침) 좋아요 상태 유지 기능
    public int getReplyLike(String alias,Integer replyId){
        return commentReplyLikeRepository.findByAliasAndCommentReplyEntity_Id(alias,replyId)
                .map(CommentReplyLikeEntity::getLiked) // 조회결과가 있을시 LikeEntity의 getLiked()값을 가져옴
                .orElse(0); // 만약 좋아요 기록이 없다면(=Optional이 비어 있음), 기본값으로 0을 반환합니다.
    }

    // 좋아요 총 갯수 가져오기
    public int LikeCount(Integer replyId){
        return commentReplyLikeRepository.countByCommentReplyEntity_Id(replyId);
    }


}
