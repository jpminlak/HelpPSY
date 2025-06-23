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
        List<FreeBulletin> bulletinList = freeBulletinService.bulletinList();
        Paging paging = new Paging();
        paging.setPerPageList(10, searchDTO.getCurrentPage(), searchDTO.getCurrentPageSetNum(), 5, bulletinList);

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
                , userName));
        model.addAttribute("isPressedBulletinLike", freeBulletinLikesService.isPressedBulletinLike(userName, no));
        model.addAttribute("fileNames", freeBulletinService.getAttachFileNames(no));

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
    @PostMapping("freeBulletin/addReply")
    public String addReply(@ModelAttribute FreeBulletinReply freeBulletinCommentInComment
            , @RequestParam(value = "fkNo") Integer fkNo) {
        if (freeBulletinCommentInComment.getWriter() == "") {
            freeBulletinCommentInComment.setWriter("guest");
        }
        freeBulletinService.addReply(freeBulletinCommentInComment, fkNo);
        return "succeeded";
    }

    @PostMapping("showReply")
    @ResponseBody
    public List<FreeBulletinReplyDTO> commentInComment(@RequestParam(value = "no") Integer no
            , @RequestParam(value = "userName", defaultValue = "guest") String useriName) {
        List<FreeBulletinReply> replyList = freeBulletinService.getCommentInComments(no);

        if (replyList == null)
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

        return freeBulletinLikesService.isPressedReplyLike(list, useriName);
    }

    //아래는 게시판 부분
    @GetMapping("deleteFreeBulletin")
    public String deleteBulletin(@RequestParam("no") int no) {
        freeBulletinService.deleteBulletin(no);
        return "redirect:/mainFreeBulletin";
    }

    @GetMapping("editFreeBullein")
    public String editFreeBullein(@RequestParam("no") Integer no, @RequestParam("userName") String userName
            , Model model) throws UnsupportedEncodingException {
        if (no == null) {
            return "redirect:/specificBulletin";
        }
        model.addAttribute("bulletinOneInfo", freeBulletinService.bulletinOne(no));
        model.addAttribute("userName", userName);
        model.addAttribute("fileNames", freeBulletinService.getAttachFileNames(no));
        return "freeBulletinBoard/updateFreeBulletin";
    }

    @PostMapping("updateFreeBulletin")
    public String updateFreeBulletin(@RequestParam("no") Integer no, @RequestParam("userName") String userName
            , Model model, @ModelAttribute FreeBulletin bulletinEntity, @RequestParam(value = "existingFileNames", defaultValue = "noFileName") List<String> existingFileNames
            , @RequestParam(value = "file", required = false) MultipartFile[] files) throws UnsupportedEncodingException {
        freeBulletinService.updateFreeBulletin(no, bulletinEntity, files, existingFileNames);
        userName = URLEncoder.encode(userName, "UTF-8");
        return "redirect:/specificBulletin?no=" + no + "&userName=" + userName;
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
//        System.out.println("_________________________11__________________________");
//        System.out.println("#"+searchWord+"#");
//        System.out.println("#"+sortingType+"#");
//        System.out.println("_________________________12__________________________");
//        System.out.println(searchWord.equals(""));
//        System.out.println(sortingType.equals(""));
//        System.out.println("_________________________13__________________________");
        List<FreeBulletinDTO> bulletinList = freeBulletinService.searchBulletin(searchDTO.getSearchWord(), searchDTO.getSortingType());
        Paging paging = new Paging();
        paging.setPerPageList(10, searchDTO.getCurrentPage(), searchDTO.getCurrentPageSetNum(), 5, bulletinList);

        System.out.println(searchDTO.getSearchWord());
        System.out.println(searchDTO.getSortingType());

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

