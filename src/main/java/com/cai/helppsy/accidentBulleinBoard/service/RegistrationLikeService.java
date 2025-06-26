package com.cai.helppsy.accidentBulleinBoard.service;


import com.cai.helppsy.accidentBulleinBoard.DTO.RegistrationLikeDTO;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationLikeEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationLikeService {
    private final RegistrationLikeRepository likeRepository;

    // 좋아요 저장 및 이미지 변환 (즉각처리)
    @Transactional // 한번 요청으로 외래키 까지 접근하지 못하여 Transactional를 사용하여 요청한번에 모두 처리
    public RegistrationLikeDTO addLike(String type, String alias, Integer registration_entity_id, int liked) {
        // RegistrationEntity 객체를 만들어 게시글 번호를 입력후 LikeEntity객체에 넣기 (조인할 id주입)
        RegistrationEntity postEntity = new RegistrationEntity();
        postEntity.setId(registration_entity_id);
        // 최초 디비 접근해서 닉네임, registration_entity_id 개사글번호 가 있으면
        Optional<RegistrationLikeEntity> likeCheck = likeRepository.findByAliasAndRegistrationEntityId(alias, registration_entity_id);
        if(likeCheck.isEmpty()){ // .isEmpty() 값이 없으면 true
            RegistrationLikeEntity likeEntity = new RegistrationLikeEntity(); // LikeEntity 객체를 만들어 클라이언트에게 넘어온 파라미터 저장
            likeEntity.setAlias(alias);
            likeEntity.setType(type);
            likeEntity.setRegistrationEntity(postEntity);
            likeEntity.setLiked(liked);
            RegistrationLikeEntity like = likeRepository.save(likeEntity); // dto에 entity 생성자 주입
            RegistrationLikeDTO dto = new RegistrationLikeDTO(like.getId(), like.getType(), like.getAlias(), like.getLiked());
            return dto;
        }else{
            likeRepository.deleteByAliasAndRegistrationEntityId(alias, registration_entity_id);
            return new RegistrationLikeDTO(0, type, alias, 0); // 0은 좋아요 취소 상태를 의미 (( 임의적으로 조건적 0 리턴)
        }
    }

    // 게시글 로드시 개인회원 별명 별 좋아요 상태 이미지 출력
    public int getLike(String alias, Integer registration_entity_id) {
        return likeRepository.findByAliasAndRegistrationEntityId(alias, registration_entity_id)
                .map(RegistrationLikeEntity::getLiked) // 조회결과가 있을시 LikeEntity의 getLiked()값을 가져옴
                .orElse(0); // 만약 좋아요 기록이 없다면(=Optional이 비어 있음), 기본값으로 0을 반환합니다.
    }

    // 좋아요 총 갯수
    public int LikeCountByPostId(Integer registration_entity_id){
        return likeRepository.countByRegistrationEntityId(registration_entity_id);
    }
}