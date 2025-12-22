package com.mzcteam01.mzcproject01be.domains.file.controller;

import com.mzcteam01.mzcproject01be.domains.file.dto.response.FileResponse;
import com.mzcteam01.mzcproject01be.domains.file.service.FileService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/v1/file")
@Tag( name = "File Controller", description = "파일 업로드 및 조회 관련 API")
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation( summary = "파일 업로드 API", description = "fileUrl: 해당 파일을 볼 수 있는 경로, fileId: File 엔티티 ID")
    public ResponseEntity<Map<String, Object>> upload(
            @RequestParam MultipartFile file,
            @AuthenticationPrincipal AuthUser authUser
    ){
        return ResponseEntity.ok( fileService.uploadFiles( file, authUser.getId() ) );
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "파일 삭제" )
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id,
            @AuthenticationPrincipal AuthUser authUser
    ){
        fileService.deleteFile( id, authUser.getId() );
        return ResponseEntity.ok( Map.of("status", "성공" ) );
    }

    @GetMapping("/{lectureId}/lecture")
    @Operation( summary = "파일 정보 조회" )
    public ResponseEntity<FileResponse> getFileWithLecture(
            @PathVariable Integer lectureId
    ){
        return ResponseEntity.ok( fileService.getFileWithLecture( lectureId ) );
    }

}
