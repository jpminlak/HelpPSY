package com.cai.helppsy.accidentBulleinBoard.service;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.CommentReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;

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

    // 프로필 별명 변경 테이블에 반영해주기
    public void setReplyAlias(String alias, Integer id){
        List<CommentReplyEntity> commentReplys = commentReplyRepository.findBySignupEntity_Id(id);
        for(CommentReplyEntity commentReply : commentReplys){
            commentReply.setAlias(alias);
            commentReplyRepository.save(commentReply);
        }
    }


//    // 프로필 별명 변경 테이블에 반영해주기
//    public void setReplyAlias(String alias, Integer id){
//
//        Optional<CommentReplyEntity> a = commentReplyRepository.findBySignupEntity_Id(id);
//        if(a.isPresent()){
//            CommentReplyEntity entity = commentReplyRepository.findBySignupEntity_Id(id)
//                    .orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다"));
//            entity.setAlias(alias);
//            commentReplyRepository.save(entity);
//        }else {
//            System.out.println("게시물이 존재하지않습니다");
//        }
//    }
}
