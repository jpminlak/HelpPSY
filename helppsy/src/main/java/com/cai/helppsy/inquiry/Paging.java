package com.cai.helppsy.inquiry;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Paging {

    private final QuestionRepository questionRepository;

    public static List<Question> MemberList(Integer p, List<Question> a) {
        List<Question> b = new ArrayList();

//		for(Question aa : a){
//			System.out.println(aa.getId());
//		}

        int initialValue = a.size() - 1 -(p-1)*10;
        int conditions = initialValue-9;

        for(int i = initialValue ; i>=conditions && i>=0; i--){
            b.add(a.get(i));
        }
//        for(Question bb : b){
//            System.out.println(bb.getId());
//        }
        return b;
    }

    public static List<Integer> cnt(int s){
        List<Integer> pages = new ArrayList<>();

        int a = s/10;
        double b = (double)s/10;

        int repeatNum = 0;

        if(b>a){
            repeatNum = a+1;
        } else if (b==a) {
            repeatNum = a;
        }

        for(int i = 0; i<repeatNum; i++){
            pages.add(i+1);
        }

        return pages;
    }
}
