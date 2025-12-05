package com.mzcteam01.mzcproject01be.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {
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
