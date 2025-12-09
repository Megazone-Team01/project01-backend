package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public void create(
            String name,
            String email,
            String password,
            String phone,
            String addressCode,
            String addressDetail,
            String roleName,
            Integer type
    ){
        UserRole role = userRoleRepository.findByName( roleName ).orElseThrow( () -> new CustomException("해당하는 Role이 없습니다") );
        User user = User.builder()
                .name( name )
                .email( email )
                .password( password )
                .phone( phone )
                .addressCode( addressCode )
                .addressDetail( addressDetail )
                .role( role )
                .type( type )
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save( user );
    }

    public void update( int id, String phone, String addressCode, String addressDetail, Integer type ){
        User user = userRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 사용자가 존재하지 않습니다") );
        user.update( phone, addressCode, addressDetail, type);
    }

    public void delete( int id, int deletedBy ){
        User user = userRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 사용자가 존재하지 않습니다") );
        user.delete();
    }

    public List<AdminGetUserResponse> findAllUser(){
        return userRepository.findAll().stream().map( AdminGetUserResponse::of ).toList();
    }

    public AdminGetUserResponse findById( int id ){
        return AdminGetUserResponse.of( userRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 사용자가 없습니다") ) );
    }
}
