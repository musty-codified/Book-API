package com.mustycodified.BookApi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    private Date lastLoginDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<BookEntity> books = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<BorrowedBookEntity> borrowedBooks = new ArrayList<>();

    private String roles;

}
