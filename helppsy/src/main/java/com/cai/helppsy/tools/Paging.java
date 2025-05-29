package com.cai.helppsy.tools;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Paging {
    // 전체 리스트를 보내서 한 페이지당 보여줄 리스트를 뽑는데 사용한다.
    public static List pagingList(int CntOfOnePage, int page, List list) {
        List responseList = new ArrayList();

        int initialValue = list.size() - 1 -(page-1)*CntOfOnePage;
        int condition = initialValue-(CntOfOnePage-1);

        for(int i = initialValue ; i >= condition && i >= 0; i--){
            responseList.add(list.get(i));
        }

        return responseList;
    }
    // 페이지 번호들의 개수를 얻는데 사용한다.
    public static int pageNumListNumCnt(int CntOfOnePage,  List list){
        int perPageNUmCnt =  perPageListCnt(CntOfOnePage, list);

        int intNUm = perPageNUmCnt/CntOfOnePage;
        double doubleNum = (double)perPageNUmCnt/CntOfOnePage;

        int repeatNum = 0;

        if(doubleNum > intNUm){
            repeatNum = intNUm+1;
        } else if (doubleNum == intNUm) {
            repeatNum = intNUm;
        }

        return repeatNum;
    }
    // 한 페이지당 보여줄 목록의 개수를 얻는데 사용한다.
    public static int perPageListCnt(int CntOfOnePage, List list){
        int cnt = list.size();

        int intNUm = cnt/CntOfOnePage;
        double doubleNum = (double)cnt/CntOfOnePage;

        int repeatNum = 0;

        if(doubleNum > intNUm){
            repeatNum = intNUm+1;
        } else if (doubleNum == intNUm) {
            repeatNum = intNUm;
        }

        return repeatNum;
    }
    // 전체 페이지의 개수를 얻는데 사용한다.
    public static List<Integer> allPages(int CntOfOnePage, List list){
        List<Integer> pages = new ArrayList<>();
        int cnt = list.size();

        int intNUm = cnt/CntOfOnePage;
        double doubleNum = (double)cnt/CntOfOnePage;

        int repeatNum = 0;

        if(doubleNum > intNUm){
            repeatNum = intNUm+1;
        } else if (doubleNum == intNUm) {
            repeatNum = intNUm;
        }

        for(int i = 0; i<repeatNum; i++){
            pages.add(i+1);
        }

        return pages;
    }


    // 한 페이지당 보여줄 페이지 번호들을 얻는데 사용한다.
    public static List<Integer> perPageNums(int CntOfOnePage, int perPageNumList, int currentPageNumListNum, List list){
        List<Integer> pages = new ArrayList<>();
        int cnt = list.size();

        int intNUm = cnt/CntOfOnePage;
        double doubleNum = (double)cnt/CntOfOnePage;

        int repeatNum = 0;

        if(doubleNum > intNUm){
            repeatNum = intNUm+1;
        } else if (doubleNum == intNUm) {
            repeatNum = intNUm;
        }
        if(repeatNum <= perPageNumList) {
            for(int i = 1; i <= repeatNum; i++){
                pages.add(i);
            }
        }else {
            int initialValue = (currentPageNumListNum-1)*perPageNumList+1;
            int condition = currentPageNumListNum*perPageNumList;

            for(int i = initialValue; i <= condition; i++){
                pages.add(i);
            }
        }

        return pages;
    }
}
