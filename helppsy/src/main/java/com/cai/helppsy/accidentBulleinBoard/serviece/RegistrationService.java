package com.cai.helppsy.accidentBulleinBoard.serviece;


import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationFileEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationFileRepository;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RegistrationService{

    private final RegistrationRepository registrationrepository; //일반 작성글
    private final RegistrationFileRepository filerepository; //파일

    // 작성글 db 보내기
    public RegistrationEntity write(RegistrationEntity registrationEntity) {
        return registrationrepository.save(registrationEntity);
    }

    // 작성글 전체출력
    public List<RegistrationEntity> writegetlist(){
        return this.registrationrepository.findAll();
    }

    //업로드 사진 db보내기
    public void files(MultipartFile[] file, RegistrationEntity registrationEntity) {
        // .getProperty 프로젝트 경로를 반환해주는 값
        //  String projectPath = System.getProperty("user.dir") + "/src//main/resources/static/files";
        String projectPath = System.getProperty("user.dir") + "/files/accidentBulletin";
        // 디렉터리 없으면 생성
        File directory = new File(projectPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 폴더 없으면 생성
        }
        for(int i=0; i<file.length; i++){
            if (file[i].getOriginalFilename()!=null){
                try{
                    UUID uuid = UUID.randomUUID(); // 유형 4(가상 난수 생성)
                    String fileName = uuid.toString() +"_"+ file[i].getOriginalFilename(); // 파일 랜덤id+오리지날이름으로 결합
                    File saveFile = new File(projectPath,fileName);
                    file[i].transferTo(saveFile); //  .transferTo 업로드한 파일을 지정된 경로로 저장할 때 사용

                    // @ N개의  사진을 db에 저장하기 위해선 새 RegistrationFileEntity객체를 생성해줘야함
                    RegistrationFileEntity fileEntity = new RegistrationFileEntity();
                    fileEntity.setFilename(fileName);

                    fileEntity.setRegistrationEntity(registrationEntity); // fileEntity에  registrationEntity정보 연결

                    filerepository.save(fileEntity);


                } catch (IOException e){
                    throw new RuntimeException("파일 저장 중 오류 발생",e);
                }
            }
        }
    }

    // Id값으로 테이블 리스트 조회
    public Optional<RegistrationEntity> getaccidentview(Integer id){
        return registrationrepository.findById(id);
    }
    // Id값으로 file 테이블 filename 가져오기
    public List<RegistrationFileEntity> getfilename(Integer id){
        return filerepository.findByRegistrationEntity_Id(id);
    }
}
