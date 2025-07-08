package com.cai.helppsy.accidentBulleinBoard.DTO;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegistrationDTO {
    private Integer id;
    private String title;
    private String preview;
    private String alias;
    private String accident;
    private LocalDateTime createDate;
    private int postViews;
    private String profileImage;
    private String mainImg;

    public RegistrationDTO(RegistrationEntity entity, String profileImage) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.preview = entity.getPreview();
        this.alias = entity.getAlias();
        this.accident = entity.getAccident();
        this.createDate = entity.getCreateDate();
        this.postViews = entity.getPostViews();
        this.profileImage = profileImage;
        this.mainImg = entity.getMainImg();
    }
}
