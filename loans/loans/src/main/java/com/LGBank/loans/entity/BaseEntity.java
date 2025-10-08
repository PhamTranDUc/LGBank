package com.LGBank.loans.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime  createAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false)
    private String createBy;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime  updateAt;

    @LastModifiedBy
    @Column(name = "updated_by", insertable = false)
    private String updateBy;
}
