package com.cai.helppsy.freeBulletinBoard.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String searchWord = "";
    private String sortingType = "";
    private String descOrAsc = "";
    private int currentPage = 1;
    private int currentPageSetNum = 1;
}
