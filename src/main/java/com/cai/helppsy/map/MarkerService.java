package com.cai.helppsy.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MarkerService {
    public final MarkerRepository markerRepository;

    public void createMarker(String lon, String lat, String regX, String regY,
                             String addr0, String addr1, String content) {
        Mapmarker mapmarker = new Mapmarker();
        mapmarker.setLon(lon);
        mapmarker.setLat(lat);
        mapmarker.setRegX(regX);
        mapmarker.setRegY(regY);
        mapmarker.setAddr0(addr0);
        mapmarker.setAddr1(addr1);
        mapmarker.setContent(content);
        this.markerRepository.save(mapmarker);
    }

    public List<Mapmarker> getList() {
        return this.markerRepository.findAll();
    }

    public Mapmarker getMarker(Integer num) {
        // Optional 사용 이유:
        // 1. NullPointException 을 피하고, 에러가 발생할 수 있는 경우에 결과 없음을 명확히 드러내기 위해.
        // 2. null 이나 .equals("") 보다 .isPresent로 대체.
        Optional<Mapmarker> mapmarker = this.markerRepository.findById(num);
        return mapmarker.orElse(null);  // #1 축약된 형태
    }

    public void modify(Mapmarker mapmarker, String content) {
        mapmarker.setContent(content);
        this.markerRepository.save(mapmarker);
    }

    public void delete(Mapmarker mapmarker) {
        this.markerRepository.delete(mapmarker);
    }
}

//  #1 원래 형태
//  if (mapmarker.isPresent()) {
//      return mapmarker.get();
//  } else {
//      return null;
//      //throw new DataNotFoundException("마커를 찾지 못함");   // function 패키지에 예외 클래스 새로 만들어 오류 처리
//  }