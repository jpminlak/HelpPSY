package com.cai.helppsy.freeBulletinBoard.controller;

import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinDTO;
import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinReplyDTO;
import com.cai.helppsy.freeBulletinBoard.dto.SearchDTO;
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
    public String mainFreeBulletin(Model model, @ModelAttribute SearchDTO searchDTO) {
        List<FreeBulletinDTO> bulletinList = freeBulletinService.bulletinList();
        Paging paging = new Paging();
        paging.setPerPageList(15, searchDTO.getCurrentPage(), searchDTO.getCurrentPageSetNum(), 5, bulletinList);

        model.addAttribute("perPageList", paging.getPerPageList());
        model.addAttribute("allPageNumCnt", paging.getAllPageNumCnt());
        model.addAttribute("currentPageNums", paging.getCurrentPageNums());
        model.addAttribute("perPageNumCnt", 5);
        model.addAttribute("pageNumSetCnt", paging.getPageNumSetCnt());
        model.addAttribute("searchListOrDefaultList", "normal");
        model.addAttribute("requestName", "mainFreeBulletin");
        model.addAttribute("searchDTO", searchDTO);
        return "freeBulletinBoard/mainFreeBulletin";
    }

    @PostMapping("addFreeBulletin")
    public String addFreeBulletinBoard(@ModelAttribute FreeBulletin bulletinEntity
            , @RequestParam(value = "file", required = false) MultipartFile[] files) {
        freeBulletinService.addBulletin(bulletinEntity, files);
        return "redirect:/mainFreeBulletin";
    }

    @GetMapping("specificBulletin")
    public String specificBulletin(Model model, @RequestParam("no") Integer no
            , @RequestParam(value = "userId", defaultValue = "") String userId) {
        freeBulletinService.increaseViews(no);
        model.addAttribute("bulletinOne", freeBulletinService.bulletinOne(no));
        model.addAttribute("commentList", freeBulletinLikesService.isPressedCommentLike(freeBulletinService.getComments(no)
                , userId));
        model.addAttribute("isPressedBulletinLike", freeBulletinLikesService.isPressedBulletinLike(userId, no));
        model.addAttribute("fileNames", freeBulletinService.getAttachFileNames(no));
        model.addAttribute("userIdFromController", userId);

        return "freeBulletinBoard/specificFreeBulletin";
    }

    @ResponseBody
    @PostMapping("freeBulletin/addComment")
    public String addComment(@ModelAttribute FreeBulletinComment freeBulletinComment
            , @RequestParam(value = "fkNo") Integer fkNo) {
        if (freeBulletinComment.getUserId().equals("")) {
            freeBulletinComment.setUserId("guest");
        }
        freeBulletinService.addComment(freeBulletinComment, fkNo);
        return "succeeded";
    }

    @ResponseBody
    @PostMapping("freeBulletin/addReply")
    public String addReply(@ModelAttribute FreeBulletinReply freeBulletinCommentInComment
            , @RequestParam(value = "fkNo") Integer fkNo) {
        if (freeBulletinCommentInComment.getUserId().equals("")) {
            freeBulletinCommentInComment.setUserId("guest");
        }
        freeBulletinService.addReply(freeBulletinCommentInComment, fkNo);
        return "succeeded";
    }

    @PostMapping("showReply")
    @ResponseBody
    public List<FreeBulletinReplyDTO> commentInComment(@RequestParam(value = "no") Integer no
            , @RequestParam(value = "userId", defaultValue = "guest") String userId) {
        return freeBulletinLikesService.isPressedReplyLike(freeBulletinService.getCommentInComments(no), userId);
    }

    //아래는 게시판 부분
    @GetMapping("deleteFreeBulletin")
    public String deleteBulletin(@RequestParam("no") int no) {
        freeBulletinService.deleteBulletin(no);
        return "redirect:/mainFreeBulletin";
    }

    @GetMapping("editFreeBullein")
    public String editFreeBullein(@RequestParam("no") Integer no, @RequestParam("userId") String userId
            , Model model) throws UnsupportedEncodingException {
        if (no == null) {
            return "redirect:/specificBulletin";
        }
        model.addAttribute("bulletinOneInfo", freeBulletinService.bulletinOne(no));
        model.addAttribute("userId", userId);
        model.addAttribute("fileNames", freeBulletinService.getAttachFileNames(no));
        return "freeBulletinBoard/updateFreeBulletin";
    }

    @PostMapping("updateFreeBulletin")
    public String updateFreeBulletin(Model model, @ModelAttribute FreeBulletin bulletinEntity, @RequestParam(value = "existingFileNames", defaultValue = "noFileName") List<String> existingFileNames
            , @RequestParam(value = "file", required = false) MultipartFile[] files) throws UnsupportedEncodingException {
        freeBulletinService.updateFreeBulletin(bulletinEntity.getNo(), bulletinEntity, files, existingFileNames);
        bulletinEntity.setUserId(URLEncoder.encode(bulletinEntity.getUserId(), "UTF-8"));
        return "redirect:/specificBulletin?no=" + bulletinEntity.getNo() + "&userId=" + bulletinEntity.getUserId();
    }

    // 아래는 댓글 부분
    @ResponseBody
    @GetMapping("deleteFreeBulletinComment")
    public String deleteFreeBulletinComment(@RequestParam("no") int no) {
        freeBulletinService.deleteComment(no);
        return "succeeeded";
    }

    @ResponseBody
    @PostMapping("editFreeBulleinComment")
    public String editFreeBulleinComment(@RequestParam("content") String content, @RequestParam("no") int no
    ) throws UnsupportedEncodingException {
        freeBulletinService.editFreeBulleinComment(no, content);
        return "success";
    }

    @ResponseBody
    @PostMapping("editFreeBulleinReply")
    public String editFreeBulleinReply(@RequestParam("content") String content, @RequestParam("no") int no
    ) throws UnsupportedEncodingException {
        freeBulletinService.editFreeBulleinReply(no, content);
        return "success";
    }

    @ResponseBody
    @GetMapping("deleteFreeBulletinReply")
    public String deleteFreeBulletinReply(@RequestParam("no") Integer no) {
        if (no != null) {
            freeBulletinService.deleteFreeBulletinReply(no);
            return "succeeded";
        } else {
            return "failed";
        }
    }

    // 아래는 검색 기능
    @GetMapping("searchFreeBulletin")
    public String searchBulletin(Model model, @ModelAttribute SearchDTO searchDTO){

//        System.out.println("______________________99______________________");
//        System.out.println(searchDTO.getSearchWord());
//        System.out.println(searchDTO.getSortingType());
//        System.out.println("______________________99______________________");

        List<FreeBulletinDTO> bulletinList = freeBulletinService.searchBulletin(searchDTO);
        Paging paging = new Paging();
        paging.setPerPageList(15, searchDTO.getCurrentPage(), searchDTO.getCurrentPageSetNum(), 5, bulletinList);


        model.addAttribute("perPageList", paging.getPerPageList());
        model.addAttribute("allPageNumCnt", paging.getAllPageNumCnt());
        model.addAttribute("currentPageNums", paging.getCurrentPageNums());
        model.addAttribute("perPageNumCnt", 5);
        model.addAttribute("pageNumSetCnt", paging.getPageNumSetCnt());
        model.addAttribute("searchListOrDefaultList", "search");
        model.addAttribute("requestName", "searchFreeBulletin");
        model.addAttribute("searchDTO", searchDTO);
        return "freeBulletinBoard/mainFreeBulletin";
    }
}

