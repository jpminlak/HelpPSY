package com.cai.helppsy.accidentBulleinBoard.service;


import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationFileEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationFileRepository;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegistrationService {

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

//    //업로드 사진 db보내기
//    public void files(MultipartFile[] file, RegistrationEntity registrationEntity) {
//        // .getProperty 프로젝트 경로를 반환해주는 값
//        //  String projectPath = System.getProperty("user.dir") + "/src//main/resources/static/files";
//        String projectPath = System.getProperty("user.dir") + "/files/accidentBulletin";
//        // 디렉터리 없으면 생성
//        File directory = new File(projectPath);
//        if (!directory.exists()) {
//            directory.mkdirs(); // 폴더 없으면 생성
//        }
//        for(int i=0; i<file.length; i++){
//            if (file[i].getOriginalFilename()!=null){
//                try{
//                    UUID uuid = UUID.randomUUID(); // 유형 4(가상 난수 생성)
//                    String fileName = uuid.toString() +"_"+ file[i].getOriginalFilename(); // 파일 랜덤id+오리지날이름으로 결합
//                    File saveFile = new File(projectPath,fileName);
//                    file[i].transferTo(saveFile); //  .transferTo 업로드한 파일을 지정된 경로로 저장할 때 사용
//
//                    // @ N개의  사진을 db에 저장하기 위해선 새 RegistrationFileEntity객체를 생성해줘야함
//                    RegistrationFileEntity fileEntity = new RegistrationFileEntity();
//                    fileEntity.setFilename(fileName);
//
//                    fileEntity.setRegistrationEntity(registrationEntity); // fileEntity에  registrationEntity정보 연결
//
//                    filerepository.save(fileEntity);
//
//                } catch (IOException e){
//                    throw new RuntimeException("파일 저장 중 오류 발생",e);
//                }
//            }
//        }
//    }

    // Id값으로 테이블 리스트 조회
    public Optional<RegistrationEntity> getaccidentview(Integer id){
        return registrationrepository.findById(id);
    }
    // Id값으로 file 테이블 filename 가져오기
    public List<RegistrationFileEntity> getfilename(Integer id){
        return filerepository.findByRegistrationEntity_Id(id);
    }

    // 게시글 삭제하기
    public void deleteAccident(Integer id){
        registrationrepository.deleteById(id);
    }

    // 게시글 조회수 메인리스트 --  (비동기처리)
    @Transactional
    // 메서드 내에서 발생하는 DB 작업들을 하나의 트랜잭션으로 묶어줌 (2가지 이상의 트랜잭션 발생시 1개의 트랜잭션으로 작업)
    // 작업 도중 에러가 발생하면 전체 롤백(되돌림), 성공하면 모두 커밋(반영).
    public Integer PostView(Integer postId) {
        Optional<RegistrationEntity> postviewsNum = registrationrepository.findById(postId);
        if (postviewsNum.isPresent()) { // .isPresent() = 객체의 값이 있다면 / 반대 .isEmpty()는 값이 없다면 암기!
            RegistrationEntity entity = postviewsNum.get(); // 해당 id가 있는 튜플을 entity객체에 저장
            entity.setPostViews(entity.getPostViews() + 1); // 현재 조회수에서 +1 값으로 수정
            registrationrepository.save(entity); // 수정된 조회수를 최종적으로 다시 저장
            return entity.getPostViews(); // 증가된 조회수 반환
        }
        return null;
    }

    // 게시글 상세보기 조회수 --  (동기) 비동기 로직 재활용 +1만 삭제
    public Integer getPostView(Integer postId) {
        Optional<RegistrationEntity> postviewsNum = registrationrepository.findById(postId);
        if (postviewsNum.isPresent()) { // .isPresent() = 객체의 값이 있다면 / 반대 .isEmpty()는 값이 없다면 암기!
            RegistrationEntity entity = postviewsNum.get(); // 해당 id가 있는 튜플을 entity객체에 저장
            entity.setPostViews(entity.getPostViews()); // 현재 조회수에서 +1 값으로 수정
            registrationrepository.save(entity); // 수정된 조회수를 최종적으로 다시 저장
            return entity.getPostViews(); // 증가된 조회수 반환
        }
        return null;
    }

    // 게시물 수정란 채우기 용 (기존 작성칸 작성되어 수정칸 보여주기)
    public Optional<RegistrationEntity> UpdateAccidentPage(Integer id, String alias){
        Optional<RegistrationEntity> registrationEntity = registrationrepository.findByIdAndAlias(id, alias);
        return registrationEntity;
    }

    // 최종 게시물 수정
    // JPA에서는 findById()로 가져온 객체는 영속 상태(Persistent) 이므로, 수정 후 save() 호출하면 변경이 DB에 반영.
    // **변경 감지(Dirty Checking)**로 내부적으로 UPDATE SQL 실행
    public RegistrationEntity UpdateAccident(RegistrationEntity Data){
        // Optional로 감싼 객체는 entity로 바로접근이 불가
        // Optional<RegistrationEntity> entity = registrationrepository.findById(Data.getId());
        RegistrationEntity entity = registrationrepository.findById(Data.getId())
                .orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다"));
                // .orElseThrow() = 값이 존재하면 그 값을 꺼내고,없으면 바로 예외를 던짐, !null 체크가 불필요하고, 코드도 더 안정적

        // JAP가 변경을 감지
        entity.setTitle(Data.getTitle()); // 제목
        entity.setAccident(Data.getAccident()); // 사고분류
        entity.setRegion(Data.getRegion()); // 사고지역
        entity.setType(Data.getType()); // 차 종류
        entity.setContent(Data.getContent()); // 내용
        entity.setLatitude(Data.getLatitude()); // 위도
        entity.setLongitude(Data.getLongitude()); // 경도

        return registrationrepository.save(entity);
    }

}