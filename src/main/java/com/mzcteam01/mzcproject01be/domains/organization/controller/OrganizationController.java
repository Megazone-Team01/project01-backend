package com.mzcteam01.mzcproject01be.domains.organization.controller;

import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.service.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/v1/organization" )
@Tag( name = "Organization Controller", description = "아카데미(기관)에 대한 API" )
public class OrganizationController {
    private final OrganizationService service;

    @GetMapping()
    public ResponseEntity<List<GetOrganizationResponse>> list(
            @RequestBody GetOrganizationRequest request
    ){
        return ResponseEntity.ok( service.list( request ) );
    }

    @GetMapping("{id}")
    public ResponseEntity<GetOrganizationResponse> findById(
            @PathVariable int id
    ){
        return ResponseEntity.ok( service.findById( id ) );
    }
}
