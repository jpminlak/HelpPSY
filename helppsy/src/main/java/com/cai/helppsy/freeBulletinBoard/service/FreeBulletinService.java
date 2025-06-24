package com.cai.helppsy.freeBulletinBoard.service;

import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinCommentDTO;
import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinDTO;
import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinReplyDTO;
import com.cai.helppsy.freeBulletinBoard.dto.SearchDTO;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinAttach;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinReply;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinAttachRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinCommentRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinReplyRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import com.cai.helppsy.memberManager.signupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FreeBulletinService {
    private final FreeBulletinRepository freeBulletinRepository;
    private final FreeBulletinAttachRepository freeBulletinAttachRepository;
    private final FreeBulletinCommentRepository freeBulletinCommentRepository;
    private final FreeBulletinReplyRepository freeBulletinReplyRepository;
    private final signupRepository signupRepository;


    public FreeBulletinDTO bulletinOne(int no) {
        FreeBulletin freeBulletin = freeBulletinRepository.findById(no).get();
        FreeBulletinDTO freeBulletinDTO = new FreeBulletinDTO();

        freeBulletinDTO.setNo(freeBulletin.getNo());
        freeBulletinDTO.setTitle(freeBulletin.getTitle());
        freeBulletinDTO.setViews(freeBulletin.getViews());
        freeBulletinDTO.setContent(freeBulletin.getContent());
        freeBulletinDTO.setCreateDate(freeBulletin.getCreateDate());
        freeBulletinDTO.setWriter(freeBulletin.getWriter());
        freeBulletinDTO.setProfileImgName(signupRepository.findByAlias(freeBulletin.getWriter()).getProfileImage());
        freeBulletinDTO.setThumbnail(freeBulletin.getThumbnail());
        freeBulletinDTO.setLikes(freeBulletin.getLikes());
        return freeBulletinDTO;
    }

    public void increaseViews(int no) {
        FreeBulletin freeBulletin = freeBulletinRepository.findById(no).get();
        freeBulletin.setViews(freeBulletin.getViews() + 1);
        freeBulletinRepository.saveAndFlush(freeBulletin); // flush는 변경사항의 동기화를 의미한다.
    }

    public List<FreeBulletin> bulletinList() {
        return freeBulletinRepository.findAll();
    }

    @Transactional
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

    // 첨부파일 이름 가져오기
    public List<String> getAttachFileNames(int no) {
        List<FreeBulletinAttach> freeBulletinAttacheList = freeBulletinAttachRepository.findByFreeBulletin_no(no);
        List<String> attachFileName = new ArrayList<>();

        if (freeBulletinAttacheList != null)
            for (FreeBulletinAttach fb : freeBulletinAttacheList)
                attachFileName.add(fb.getFileName());
        return attachFileName;
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
        for (FreeBulletinComment fbc : cList) {
            FreeBulletinCommentDTO freeBulletinCommentDTO = new FreeBulletinCommentDTO();
            freeBulletinCommentDTO.setNo(fbc.getNo());
            freeBulletinCommentDTO.setType(fbc.getType());
            freeBulletinCommentDTO.setFkNo(fbc.getFreeBulletin().getNo());
            freeBulletinCommentDTO.setLikes(fbc.getLikes());
            freeBulletinCommentDTO.setContent(fbc.getContent());
            freeBulletinCommentDTO.setWriter(fbc.getWriter());
            freeBulletinCommentDTO.setCreateDate(fbc.getCreateDate());
            freeBulletinCommentDTO.setProfileImgName(signupRepository.findByAlias(fbc.getWriter()).getProfileImage());
            freeBulletinCommentDTOList.add(freeBulletinCommentDTO);
        }

        return freeBulletinCommentDTOList;
    }

    // 대댓글

    public void addReply(FreeBulletinReply freeBulletinCommentInComment, int fkNo) {
        freeBulletinCommentInComment.setFreeBulletinComment(freeBulletinCommentRepository.findById(fkNo).get());
        freeBulletinCommentInComment.setCreateDate(LocalDateTime.now());
        freeBulletinReplyRepository.save(freeBulletinCommentInComment);
    }

    public List<FreeBulletinReplyDTO> getCommentInComments(Integer no) {
        List<FreeBulletinReply> replyList = freeBulletinReplyRepository.findByFreeBulletinComment_no(no);

        if (replyList == null)
            return null;

        List<FreeBulletinReplyDTO> list = new ArrayList<>();

        for (FreeBulletinReply fbr : replyList) {
            FreeBulletinReplyDTO freeBulletinReplyDTO = new FreeBulletinReplyDTO();
            freeBulletinReplyDTO.setNo(fbr.getNo());
            freeBulletinReplyDTO.setFkNo(fbr.getFreeBulletinComment().getNo());
            freeBulletinReplyDTO.setType(fbr.getType());
            freeBulletinReplyDTO.setWriter(fbr.getWriter());
            freeBulletinReplyDTO.setLikes(fbr.getLikes());
            freeBulletinReplyDTO.setContent(fbr.getContent());
            freeBulletinReplyDTO.setCreateDate(fbr.getCreateDate());
            freeBulletinReplyDTO.setProfileImgName(signupRepository.findByAlias(fbr.getWriter()).getProfileImage());
            list.add(freeBulletinReplyDTO);
        }
        return list;

    }

    // 게시글 삭제
    public void deleteBulletin(int no) {
        freeBulletinRepository.delete(freeBulletinRepository.findById(no).get());
    }

    // 댓글 삭제
    public void deleteComment(int no) {
        freeBulletinCommentRepository.delete(freeBulletinCommentRepository.findById(no).get());
    }

    // 게시글 업데이트
    @Transactional
    public void updateFreeBulletin(Integer no, FreeBulletin bulletin, MultipartFile[] files, List<String> existinFileNames) {
        Optional<FreeBulletin> beforeOptionalBulletin = freeBulletinRepository.findById(no);
        if (beforeOptionalBulletin.isPresent()) {
            FreeBulletin beforeFreeBulletin = beforeOptionalBulletin.get();
            beforeFreeBulletin.setContent(bulletin.getContent());
            beforeFreeBulletin.setTitle(bulletin.getTitle());
            beforeFreeBulletin.setThumbnail(bulletin.getThumbnail());
            freeBulletinRepository.saveAndFlush(beforeFreeBulletin);

            FreeBulletinAttach attachEntity;
            UUID uuid;
            String fileName;
            File savingInfo;
            String attachFileStoragePath = System.getProperty("user.dir") + "/files/freeBulletin";

            // 기존의 파일 및 파일 정보 삭제
            List<FreeBulletinAttach> freeBulletinAttach = freeBulletinAttachRepository.findByFreeBulletin_no(no);
            for (FreeBulletinAttach fba : freeBulletinAttach) {
//                System.out.println("____________________________________99___________________________________");
//                System.out.println("기존에 존재하던 파일명"+fba.getFileName());
//                for(String s : existinFileNames)
//                    System.out.println("지우지 말아야 할 파일명"+s);
//                System.out.println(!existinFileNames.contains(fba.getFileName()));
//                System.out.println("____________________________________99___________________________________");
                if (!existinFileNames.contains(fba.getFileName())) {
//                    System.out.println("____________________________________100___________________________________");
//                    System.out.println("기존에 존재하던 파일명"+fba.getFileName());
//                    System.out.println("지우지 말아야 할 파일명"+existinFileNames.get(0));
//                    System.out.println("____________________________________100___________________________________");
                    File file = new File(fba.getFileName());
                    file.delete();
                    freeBulletinAttachRepository.deleteByFileName(fba.getFileName());
                }
            }

            //첨부파일 추가
            if (files != null) {
                for (MultipartFile mf : files) {
                    uuid = UUID.randomUUID();
                    fileName = uuid.toString() + "_" + mf.getOriginalFilename();
                    savingInfo = new File(attachFileStoragePath, fileName);
                    // 위에는 파일의 저장 위치와 파일의 이름을 저장
                    try {
                        mf.transferTo(savingInfo);
                        attachEntity = new FreeBulletinAttach();
                        attachEntity.setFileName(fileName);
                        attachEntity.setCreateDate(LocalDateTime.now());
                        attachEntity.setFreeBulletin(beforeFreeBulletin);
                        freeBulletinAttachRepository.save(attachEntity);
                    } catch (Exception e) {
                        System.out.println("_____________________________");
                        System.out.println("파일 저장을 실패하였습니다.");
                        System.out.println("_____________________________");
                    }

                }
            }
        } else {
            // 나중에 수정 실패 페이지 만들어서 거기로 보내는 로직 구현하기
        }
    }

    public void editFreeBulleinComment(int no, String content) {
        FreeBulletinComment freeBulletinComment = freeBulletinCommentRepository.findById(no).get();
        freeBulletinComment.setContent(content);
        freeBulletinCommentRepository.saveAndFlush(freeBulletinComment);
    }

    public void editFreeBulleinReply(int no, String content) {
        FreeBulletinReply freeBulletinReply = freeBulletinReplyRepository.findById(no).get();
        freeBulletinReply.setContent(content);
        freeBulletinReplyRepository.saveAndFlush(freeBulletinReply);
    }

    public void deleteFreeBulletinReply(Integer no) {
        freeBulletinReplyRepository.deleteById(no);
    }

    // 아래는 검색 기능
    public List<FreeBulletinDTO> searchBulletin(SearchDTO searchDTO) {
        List<FreeBulletin> fbEntityList = null;
        List<FreeBulletinDTO> fbDTOList = new ArrayList<>();

        if (!searchDTO.getSearchWord().equals("") && !searchDTO.getSortingType().equals("")) {
            if (searchDTO.getSortingType().equals("likes") && searchDTO.getDescOrAsc().equals("desc")) {
                fbEntityList = freeBulletinRepository.findByTitleContainingOrderByLikesAsc
                        (searchDTO.getSearchWord(), Sort.by(Sort.Order.asc("likes")));
            } else if (searchDTO.getSortingType().equals("likes")) {
                fbEntityList = freeBulletinRepository.findByTitleContainingOrderByLikesDesc
                        (searchDTO.getSearchWord(), Sort.by(Sort.Order.desc("likes")));
            } else if (searchDTO.getSortingType().equals("views") && searchDTO.getDescOrAsc().equals("desc")) {
                fbEntityList = freeBulletinRepository.findByTitleContainingOrderByViewsAsc
                        (searchDTO.getSearchWord(), Sort.by(Sort.Order.asc("views")));
            } else if (searchDTO.getSortingType().equals("views")) {
                fbEntityList = freeBulletinRepository.findByTitleContainingOrderByViewsDesc
                        (searchDTO.getSearchWord(), Sort.by(Sort.Order.desc("views")));
            }
            System.out.println("1-1");
        } else if (searchDTO.getSearchWord().equals("") && searchDTO.getSortingType().equals("")) {
            if (searchDTO.getDescOrAsc().equals("desc")) {
                fbEntityList = freeBulletinRepository.findAll(Sort.by(Sort.Order.asc("no")));

            } else {
                fbEntityList = freeBulletinRepository.findAll(Sort.by(Sort.Order.desc("no")));
            }
            System.out.println("2-2");
        } else if (searchDTO.getSearchWord().equals("")) {
            if (searchDTO.getSortingType().equals("likes") && searchDTO.getDescOrAsc().equals("desc")) {
                fbEntityList = freeBulletinRepository.findAll(Sort.by(Sort.Order.asc("likes")));
            } else if (searchDTO.getSortingType().equals("likes")) {
                fbEntityList = freeBulletinRepository.findAll(Sort.by(Sort.Order.desc("likes")));
            } else if (searchDTO.getSortingType().equals("views") && searchDTO.getDescOrAsc().equals("desc")) {
                fbEntityList = freeBulletinRepository.findAll(Sort.by(Sort.Order.asc("views")));
            } else if (searchDTO.getSortingType().equals("views")) {
                fbEntityList = freeBulletinRepository.findAll(Sort.by(Sort.Order.desc("views")));
            }
            System.out.println("3-3");
        } else if (searchDTO.getSortingType().equals("")) {
            if (searchDTO.getDescOrAsc().equals("desc")) {
                fbEntityList = freeBulletinRepository.findByTitleContainingOrderByNoAsc(searchDTO.getSearchWord()
                        , Sort.by(Sort.Order.asc("no")));
            } else {
                fbEntityList = freeBulletinRepository.findByTitleContainingOrderByNoDesc(searchDTO.getSearchWord()
                        , Sort.by(Sort.Order.desc("no")));
            }
            System.out.println("4-4");
        }

        if (fbEntityList != null) {
            for (FreeBulletin fbe : fbEntityList) {
                FreeBulletinDTO fbDTO = new FreeBulletinDTO();
                fbDTO.setNo(fbe.getNo());
                fbDTO.setContent(fbe.getContent());
                fbDTO.setTitle(fbe.getTitle());
                fbDTO.setLikes(fbe.getLikes());
                fbDTO.setViews(fbe.getViews());
                fbDTO.setCreateDate(fbe.getCreateDate());
                fbDTO.setThumbnail(fbe.getThumbnail());
                fbDTO.setWriter(fbe.getWriter());
                fbDTOList.add(fbDTO);
            }
        } else {
            System.out.println("freeBulletinEntityList null 발생");
        }

        return fbDTOList;
    }
    //String getProfile
}
