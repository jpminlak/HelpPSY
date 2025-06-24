package com.cai.helppsy.likes.controller;

import com.cai.helppsy.likes.entity.FreeBulletinCommentLike;
import com.cai.helppsy.likes.entity.FreeBulletinLike;
import com.cai.helppsy.likes.entity.FreeBulletinReplyLike;
import com.cai.helppsy.likes.service.FreeBulletinLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class FreeBulletinLikesController {
    private final FreeBulletinLikesService likesService;

    @ResponseBody
    @PostMapping("bulletinLikesUpAndDown")
    public int likesUp(@ModelAttribute FreeBulletinLike freeBulletinLikes
            , @RequestParam("fkNo") int fkNo, @RequestParam("flag") boolean flag){
        if(freeBulletinLikes.getUserName().equals("")){
            freeBulletinLikes.setUserName("guest");
        }

        return likesService.bulletinLikesUp(freeBulletinLikes, fkNo, flag);
    }

    @ResponseBody
    @PostMapping("commentLikesUpAndDown")
    public int commentLikesUp(@ModelAttribute FreeBulletinCommentLike freeBulletinCommentLikes
            , @RequestParam("fkNo") int fkNo, @RequestParam("flag") boolean flag){
        if(freeBulletinCommentLikes.getUserName().equals("")){
            freeBulletinCommentLikes.setUserName("guest");
        }

        return likesService.commentLikesUp(freeBulletinCommentLikes, fkNo, flag);
    }

    @ResponseBody
    @PostMapping("ReplyLikesUp")
    public int ReplyLikesUp(@ModelAttribute FreeBulletinReplyLike freeBulletinReplyLike
            , @RequestParam("fkNo") int fkNo, @RequestParam("flag") boolean flag){
        if(freeBulletinReplyLike.getUserName().equals("")){
            freeBulletinReplyLike.setUserName("guest");
        }

        return likesService.replyLikesUp(freeBulletinReplyLike, fkNo, flag);
    }
}
