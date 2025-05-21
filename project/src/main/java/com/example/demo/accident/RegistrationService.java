package com.example.demo.accident;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final RegistrationRepository registrationrepository;

    // 사고 게시판 글작성
    public void write(RegistrationEntity registration, MultipartFile file) throws Exception{
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files" ; // 프로젝트 경로담기
        // 랜덤으로 이름을 생성
        UUID uuid = UUID.randomUUID();
        // 랜덤 uuid(랜덤id)_파일이름
        String fileName = uuid + "_" + file.getOriginalFilename();
        // saveFile변수에 name이라는 변수명으로 프로젝트 경로를 넣어줌
        // File 이라는 클래스를 이용해서 들어온 file을 넣어줄 공간을 생성
        File saveFile = new File(projectPath,"fileName");

        file.transferTo(saveFile);

        registrationrepository.save(registration);
    }
}
