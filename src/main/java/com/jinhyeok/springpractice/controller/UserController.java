package com.jinhyeok.springpractice.controller;


import com.jinhyeok.springpractice.data.UserDTO;
import com.jinhyeok.springpractice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller // 이 java파일을 컨트롤러로 사용할 수 있으며 스프링의 객체(bean)으로 등록된다.
public class UserController {

    private UserService userService;

    // 유저리스트 // 127.0.0.1:8080/user -> 유저정보 리스트 보여주기
    @GetMapping("/user")
    public String userList(){
        return "board/user_list.html";
    }

    /*

     -- 실행순서 --
     Client -> Request(127.0.0.1:8080/user) -> Dispatcher Servlet -> Handler Mapping -> Controller (return "board/user_list.html")
     -> axios를 통해 '/api/list' 접근 -> RestController('/api/list') -> Service 비즈니스 로직 실행 getUserList()
     -> 데이터를 JSON형식으로 반환 -> Dispatcher Servlet -> Response ->  Client

    -- 간략히 --
    Request(127.0.0.1:8080/user) -> Controller (return "board/user_list.html") -> axios 통신 -> '/api/list' -> RestController
    ->  UserService.java .getUserList() 실행 후 유저리스트 데이터 반환 -> Response -> Client

     */



    // 유저상세정보 // 127.0.0.1:8080/user/detail/{no} -> 유저상세정보 보여주기
    @RequestMapping(value="/user/detail/{no}", method = RequestMethod.GET)
    public String userDetail(@PathVariable("no") Long no, Model model){
        // model 객체와 id를 파라미터로 가져온다.
        // model 객체 -> Controller에서 생성된 데이터를 담아서 View로 전달할 때 사용하는 객체
        // @RequestMapping 어노테이션에는 받을 파라미터를 변수 { }로 지정해주고
        // @PathVariable에는 메소드의 파라미터를 지정해준다.
        // @PathVariable -> 요청하는 주소 사이에 값을 매핑시킬 수 있게 한다.
        // 그말인 즉슨, Long no를 {no} 위치에 값으로 넣을 수 있다. -> id 값이 들어가게 된다.

        model.addAttribute("id", no);
        // model.addAttribute("변수이름", "변수에 넣을 데이터값");
        // ex) 만약 id값이 1이라면 no에는 id값이 담기기 때문에
        // model.addAttribute( "id", 1); 으로 표현할 수 있다. 즉, 1을 담고 있는 id 변수명을 view에 전달!

        // board/user_detail.html로 데이터를 전달하기 때문에
        // board/user_detail.html에서는 ${id}로 데이터를 사용할 수 있게 된다.
        // primary key인 id값만 가지고 있으면 그 외에 다른 필드변수
        // userid, userpw.. 등등의 데이터를 가져올 수 있다.

        return "board/user_detail.html";
    }

    // 유저정보수정 // 127.0.0.1:8080/user/edit/{no} -> 유저정보수정 보여주기
    @RequestMapping(value="/user/edit/{no}", method = RequestMethod.GET)
    public String userEdit(@PathVariable("no") Long no, Model model){
        model.addAttribute("id", no);
        return "board/user_edit.html";
    }

    // 유저정보수정 후 저장  // 메소드 오버로딩
    @RequestMapping(value="/user/edit/{no}", method = {RequestMethod.POST, RequestMethod.PUT})
    public String userEdit(UserDTO userDTO){
        userService.createUserData(userDTO);    // 저장 로직 실행
        return "redirect:/user";                // 127.0.0.1:8080/user로 이동
    }

    // 유저정보등록
    @GetMapping("/user/write")
    public String userWrite(){
        return "board/user_write.html";
    }

    // 유저정보등록 후 저장 // 메소드 오버로딩
    @PostMapping("/user/write/insert")
    public String userWrite(UserDTO userDTO){
        userService.createUserData(userDTO);    // 저장 로직 실행
        return "redirect:/user";                // 127.0.0.1:8080/user로 이동
    }





}
