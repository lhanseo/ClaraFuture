package com.clara.ClaraFuture.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 예외 처리기 클래스
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 부모를 찾을 수 없는 경우 처리
    @ExceptionHandler(ParentNotFoundException.class)
    public ResponseEntity<String> handleParentNotFoundException(ParentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 자녀를 찾을 수 없는 경우 처리
    @ExceptionHandler(ChildNotFoundException.class)
    public ResponseEntity<String> handleChildNotFoundException(ChildNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 자녀가 이미 부모와 연동된 경우 처리
    @ExceptionHandler(AlreadyLinkedException.class)
    public ResponseEntity<String> handleAlreadyLinkedException(AlreadyLinkedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 기기를 찾을 수 없는 경우 처리
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<String> handleDeviceNotFoundException(DeviceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 잘못된 사용 시간 설정 처리
    @ExceptionHandler(InvalidUsageTimeException.class)
    public ResponseEntity<String> handleInvalidUsageTimeException(InvalidUsageTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 미션을 찾을 수 없는 경우 처리
    @ExceptionHandler(MissionNotFoundException.class)
    public ResponseEntity<String> handleMissionNotFoundException(MissionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    // JWT가 유효하지 않을 때 처리
    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<String> handleInvalidJwtTokenException(InvalidJwtTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 지원되지 않는 소셜 로그인 제공자 처리
    @ExceptionHandler(OauthProviderNotFoundException.class)
    public ResponseEntity<String> handleOauthProviderNotFoundException(OauthProviderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 예상치 못한 일반 예외 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<String> handleGeneralException(GeneralException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // 스타를 찾을 수 없는 경우 예외 처리
    @ExceptionHandler(StarNotFoundException.class)
    public ResponseEntity<String> StarNotFoundException(StarNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에 문제가 발생했습니다.");
    }
}
