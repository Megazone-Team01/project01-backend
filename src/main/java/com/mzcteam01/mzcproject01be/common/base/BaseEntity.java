package com.mzcteam01.mzcproject01be.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {
    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at" )
    private LocalDateTime updatedAt;

    @Column( name = "deleted_at" )
    private LocalDateTime deletedAt;

    @Setter
    @Column( name = "created_by", nullable = false, updatable = false )
    protected Integer createdBy;

    @Column( name = "updated_by" )
    protected Integer updatedBy;

    @Column( name = "deleted_by" )
    private Integer deletedBy;

    public void delete( int deletedBy ) {
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
    }

    public void createdBy( int createdBy ) {
        this.createdBy = createdBy;
    }

}
