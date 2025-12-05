package com.mzcteam01.mzcproject01be.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
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
