package com.cai.helppsy.freeBulletinBoard.service;

import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinCommentDTO;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinAttach;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinReply;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinAttachRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinCommentRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinReplyRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FreeBulletinService {
    private final FreeBulletinRepository freeBulletinRepository;
    private final FreeBulletinAttachRepository freeBulletinAttachRepository;
    private final FreeBulletinCommentRepository freeBulletinCommentRepository;
    private final FreeBulletinReplyRepository freeBulletinReplyRepository;

    public FreeBulletin bulletinOne(int no) {
        return freeBulletinRepository.findById(no).get();
    }

    public void increaseViews(int no) {
        FreeBulletin freeBulletin = freeBulletinRepository.findById(no).get();
        freeBulletin.setViews(freeBulletin.getViews() + 1);
        freeBulletinRepository.saveAndFlush(freeBulletin); // flush는 변경사항의 동기화를 의미한다.
    }

    public List<FreeBulletin> bulletinList() {
        return freeBulletinRepository.findAll();
    }

    public void addBulletin(FreeBulletin bulletinEntity, MultipartFile[] files, String crrentUserName) {

        LocalDateTime createDate = LocalDateTime.now();
        bulletinEntity.setCreateDate(createDate);
        bulletinEntity.setViews(0);
        bulletinEntity.setWriter(crrentUserName);
        freeBulletinRepository.save(bulletinEntity);


        FreeBulletinAttach attachEntity;
        UUID uuid;
        String fileName;
        File savingInfo;
        String attachFileStoragePath = System.getProperty("user.dir") + "/files/freeBulletin";

        //첨부파일 등록
        if (files != null) {
            for (MultipartFile mf : files) {
                uuid = UUID.randomUUID();
                fileName = uuid.toString() + "_" + mf.getOriginalFilename();
                savingInfo = new File(attachFileStoragePath, fileName);
                // 위에는 파일의 저장 위치와 파일의 이름을 저장
                try {
                    mf.transferTo(savingInfo);
                } catch (Exception e) {
                    System.out.println("_____________________________");
                    System.out.println("파일 저장을 실패하였습니다.");
                    System.out.println("_____________________________");
                }
                attachEntity = new FreeBulletinAttach();
                attachEntity.setFileName(fileName);
                attachEntity.setCreateDate(createDate);
                attachEntity.setFreeBulletin(bulletinEntity);

                freeBulletinAttachRepository.save(attachEntity);
            }
        }
    }

    // 댓글

    public void addComment(FreeBulletinComment freeBulletinComment, int fkNo) {
        freeBulletinComment.setFreeBulletin(freeBulletinRepository.findById(fkNo).get());
        freeBulletinComment.setCreateDate(LocalDateTime.now());
        freeBulletinCommentRepository.save(freeBulletinComment);
    }

    public List<FreeBulletinCommentDTO> getComments(int fkNo) {
        List<FreeBulletinComment> cList = freeBulletinCommentRepository.findByFreeBulletin_no(fkNo);
        List<FreeBulletinCommentDTO> freeBulletinCommentDTOList = new ArrayList<>();
        for(int i = 0; i < cList.size(); i++){
            FreeBulletinCommentDTO freeBulletinCommentDTO = new FreeBulletinCommentDTO();
            freeBulletinCommentDTO.setNo(cList.get(i).getNo());
            freeBulletinCommentDTO.setType(cList.get(i).getType());
            freeBulletinCommentDTO.setFkNo(cList.get(i).getFreeBulletin().getNo());
            freeBulletinCommentDTO.setLikes(cList.get(i).getLikes());
            freeBulletinCommentDTO.setContent(cList.get(i).getContent());
            freeBulletinCommentDTO.setWriter(cList.get(i).getWriter());
            freeBulletinCommentDTO.setCreateDate(cList.get(i).getCreateDate());
            freeBulletinCommentDTOList.add(freeBulletinCommentDTO);
        }

        return freeBulletinCommentDTOList;
    }

    // 대댓글

    public void addCommentInComment(FreeBulletinReply freeBulletinCommentInComment, int fkNo) {
        freeBulletinCommentInComment.setFreeBulletinComment(freeBulletinCommentRepository.findById(fkNo).get());
        freeBulletinCommentInComment.setCreateDate(LocalDateTime.now());
        freeBulletinReplyRepository.save(freeBulletinCommentInComment);
    }

    public List<FreeBulletinReply> getCommentInComments(int no) {
        return freeBulletinReplyRepository.findByFreeBulletinComment_no(no);
    }

    // 게시글 삭제
    public void deleteBulletin(int no){
        freeBulletinRepository.delete(freeBulletinRepository.findById(no).get());
    }
    // 댓글 삭제
    public void deleteComment(int no){
        freeBulletinCommentRepository.delete(freeBulletinCommentRepository.findById(no).get());
    }
}
