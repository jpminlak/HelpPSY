package com.cai.helppsy.accidentBulleinBoard.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // @RequestBody를 역직렬화할 때 사용 이 어노테이션 사용시 변수명만 맞춰주면 자동매핑
public class ReplyLikeDTO {
    // @JsonProperty("replyId") // JSON에선 replyId지만, Java에선 id
    // 명칭을 맞추고 싶지 않다면 해당 변수에 @JsonProperty 어노테이션 입력 하여 설정
    private Integer id; // 저장 번호
    private String type; // 해당 게시물 타입
    private String alias; // 로그인한 사용자 별명
    private int liked; // 좋아요 현재 상태

    public ReplyLikeDTO(Integer id, String type, String alias, int liked){
        this.id=id;
        this.type=type;
        this.alias=alias;
        this.liked=liked;
    }
}
