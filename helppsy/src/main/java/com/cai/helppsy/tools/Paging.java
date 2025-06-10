package com.cai.helppsy.tools;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Paging {
    private List perPageList;
    private List<Integer> allPageNums;
    private int allPageNumCnt;
    private List<Integer> currentPageNums;
    private int pageNumSetCnt;
    
    public List getPerPageList(){return perPageList;}
    public List<Integer> getAllPageNums(){return allPageNums;}
    public int getAllPageNumCnt(){return allPageNumCnt;}
    public List<Integer> getCurrentPageNums(){return currentPageNums;}
    public int getPageNumSetCnt(){return pageNumSetCnt;}

    public void setPerPageList(int perPageListCnt, int pageNum, int currentPageNumsetNum, int perPageNumCnt, List list){
        List perPageList = new ArrayList<>();
        int cnt = 0;

        int initialValue = list.size() - 1 -(pageNum-1)*perPageListCnt;
        int condition = initialValue-perPageListCnt;

        for(int i = initialValue ; i > condition && i >= 0; i--){
            perPageList.add(list.get(i));
        }

        this.perPageList = perPageList;

        double a = list.size()/(double)perPageListCnt;
        int b = (int)a;

        if(a == b){
            allPageNumCnt = b;
        }else if(a > b){
            allPageNumCnt = ++b;
        }

        allPageNums = new ArrayList<>();

        for(int i = 0; i < allPageNumCnt; i++){
            allPageNums.add(i+1);
        }
        
        double c = allPageNumCnt/(double)perPageNumCnt;
        int d = (int)c;
        
        if(c == d){
            pageNumSetCnt = d;
        }else if(c > d){
            pageNumSetCnt = ++d;
        }

        currentPageNums = new ArrayList<>();

        int currentPageNumInitialValue = (currentPageNumsetNum-1)*perPageNumCnt+1;
        int currentPageNumConditionV = currentPageNumsetNum*perPageNumCnt;

        System.out.println("______________________________55_________________________________");
        System.out.println(pageNumSetCnt);
        System.out.println(perPageNumCnt);
        System.out.println(currentPageNumInitialValue);
        System.out.println(currentPageNumConditionV);
        System.out.println("______________________________55_________________________________");

        for(int i = currentPageNumInitialValue; i <= currentPageNumConditionV && i <= allPageNumCnt; i++){
            currentPageNums.add(i);
            System.out.println(i);
        }
    }

}
