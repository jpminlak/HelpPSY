package com.cai.helppsy.accidentBulleinBoard.service;


import com.cai.helppsy.accidentBulleinBoard.DTO.CommentLikeDTO;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentLikeEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.CommentLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Transactional // 한번 요청으로 등록,삭제 처리하기에 필요함 두 요청이 다 들어올떄 까지 대기했다 실행시킴
    // @Transactional를 안쓰려면 등록, 삭제 로직을 컨트롤러를 나눠 redirect로 컨트롤러를 돌려야함
    public CommentLikeDTO addCommentlike(String type, String alias, Integer commentId,int liked){
        // 기본키 entity 클래스 객체 생성하여 넘겨받은 파라미터(기본키)를 새 객체에 주입
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(commentId);
        // 좋아요 중복을 체크를 막기위해 해당 좋아요 테이블에 좋아요 기록이 있는지 확인하기
        Optional<CommentLikeEntity> CommentLikeCheck =
                commentLikeRepository.findByAliasAndCommentEntity_Id(alias,commentId);
        // 좋아요 기록이 없다면
        if(CommentLikeCheck.isEmpty()){
            // entity객체 생성하여 db에 저장
            CommentLikeEntity commentLikeEntity = new CommentLikeEntity();
            commentLikeEntity.setType(type);
            commentLikeEntity.setAlias(alias);
            commentLikeEntity.setCommentEntity(commentEntity); //외래키값
            commentLikeEntity.setLiked(liked);
            CommentLikeEntity CLike = commentLikeRepository.save(commentLikeEntity);
//            System.out.println("-----------------여기확인");
//            System.out.println(commentLikeEntity.getType());
//            System.out.println(commentLikeEntity.getAlias());
//            System.out.println(commentLikeEntity.getCommentEntity());
//            System.out.println(commentLikeEntity.getLiked());
            // 이후 DTO에 저장하여 DTO를 리턴
            CommentLikeDTO commentLikeDTO =
                    new CommentLikeDTO(CLike.getId(),CLike.getType(),
                            CLike.getAlias(),CLike.getLiked());
            return commentLikeDTO;
        }else {
            commentLikeRepository.deleteByAliasAndCommentEntity_Id(alias,commentId);
            return new CommentLikeDTO(0, type, alias, 0); // 0은 좋아요 취소 상태를 의미 (( 임의적으로 조건적 0 리턴)
        }
    }

    // 댓글 별 좋아요 상태 이미지 출력
    public int getCommentLike(String alias, Integer commentId) {
        return commentLikeRepository.findByAliasAndCommentEntity_Id(alias, commentId)
                .map(CommentLikeEntity::getLiked) // 조회결과가 있을시 LikeEntity의 getLiked()값을 가져옴
                .orElse(0); // 만약 좋아요 기록이 없다면(=Optional이 비어 있음), 기본값으로 0을 반환합니다.
    }

    // 총아요 총 갯수 가져오기
    public int LikeCountByPostId(Integer commentId){
        return commentLikeRepository.countByCommentEntity_Id(commentId);
    }

}
