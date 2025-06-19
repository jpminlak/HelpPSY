package com.cai.helppsy.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MapController {
    private final MarkerService markerService;

    @GetMapping("/map")
    public String map(Model model) {
    //public String map() {
        List<Mapmarker> mapMarkerList = this.markerService.getList();
        model.addAttribute("mapMarkerList", mapMarkerList);
        System.out.println("지도 페이지 입장");
        return "/map/map";
    }

    @PostMapping("/map/addMarker")
    public String addMarker(Mapmarker mapmarker) {
        this.markerService.createMarker(mapmarker.getLon(), mapmarker.getLat(),
                mapmarker.getRegX(), mapmarker.getRegY(), mapmarker.getAddr0(), mapmarker.getAddr1(),
                mapmarker.getContent());
        return "/map/map";
    }

    @GetMapping("/map/loadMarker")
    public String listMarker(Model model) {
        List<Mapmarker> mapMarkerList = this.markerService.getList();
        model.addAttribute("mapMarkerList", mapMarkerList);
        return "/map/map";
    }

    @PostMapping("/map/modMarker")
    public String modMarker(Mapmarker mapmarker) {
    //public String modMarker(@PathVariable("num") Integer num) {
        //Mapmarker mapmarker = this.markerService.getMarker(num);
        this.markerService.getMarker(mapmarker.getNum());
        System.out.println("mapmarker = " + mapmarker);
        System.out.println("num = " + mapmarker.getNum());
        this.markerService.modify(mapmarker, mapmarker.getContent());
        return "/map/map";
    }

    @GetMapping("/map/delMarker/{num}")
    public String deleteMarker(@PathVariable("num") Integer num){
        Mapmarker mapmarker =this.markerService.getMarker(num);
        this.markerService.delete(mapmarker);
        return "redirect:/map";
    }
}

// html 문서에서 post 방식으로 얻은 객체의 데이터를 바로 콘솔 출력하는 코드
/*@PostMapping("/map/mapmarker")
public String mapMarker(HttpServletRequest request) {
    System.out.println("전체 객체: " + request);
    System.out.println("content: " + request.getParameter("content"));
    System.out.println("lon = " + request.getParameter("lon"));
    System.out.println("lat = " + request.getParameter("lat"));
    System.out.println("regY = " + request.getParameter("regY"));
    System.out.println("regX = " + request.getParameter("regX"));
    return "redirect:/map";
}*/