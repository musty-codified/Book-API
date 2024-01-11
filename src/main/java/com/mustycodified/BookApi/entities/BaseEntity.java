package com.mustycodified.BookApi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;

    BaseEntity(){
        this.createdDate= new Date();
        this.updatedDate = new Date();
    }

    @PrePersist
    public void setCreatedAt() {
        createdDate = new Date();
    }
    @PreUpdate
    public void setUpdatedAt() {
        updatedDate = new Date();
    }
}
