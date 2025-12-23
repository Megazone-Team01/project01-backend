package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private int id;
    private String fileUrl;
    private String fileName;

    public static FileResponse from(File file) {
        if (file == null) return null;

        return FileResponse.builder()
                .id(file.getId())
                .fileName(file.getOriginalName())
                .fileUrl("/uploads/"+file.getOriginalName())
                .build();
    }
}
