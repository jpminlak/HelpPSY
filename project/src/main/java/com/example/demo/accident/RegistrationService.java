package com.example.demo.accident;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RegistrationService implements IF_RegistrationService{

    private final RegistrationRepository registrationrepository;

    @Override
    public void write(RegistrationEntity registrationentity, MultipartFile[] file) {
        // .getProperty 프로젝트 경로를 반환해주는 값
        String projectPath = System.getProperty("user.dir") + "/src//main/resources/static/files";

        for(int i=0; i<file.length; i++){
            if (file[i].getOriginalFilename()!=null){
                try{
                UUID uuid = UUID.randomUUID(); // 유형 4(가상 난수 생성)
                String originalName = file[i].getOriginalFilename();
                String fileName = uuid.toString() +"_"+ file[i].getOriginalFilename(); // 파일 랜덤id+오리지날이름으로 결합
                File saveFile = new File(projectPath,"fileName");
                file[i].transferTo(saveFile); //  .transferTo 업로드한 파일을 지정된 경로로 저장할 때 사용
//                registrationentity.setFilename(fileName);

                } catch (IOException e){
                        e.printStackTrace();
                        throw new RuntimeException("파일 저장 중 오류 발생",e);
                    }
            }
        }
        registrationrepository.save(registrationentity); //사건 정보들 보내기
    }


//    // 사고 게시판 글작성
//    public void write(Registrationentity registration, MultipartFile file) throws Exception{
//        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files" ; // 프로젝트 경로담기
//        // 랜덤으로 이름을 생성
//        UUID uuid = UUID.randomUUID();
//        // 랜덤 uuid(랜덤id)_파일이름
//        String fileName = uuid + "_" + file.getOriginalFilename();
//        // saveFile변수에 name이라는 변수명으로 프로젝트 경로를 넣어줌
//        // File 이라는 클래스를 이용해서 들어온 file을 넣어줄 공간을 생성
//        File saveFile = new File(projectPath,"fileName");
//
//        file.transferTo(saveFile);
//
//        registrationrepository.save(registration);
//    }
}
