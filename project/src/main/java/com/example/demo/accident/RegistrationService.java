package com.example.demo.accident;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RegistrationService{

    private final RegistrationRepository registrationrepository;
    private final RegistrationFileRepository filerepository;

    public void write(RegistrationEntity registration) { // 작성글 db 보내기
        registrationrepository.save(registration);
    }

    public List<RegistrationEntity> writegetlist(){ // 작성글 전체출력
        return this.registrationrepository.findAll();
    }


    public void files(RegistrationFileEntity registrationfileentity, MultipartFile[] file) { //업로드 사진 db보내기
        // .getProperty 프로젝트 경로를 반환해주는 값
        String projectPath = System.getProperty("user.dir") + "/src//main/resources/static/files";
        for(int i=0; i<file.length; i++){
            if (file[i].getOriginalFilename()!=null){
                try{
                    UUID uuid = UUID.randomUUID(); // 유형 4(가상 난수 생성)
                    String originalName = file[i].getOriginalFilename();
                    String fileName = uuid.toString() +"_"+ file[i].getOriginalFilename(); // 파일 랜덤id+오리지날이름으로 결합
                    File saveFile = new File(projectPath,fileName);
                    file[i].transferTo(saveFile); //  .transferTo 업로드한 파일을 지정된 경로로 저장할 때 사용
                    registrationfileentity.setFilename(fileName);
                    filerepository.save(registrationfileentity);
                } catch (IOException e){
                    e.printStackTrace();
                    throw new RuntimeException("파일 저장 중 오류 발생",e);
                }
            }
        }
    }
}
