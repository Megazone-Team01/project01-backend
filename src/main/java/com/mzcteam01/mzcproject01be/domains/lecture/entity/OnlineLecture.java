package com.mzcteam01.mzcproject01be.domains.lecture.entity;

import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Table(name = "online_lecture")
public class OnlineLecture extends Lecture {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    // 승인여부
    // -1 (반려), 0 (대기), 1(승인)
    @Column(name = "status", nullable = false)
    private int status;

}
