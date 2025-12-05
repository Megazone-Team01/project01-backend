package com.mzcteam01.mzcproject01be.domains.file.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "file" )
public class File extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "url" )
    private String url;

     @ManyToOne( fetch = FetchType.LAZY )
     @JoinColumn( name = "uploader_id" )
     private User uploader;

    @Column( name = "extension", length = 100)
    private String extension;

    @Column( name = "original_name" )
    private String originalName;
}
