package com.cai.helppsy.map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MapController {
    private final MarkerService markerService;

    @GetMapping("/map")
    public String map() {
        System.out.println("지도 페이지 입장");
        return "/map/map";
    }
    @PostMapping("/mapmarker")
    public String mapMarker(@RequestParam("lon") String lon, @RequestParam("lat") String lat, @RequestParam("regX") String regX,
                            @RequestParam("regY") String regY, @RequestParam("content") String content) {
        this.markerService.createMarker(lon, lat, regX, regY, content);
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
