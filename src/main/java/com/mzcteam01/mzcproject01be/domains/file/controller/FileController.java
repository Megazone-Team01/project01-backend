package com.mzcteam01.mzcproject01be.domains.file.controller;

import com.mzcteam01.mzcproject01be.domains.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/v1/file")
@Tag( name = "File Controller", description = "파일 업로드 및 조회 관련 API")
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    @Operation( summary = "파일 업로드 API")
    public ResponseEntity<Map<String, Object>> upload(
            @RequestParam List<MultipartFile> files,
            @RequestParam Integer uploaderId
    ){

    }
}
