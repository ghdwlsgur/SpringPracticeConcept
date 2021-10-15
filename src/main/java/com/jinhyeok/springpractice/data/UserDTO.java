package com.jinhyeok.springpractice.data;


import com.jinhyeok.springpractice.entity.UserEntity;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;                        // primary key
    private String userid;                  // 아이디
    private String userpw;                  // 비밀번호
    private String name;                    // 이름
    private String gender;                  // 성별('남자', '여자')
    private String hp;                      // 연락처
    private LocalDateTime regdate;          // 등록일

    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .userid(userid)
                .userpw(userpw)
                .name(name)
                .gender(gender)
                .hp(hp)
                .regdate(LocalDateTime.now())       // 현재시간을 저장
                .build();
        return userEntity;
    }

    @Builder
    public UserDTO(Long id, String userid, String userpw, String name, String gender, String hp, LocalDateTime regdate){
        this.id = id;
        this.userid = userid;
        this.userpw = userpw;
        this.name = name;
        this.gender = gender;
        this.hp = hp;
        this.regdate = regdate;
    }

}
