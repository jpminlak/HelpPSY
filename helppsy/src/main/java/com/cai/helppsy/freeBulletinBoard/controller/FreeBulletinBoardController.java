package com.cai.helppsy.freeBulletinBoard.controller;

import com.cai.helppsy.freeBulletinBoard.entity.Bulletin;
import com.cai.helppsy.freeBulletinBoard.serviece.FreeBulletinBoardService;
import com.cai.helppsy.tools.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FreeBulletinBoardController {
    private final FreeBulletinBoardService freeBulletinBoardService;

    @GetMapping("addFreeBulletin")
    public String addFreeBulletin() {
        return "freeBulletinBoard/newFreeBulletin";
    }

    @GetMapping("mainFreeBulletin")
    public String main(Model model, @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "currentPages", defaultValue = "1") int currentPages) {
        List<Bulletin> bulletinList = freeBulletinBoardService.bulletinList();
        model.addAttribute("bulletin", Paging.pagingList(10, page, bulletinList));
        model.addAttribute("numList", Paging.perPageNums(10, 5, currentPages, bulletinList));
        model.addAttribute("allPages", Paging.allPages(10, bulletinList));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentPages", currentPages);
        model.addAttribute("pageNumListNumCnt", Paging.pageNumListNumCnt(10, bulletinList));
        return "freeBulletinBoard/mainFreeBulletin";
    }

    @PostMapping("addFreeBulletinBoard")
    public String addFreeBulletinBoard(@ModelAttribute Bulletin bulletinEntity
            , @RequestParam(value = "file", required = false) MultipartFile[] files
            , @RequestParam(value = "imgName", required = false) MultipartFile imgFile) {
        freeBulletinBoardService.addBulletin(bulletinEntity, files, imgFile);
        return "redirect:/mainFreeBulletin";
    }

    @GetMapping("specificBulletin")
    public String specificBulletin(Model model, @RequestParam("no") Integer no){
        Bulletin bulletinOne = freeBulletinBoardService.bulletinOne(no);
        String imgName = freeBulletinBoardService.findImgName(no);
        model.addAttribute("bulletinOne", bulletinOne);
        model.addAttribute("imgName", imgName);
        return "freeBulletinBoard/specificFreeBulletin";
    }
}
