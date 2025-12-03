package com.mzcteam01.mzcproject01be.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
public class BaseEntity {
    @CreatedDate
    @Column( name = "created_at" )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at" )
    private LocalDateTime updatedAt;

    @Column( name = "deleted_at" )
    private LocalDateTime deletedAt;

    @Column( name = "created_by" )
    protected int createdBy;

    @Column( name = "updated_by" )
    protected int updatedBy;

    @Column( name = "deleted_by" )
    private int deletedBy;

    public void delete( int deletedBy ) {
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
    }
}
