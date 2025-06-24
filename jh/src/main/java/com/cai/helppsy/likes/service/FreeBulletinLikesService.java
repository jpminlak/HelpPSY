package com.cai.helppsy.likes.service;

import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinCommentDTO;
import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinReplyDTO;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinReply;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinCommentRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinReplyRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import com.cai.helppsy.likes.entity.FreeBulletinCommentLike;
import com.cai.helppsy.likes.entity.FreeBulletinLike;
import com.cai.helppsy.likes.entity.FreeBulletinReplyLike;
import com.cai.helppsy.likes.repository.FreeBulletinCommentLikeRepository;
import com.cai.helppsy.likes.repository.FreeBulletinLikeRepository;
import com.cai.helppsy.likes.repository.FreeBulletinReplyLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FreeBulletinLikesService {
    private final FreeBulletinRepository freeBulletinRepository;
    private final FreeBulletinLikeRepository freeBulletinLikesRepository;
    private final FreeBulletinCommentRepository freeBulletinCommentRepository;
    private final FreeBulletinCommentLikeRepository freeBulletinCommentLikesRepository;
    private final FreeBulletinReplyRepository freeBulletinReplyRepository;
    private final FreeBulletinReplyLikeRepository freeBulletinReplyLikeRepository;

    public int bulletinLikesUp(FreeBulletinLike freeBulletinLikes, int fkNo, boolean flag) {

        int cnt;
        FreeBulletin bulletin = freeBulletinRepository.findById(fkNo).get();

        if (flag) {
            // 게시글 태이블의 튜플에 좋아요 갱신
            bulletin.setLikes(bulletin.getLikes() + 1);
            freeBulletinRepository.saveAndFlush(bulletin);
            cnt = bulletin.getLikes();
            // 외래키 지정
            freeBulletinLikes.setFreeBulletin(bulletin);

            // 좋아요 태이블의 튜플에 insert
            freeBulletinLikesRepository.save(freeBulletinLikes);

        } else {
            bulletin.setLikes(bulletin.getLikes() - 1);
            freeBulletinRepository.saveAndFlush(bulletin);
            cnt = bulletin.getLikes();
            // 좋아요 태이블에서 삭제
            freeBulletinLikesRepository.delete(freeBulletinLikesRepository.findByFreeBulletin_noAndUserName(fkNo, freeBulletinLikes.getUserName()));
        }

        return cnt;
    }

    // 해당 게시물에 좋아요를 눌렀었는지 여부
    public int isPressedBulletinLike(String userName, int no) {
        FreeBulletinLike freeBulletinLikes = freeBulletinLikesRepository.findByFreeBulletin_noAndUserName(no, userName);

        if (freeBulletinLikes != null) {
            return 1;
        } else
            return 0;
    }


    public int commentLikesUp(FreeBulletinCommentLike freeBulletinCommentLikes, int fkNo, boolean flag) {
        System.out.println("___________falg_____________");
        System.out.println(flag);
        System.out.println("___________falg_____________");

        int cnt;

        FreeBulletinComment freeBulletinComment = freeBulletinCommentRepository.findById(fkNo).get();

        if (flag) {
            // 댓글 태이블의 튜플에 좋아요 갱신
            freeBulletinComment.setLikes(freeBulletinComment.getLikes() + 1);
            freeBulletinCommentRepository.saveAndFlush(freeBulletinComment);
            cnt = freeBulletinComment.getLikes();

            // 외래키 지정
            freeBulletinCommentLikes.setFreeBulletinComment(freeBulletinComment);

            // 좋아요 태이블의 튜플에 insert
            freeBulletinCommentLikesRepository.save(freeBulletinCommentLikes);

        } else {
            // 게시글 태이블의 튜플에 졸아요 갱신
            freeBulletinComment.setLikes(freeBulletinComment.getLikes() - 1);
            freeBulletinCommentRepository.saveAndFlush(freeBulletinComment);
            cnt = freeBulletinComment.getLikes();
            // 좋아요 태이블에서 삭제
            System.out.println(fkNo);
            System.out.println(freeBulletinCommentLikes.getUserName());
            System.out.println("_________________________1_________________________________");
            if (freeBulletinCommentLikesRepository.findByFreeBulletinComment_noAndUserName(fkNo, freeBulletinCommentLikes.getUserName()) != null)
                System.out.println("null 아님");
            else
                System.out.println("null임");
            System.out.println("_________________________2_________________________________");
            freeBulletinCommentLikesRepository.delete(freeBulletinCommentLikesRepository.findByFreeBulletinComment_noAndUserName(fkNo, freeBulletinCommentLikes.getUserName()));
        }

        return cnt;
    }

    // 해당 댓글에 좋아요를 눌렀었는지 여부를 댓글에 설정
    public List<FreeBulletinCommentDTO> isPressedCommentLike(List<FreeBulletinCommentDTO> freeBulletinCommentDTOList, String userName) {

        for (int i = 0; i < freeBulletinCommentDTOList.size(); i++) {
            FreeBulletinCommentLike freeBulletinCommentLikes
                    = freeBulletinCommentLikesRepository.findByFreeBulletinComment_noAndUserName(freeBulletinCommentDTOList.get(i).getNo(), userName);

            if (freeBulletinCommentLikes != null) {
                freeBulletinCommentDTOList.get(i).setIsPressedCommentLike(1);
            } else {
                freeBulletinCommentDTOList.get(i).setIsPressedCommentLike(0);
            }

        }
        return freeBulletinCommentDTOList;
    }

    // 대댓글 좋아요

    public int replyLikesUp(FreeBulletinReplyLike freeBulletinReplyLike, int fkNo, boolean flag) {
        System.out.println("___________falg_____________");
        System.out.println(flag);
        System.out.println("___________falg_____________");
        FreeBulletinReply freeBulletinReply = freeBulletinReplyRepository.findById(fkNo).get();

        int cnt;

        if (flag) {
            // 댓글 태이블의 튜플에 좋아요 갱신
            freeBulletinReply.setLikes(freeBulletinReply.getLikes() + 1);
            freeBulletinReplyRepository.saveAndFlush(freeBulletinReply);
            cnt = freeBulletinReply.getLikes();

            // 외래키 지정
            freeBulletinReplyLike.setFreeBulletinReply(freeBulletinReply);

            // 좋아요 태이블의 튜플에 insert
            freeBulletinReplyLikeRepository.save(freeBulletinReplyLike);

        } else {
            // 게시글 태이블의 튜플에 졸아요 갱신
            freeBulletinReply.setLikes(freeBulletinReply.getLikes() - 1);
            freeBulletinReplyRepository.saveAndFlush(freeBulletinReply);
            cnt = freeBulletinReply.getLikes();
            // 좋아요 태이블에서 삭제
            System.out.println(fkNo);
            System.out.println(freeBulletinReplyLike.getUserName());
            System.out.println("_________________________1_________________________________");
            if (freeBulletinReplyLikeRepository.findByFreeBulletinReply_noAndUserName(fkNo, freeBulletinReplyLike.getUserName()) != null)
                System.out.println("null 아님");
            else
                System.out.println("null임");
            System.out.println("_________________________2_________________________________");
            freeBulletinReplyLikeRepository.delete(freeBulletinReplyLikeRepository.findByFreeBulletinReply_noAndUserName(fkNo, freeBulletinReplyLike.getUserName()));
        }

        return cnt;
    }

    // 해당 대댓글에 좋아요를 눌렀었는지 여부를 댓글에 설정
    public List<FreeBulletinReplyDTO> isPressedReplyLike(List<FreeBulletinReplyDTO> freeBulletinReplyDTOList, String userName) {

        for (int i = 0; i < freeBulletinReplyDTOList.size(); i++) {
            FreeBulletinReplyLike FreeBulletinReplyLike
                    = freeBulletinReplyLikeRepository.findByFreeBulletinReply_noAndUserName(freeBulletinReplyDTOList.get(i).getNo(), userName);

            if (FreeBulletinReplyLike != null) {
                freeBulletinReplyDTOList.get(i).setIsPressedReplyLike(1);
            } else {
                freeBulletinReplyDTOList.get(i).setIsPressedReplyLike(0);
            }

        }
        return freeBulletinReplyDTOList;
    }
}
