package com.mzcteam01.mzcproject01be.common.base.day.controller;

import com.mzcteam01.mzcproject01be.common.base.day.service.DayService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/day")
@RequiredArgsConstructor
@Tag( name = "Day Controller", description = "요일 관련 컨트롤러" )
public class DayController {
    private final DayService dayService;

    @GetMapping()
    @Operation( summary = "요일 전체 조회" )
    public ResponseEntity<Map<String, Integer>> findAll() {
        return ResponseEntity.ok( dayService.findAll() );
    }
    
    @PostMapping()
    @Operation( summary = "요일 생성")
    public ResponseEntity<Void> createDay(
            @RequestParam String name,
            @RequestParam Integer value,
            @AuthenticationPrincipal AuthUser authUser
    ){
        dayService.create( name, value, authUser.getId() );
        return ResponseEntity.ok( null );
    }
}
