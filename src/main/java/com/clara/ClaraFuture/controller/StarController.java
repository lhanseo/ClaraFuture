package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.entity.Star;
import com.clara.ClaraFuture.exception.StarNotFoundException;
import com.clara.ClaraFuture.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stars")
public class StarController {

    @Autowired
    private StarService starService;

    // 스타 조회
    @GetMapping("/{starId}")
    public ResponseEntity<Star> getStarById(@PathVariable Long starId) {
        Star star = starService.getStarById(starId);
        if (star == null) {
            throw new StarNotFoundException("스타를 찾을 수 없습니다. ID: " + starId);
        }
        return new ResponseEntity<>(star, HttpStatus.OK);
    }

    // 스타 생성
    @PostMapping
    public ResponseEntity<Star> createStar(@RequestBody Star star) {
        Star createdStar = starService.createStar(star);
        return new ResponseEntity<>(createdStar, HttpStatus.CREATED);
    }

    // 스타 전체 수정 (PUT)
    @PutMapping("/{starId}")
    public ResponseEntity<Star> updateStar(@PathVariable Long starId, @RequestBody Star updatedStar) {
        Star star = starService.updateStar(starId, updatedStar);
        if (star == null) {
            throw new StarNotFoundException("스타를 찾을 수 없습니다. ID: " + starId);
        }
        return new ResponseEntity<>(star, HttpStatus.OK);
    }

    // 스타 부분 수정 (PATCH)
    @PatchMapping("/{starId}")
    public ResponseEntity<Star> patchStar(@PathVariable Long starId, @RequestBody Map<String, Object> updates) {
        Star updatedStar = starService.patchStar(starId, updates);
        if (updatedStar == null) {
            throw new StarNotFoundException("스타를 찾을 수 없습니다. ID: " + starId);
        }
        return new ResponseEntity<>(updatedStar, HttpStatus.OK);
    }

    // 스타 삭제
    @DeleteMapping("/{starId}")
    public ResponseEntity<Void> deleteStar(@PathVariable Long starId) {
        starService.deleteStar(starId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
