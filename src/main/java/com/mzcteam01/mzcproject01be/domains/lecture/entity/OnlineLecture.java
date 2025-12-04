package com.mzcteam01.mzcproject01be.domains.lecture.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "online_lecture")
public class OnlineLecture extends Lecture {
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "file_id")
//    private file file;

}
