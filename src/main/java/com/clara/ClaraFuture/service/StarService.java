package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.Star;
import com.clara.ClaraFuture.exception.StarNotFoundException;
import com.clara.ClaraFuture.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class StarService {

    @Autowired
    private StarRepository starRepository;

    // 스타 정보 전체 업데이트 (PUT)
    public Star updateStar(Long starId, Star updatedStar) {
        Optional<Star> starOptional = starRepository.findById(starId);
        if (starOptional.isPresent()) {
            Star star = starOptional.get();
            star.setStarCount(updatedStar.getStarCount());
            // 기타 필요한 필드 업데이트
            // 예: star.setOtherField(updatedStar.getOtherField());
            return starRepository.save(star);
        }
        throw new RuntimeException("Star not found with id " + starId);
    }

    // 스타 정보 부분 업데이트 (PATCH)
    public Star patchStar(Long starId, Map<String, Object> updates) {
        Optional<Star> starOptional = starRepository.findById(starId);
        if (starOptional.isPresent()) {
            Star star = starOptional.get();
            if (updates.containsKey("starCount")) {
                star.setStarCount((Integer) updates.get("starCount"));
            }
            // 기타 필드 업데이트
            // 예: if (updates.containsKey("otherField")) { star.setOtherField((Type) updates.get("otherField")); }
            return starRepository.save(star);
        }
        throw new StarNotFoundException("스타를 찾을 수 없습니다. star ID: " + starId);
    }

    // 새로운 스타 생성
    public Star createStar(Star star) {
        return starRepository.save(star);
    }

    // 스타 정보 조회
    public Star getStarById(Long starId) {
        return starRepository.findById(starId)
                .orElseThrow(() -> new RuntimeException("Star not found with id " + starId));
    }

    // 자녀 id로 스타 정보 조회
    public Star getStarbyChildId(Long childId) {
        Star star = starRepository.findByChildId(childId);
        if (star == null) {
            throw new StarNotFoundException("스타를 찾을 수 없습니다. Child ID: " + childId);
        }
        return star;
    }

    // 스타 삭제
    public void deleteStar(Long starId) {
        starRepository.deleteById(starId);
    }
}
