package com.cai.helppsy.freeBulletinBoard.serviece;

import com.cai.helppsy.freeBulletinBoard.entity.Attach;
import com.cai.helppsy.freeBulletinBoard.entity.Bulletin;
import com.cai.helppsy.freeBulletinBoard.entity.ImageAttach;
import com.cai.helppsy.freeBulletinBoard.repository.AttachRepository;
import com.cai.helppsy.freeBulletinBoard.repository.BulletinRepository;
import com.cai.helppsy.freeBulletinBoard.repository.ImageAttachRepository;
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
public class FreeBulletinBoardService {
    private final AttachRepository attachRepository;
    private final BulletinRepository bulletinRepository;
    private final ImageAttachRepository imageAttachRepository;

    public Bulletin bulletinOne(Integer no){
        return bulletinRepository.findById(no).get();
    }

    public List<Bulletin> bulletinList(){
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        List<Bulletin> selectedBulletinList = new ArrayList();

        for(Bulletin b : bulletinList){
            selectedBulletinList.add(b);
        }
        return selectedBulletinList;
    }

    public void addBulletin(Bulletin bulletinEntity, MultipartFile[] files, MultipartFile imgFile){
        if (files != null){
            for (MultipartFile f : files) {
                System.out.println(f.getSize());
                System.out.println(f.getOriginalFilename());
            }
        }
        // 등록 일자
        LocalDateTime createDate = LocalDateTime.now();
        bulletinEntity.setCreateDate(createDate);
        bulletinEntity.setViews(0);
        bulletinRepository.save(bulletinEntity);

        Attach attachEntity;
        String originFileName;
        UUID uuid;
        String fileName;
        File savingInfo;
        String attachFileStoragePath = System.getProperty("user.dir") + "/src/main/resources/static/attachFiles";
        int max = 0;

        //첨부파일 등록
        for(MultipartFile mf : files){
            uuid = UUID.randomUUID();
            fileName = uuid.toString() +"_"+ mf.getOriginalFilename();
            savingInfo = new File(attachFileStoragePath,fileName);
            // 위에는 파일의 저장 위치와 파일의 이름을 저장
            try {
                mf.transferTo(savingInfo);
            }catch(Exception e) {
                System.out.println("_____________________________");
                System.out.println("파일 저장을 실패하였습니다.");
                System.out.println("_____________________________");
            }
            attachEntity = new Attach();
            attachEntity.setFileName(fileName);
            attachEntity.setCreateDate(createDate);

            // bulletinEntity의 no 컬럼에서 최댓값 가져오기
            List<Bulletin> bList =  bulletinRepository.findAll();
            max = 0;
            for(int i = 0; i < bList.size(); i++){
                if(bList.get(i).getNo() > max){
                    max = bList.get(i).getNo();
                }
            }

            attachEntity.setNo(max);

            attachRepository.save(attachEntity);
        }
        //이미지 파일 업로드
        uuid = UUID.randomUUID();
        fileName = uuid.toString() + "_" + imgFile.getOriginalFilename();
        savingInfo = new File(attachFileStoragePath, fileName);

        try {
            imgFile.transferTo(savingInfo);
        }catch(Exception e) {
            System.out.println("_____________________________");
            System.out.println("파일 저장을 실패하였습니다.");
            System.out.println("_____________________________");
        }

        ImageAttach imgEntity = new ImageAttach();
        imgEntity.setImgName(fileName);
        imgEntity.setCreateDate(createDate);
        imgEntity.setNo(max);

        imageAttachRepository.save(imgEntity);
    }

    public String findImgName(Integer no){
        return imageAttachRepository.findByNo(no).getImgName();
    }
}
