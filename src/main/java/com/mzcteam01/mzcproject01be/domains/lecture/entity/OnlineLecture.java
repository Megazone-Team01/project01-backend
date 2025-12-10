package com.mzcteam01.mzcproject01be.domains.lecture.entity;

import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    public void update(String name, User teacher, Integer price, LocalDateTime startAt, LocalDateTime endAt, String description, File file){
        super.update(name, teacher, price, startAt, endAt, description);
        if( file != null ) this.file = file;
    }

    public void updateStatus( int status ){
        this.status = status;
    }
}
