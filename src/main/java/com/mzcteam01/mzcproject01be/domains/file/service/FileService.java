package com.mzcteam01.mzcproject01be.domains.file.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    //private final UserRepository userRepository;

    private final String FILE_PATH = "src/main/resources/files";

    public List<Map<String, Object>> uploadFiles(List<MultipartFile> files, int uploaderId){
        List<Map<String, Object>> result = new ArrayList<>();
        for( MultipartFile file : files){
            try {
                // 파일이 비어있는지 확인
                if (file.isEmpty()) throw new CustomException("파일이 첨부되지 않았습니다");

                Path uploadPath = Paths.get(FILE_PATH);
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

                String originalFilename = file.getOriginalFilename();
                String filename = System.currentTimeMillis() + "_" + originalFilename;

                Path filePath = uploadPath.resolve(filename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // 파일 엔티티 저장
                File fileEntity = this.createFileEntity( FILE_PATH + "/" + filename, uploaderId, originalFilename.substring(originalFilename.lastIndexOf(".")), originalFilename );

                result.add( Map.of( "fileId", fileEntity.getId(), "fileUrl", fileEntity.getUrl() ) );
            } catch (IOException e) {
                throw new CustomException("파일 업로드에 실패했습니다");
            }
        }
        return result;
    }

    private File createFileEntity( String url, int userId, String extension, String originalName ){
        // User uploader = userRepository.findById( uploaderId ).orElseThrow( () -> new CustomException("해당하는 사용자가 존재하지 않습니다") );

    }
    
    // 파일의 메타 데이터 조회
    
    // 파일 조회

}
