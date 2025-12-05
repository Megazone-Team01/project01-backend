package com.mzcteam01.mzcproject01be.test;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping( "/api/v1/test")
@Tag( name = "Test Controller", description = "테스트용 컨트롤러" )
public class TestController {

    @GetMapping( "/ping" )
    public ResponseEntity<Map<String, Boolean>> ping() {
        return ResponseEntity.ok( Map.of( "ping", true ) );
    }
}
