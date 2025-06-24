package com.cai.helppsy.freeBulletinBoard.controller;

import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinReplyDTO;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinReply;
import com.cai.helppsy.freeBulletinBoard.service.FreeBulletinService;
import com.cai.helppsy.likes.service.FreeBulletinLikesService;
import com.cai.helppsy.tools.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class FreeBulletinController {
    private final FreeBulletinService freeBulletinService;
    private final FreeBulletinLikesService freeBulletinLikesService;

    @GetMapping("addFreeBulletinMain")
    public String addFreeBulletin() {
        return "freeBulletinBoard/newFreeBulletin";
    }

    @GetMapping("mainFreeBulletin")
    public String mainFreeBulletin(Model model, @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "currentPageNum", defaultValue = "1") int currentPageNum) {
        List<FreeBulletin> bulletinList = freeBulletinService.bulletinList();
        Paging paging = new Paging();
        paging.setPerPageList(10, page, currentPageNum, 5, bulletinList);

        model.addAttribute("perPageList", paging.getPerPageList());
        model.addAttribute("allPageNumCnt", paging.getAllPageNumCnt());
        model.addAttribute("currentPageNums", paging.getCurrentPageNums());
        model.addAttribute("currentPage", page);
        model.addAttribute("currentPageNum", currentPageNum);
        model.addAttribute("pageNumSetCnt", paging.getPageNumSetCnt());
        return "freeBulletinBoard/mainFreeBulletin";
    }

    @PostMapping("addFreeBulletin")
    public String addFreeBulletinBoard(@ModelAttribute FreeBulletin bulletinEntity
            , @RequestParam(value = "file", required = false) MultipartFile[] files
            , @RequestParam(value = "currentUserName", defaultValue = "guest") String currentUserName) {
        freeBulletinService.addBulletin(bulletinEntity, files, currentUserName);
        return "redirect:/mainFreeBulletin";
    }

    @GetMapping("specificBulletin")
    public String specificBulletin(Model model, @RequestParam("no") Integer no
            , @RequestParam(value = "userName", defaultValue = "guest") String userName) {
        freeBulletinService.increaseViews(no);
        FreeBulletin bulletinOne = freeBulletinService.bulletinOne(no);
        model.addAttribute("bulletinOne", bulletinOne);
        model.addAttribute("commentList", freeBulletinLikesService.isPressedCommentLike(freeBulletinService.getComments(no)
                ,  userName));
        model.addAttribute("isPressedBulletinLike", freeBulletinLikesService.isPressedBulletinLike(userName, no));

        return "freeBulletinBoard/specificFreeBulletin";
    }

    @ResponseBody
    @PostMapping("freeBulletin/addComment")
    public String addComment(@ModelAttribute FreeBulletinComment freeBulletinComment
            , @RequestParam(value = "fkNo") Integer fkNo) {
        if (freeBulletinComment.getWriter() == "") {
            freeBulletinComment.setWriter("guest");
        }
        freeBulletinService.addComment(freeBulletinComment, fkNo);
        return "succeeded";
    }

    @ResponseBody
    @PostMapping("freeBulletin/addCommentInComment")
    public String addCommentInComment(@ModelAttribute FreeBulletinReply freeBulletinCommentInComment
            , @RequestParam(value = "fkNo") Integer fkNo) {
        if (freeBulletinCommentInComment.getWriter() == "") {
            freeBulletinCommentInComment.setWriter("guest");
        }
        freeBulletinService.addCommentInComment(freeBulletinCommentInComment, fkNo);
        return "succeeded";
    }

    @PostMapping("showReply")
    @ResponseBody
    public List<FreeBulletinReplyDTO> commentInComment(@RequestParam(value = "no") Integer no
            , @RequestParam(value = "userName", defaultValue = "guest") String useriName) {
        List<FreeBulletinReply> replyList = freeBulletinService.getCommentInComments(no);

        if(replyList == null)
            return null;

        List<FreeBulletinReplyDTO> list = new ArrayList<>();

        for (int i = 0; i < replyList.size(); i++) {
            FreeBulletinReplyDTO freeBulletinReplyDTO = new FreeBulletinReplyDTO();
            freeBulletinReplyDTO.setNo(replyList.get(i).getNo());
            freeBulletinReplyDTO.setFkNo(replyList.get(i).getFreeBulletinComment().getNo());
            freeBulletinReplyDTO.setType(replyList.get(i).getType());
            freeBulletinReplyDTO.setWriter(replyList.get(i).getWriter());
            freeBulletinReplyDTO.setLikes(replyList.get(i).getLikes());
            freeBulletinReplyDTO.setContent(replyList.get(i).getContent());
            freeBulletinReplyDTO.setCreateDate(replyList.get(i).getCreateDate());
            list.add(freeBulletinReplyDTO);
        }

        for (FreeBulletinReplyDTO fbrDTO : list){
            System.out.println(fbrDTO.getNo());
            System.out.println(fbrDTO.getLikes());
            System.out.println(fbrDTO.getType());
            System.out.println(fbrDTO.getFkNo());
            System.out.println(fbrDTO.getContent());
            System.out.println(fbrDTO.getWriter());
        }

        return freeBulletinLikesService.isPressedReplyLike(list, useriName);
    }
    @GetMapping("deleteBulletin")
    public String deleteBulletin(@RequestParam("no") int no){
        freeBulletinService.deleteBulletin(no);
        return "redirect:/mainFreeBulletin";
    }
    @GetMapping("deleteComment")
    public String deleteComment(@RequestParam("no") int no, @RequestParam("bulletinNo") int bulletinNo
            , @RequestParam("userName") String userName) throws UnsupportedEncodingException {
        freeBulletinService.deleteComment(no);
        userName = URLEncoder.encode(userName, "UTF-8");
        return "redirect:/specificBulletin?no="+bulletinNo+"&userName="+userName;
    }
}