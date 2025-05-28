package com.cai.helppsy.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MarkerService {
    public final MarkerRepository markerRepository;

    public void createMarker(String lon, String lat, String regX, String regY, String content) {
        Mapmarker mapmarker = new Mapmarker();
        mapmarker.setLon(lon);
        mapmarker.setLat(lat);
        mapmarker.setRegX(regX);
        mapmarker.setRegY(regY);
        mapmarker.setContent(content);
        this.markerRepository.save(mapmarker);
    }

    public void createMarker(Mapmarker mapmarker) {
    }
}