package com.jinhyeok.springpractice.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name="springpractice")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid;
    private String userpw;
    private String name;
    private String gender;
    private String hp;
    private LocalDateTime regdate;

    @Builder
    public UserEntity(Long id, String userid, String userpw, String name, String gender, String hp, LocalDateTime regdate){
        this.id = id;
        this.userid = userid;
        this.userpw = userpw;
        this.name = name;
        this.gender = gender;
        this.hp = hp;
        this.regdate = regdate;
    }
}
