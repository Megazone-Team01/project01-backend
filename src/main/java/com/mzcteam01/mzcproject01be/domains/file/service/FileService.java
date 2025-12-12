package com.mzcteam01.mzcproject01be.domains.file.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.FileErrorCode;
import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    // Stage 환경에서는 경로가 변경되어야 함 -> FE에서 조회 필요
    private final String FILE_PATH = "src/main/resources/files";

    public Map<String, Object> uploadFiles(MultipartFile file, int uploaderId){
        Map<String, Object> result = new HashMap<>();
        try {
            // 파일이 비어있는지 확인
            if (file.isEmpty()) throw new CustomException(FileErrorCode.FILE_NOT_FOUND.getMessage());

            Path uploadPath = Paths.get(FILE_PATH);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String originalFilename = file.getOriginalFilename();
            String filename = System.currentTimeMillis() + "_" + originalFilename;

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 엔티티 저장
            File fileEntity = this.createFileEntity( FILE_PATH + "/" + filename, uploaderId, originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toUpperCase(), originalFilename );

            result.put( "fileId", fileEntity.getId() );
            result.put( "fileUrl", fileEntity.getUrl() );
        } catch (IOException e) {
            throw new CustomException( FileErrorCode.FILE_UPLOAD_ERROR.getMessage() );
        }
        return result;
    }

    private File createFileEntity( String url, int uploaderId, String extension, String originalName ){
        User uploader = userRepository.findById( uploaderId ).orElseThrow( () -> new CustomException("해당하는 사용자가 존재하지 않습니다") );
        File file = File.builder()
                .url( url )
                .uploader( uploader )
                .extension( extension )
                .originalName( originalName )
                .createdBy( uploader.getId() )
                .build();
        return fileRepository.save( file );
    }
    
    // 파일의 메타 데이터 조회
    
    // 파일 삭제
    @Transactional
    public void deleteFile( int fileId, int deletedBy ) {
        File file = fileRepository.findById( fileId ).orElseThrow(() -> new CustomException(FileErrorCode.FILE_NOT_FOUND.getMessage()) );
        try {
            Path filePath = Paths.get( file.getUrl() );
            // 파일 존재 여부 확인
            if (!Files.exists(filePath)) throw new CustomException(FileErrorCode.FILE_NOT_FOUND.getMessage());
            // 파일 삭제
            Files.delete(filePath);
            // File Entity 삭제
            file.delete( deletedBy );
        } catch (IOException e) {
            throw new CustomException( FileErrorCode.FILE_DELETE_ERROR.getMessage() );
        }
    }
}
