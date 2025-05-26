package com.cai.helppsy.freeBulletinBoard.controller;

import com.cai.helppsy.freeBulletinBoard.entity.Bulletin;
import com.cai.helppsy.freeBulletinBoard.serviece.FreeBulletinBoardService;
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
    public String main(Model model) {
        List<Bulletin> bulletinList = freeBulletinBoardService.bulletinList();
        model.addAttribute("bulletin", bulletinList);
        return "freeBulletinBoard/mainFreeBulletin";
    }

    @PostMapping("addFreeBulletinBoard")
    public String addFreeBulletinBoard(@ModelAttribute Bulletin bulletinEntity
            , @RequestParam(value = "file", required = false) MultipartFile[] files
            , @RequestParam(value = "imgName", required = false) MultipartFile imgFile) {
        System.out.println("제목 : " + bulletinEntity.getTitle());
        System.out.println("내용 : " + bulletinEntity.getContent());
        freeBulletinBoardService.addBulletin(bulletinEntity, files, imgFile);
        return "redirect:/";
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
