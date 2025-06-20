package com.cai.helppsy.accidentBulleinBoard.service;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.CommentReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReplyService {
    private final CommentReplyRepository commentReplyRepository;

    // 대댓글 버튼 눌렀을때
    public List<CommentReplyEntity> getRepliesByCommentId(Integer commentId){
        return commentReplyRepository.findByCommentEntityId(commentId);
    }

    // 대댓글 저장
    public CommentReplyEntity setCommentReply(CommentReplyEntity commententity, Integer commentId){
       // 1.CommentEntity(댓글) 객체를 생성하여 댓글id를 저장
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(commentId);
        commententity.setCommentEntity(commentEntity);
        // 2.대댓글 내용을 DB에 저장하고 변수에 대입
        CommentReplyEntity Repl = commentReplyRepository.save(commententity);
        return Repl;
    }

    // 대댓글 삭제
    public void deleteCommentReply(Integer id){
        commentReplyRepository.deleteById(id);
    }

    // 대댓글 수정
    public CommentReplyEntity replyUpdate(Integer commentReplyId, String commentReplyAlias,String commentReply){
        CommentReplyEntity entity = commentReplyRepository.findByIdAndAlias(commentReplyId,commentReplyAlias);
        entity.setComment(commentReply);
        System.out.println("------------대댓글 수정 ");
        System.out.println(entity.getComment());
        System.out.println(entity.getAlias());
        System.out.println("------------대댓글 수정 ");
        return commentReplyRepository.save(entity);
    }
}
