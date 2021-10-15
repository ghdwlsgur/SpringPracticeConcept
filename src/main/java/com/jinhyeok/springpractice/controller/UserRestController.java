package com.jinhyeok.springpractice.controller;


import com.jinhyeok.springpractice.data.UserDTO;
import com.jinhyeok.springpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@Controller은 Model 객체를 만들어 데이터를 담고 View를 찾는 것
@RestController은 단순히 객체만을 반환하고 객체 데이터는 JSON 또는 XML 형식으로 HTTP 응답에 담아서 전송
*/

/*
@Controller와 @RestController의 차이
전통적인 Spring MVC 컨트롤러인 @Controller와 웹서비스의 컨트롤러인 @RestController의 주요한 차이점은
HTTP Response Body가 생성되는 방식이다.
좀 더 쉽게 말하자면 @Controller의 주용도는 View를 리턴하는 것이고, @RestController은 JSON/XML 타입의
HTTP 응답을 직접 리턴한다.

@Controller에서 View를 반환하는 경우
1. Client는 URI 형식으로 웹 서비스에 요청을 보낸다.
2. Mapping되는 Handler와 그 Type을 찾는 DispatcherServlet이 요청을 인터셉트한다.
3. Controller가 요청을 처리한 후에 응답을 DispatcherServlet으로 반환하고, DispatcherServelet은 View를
사용자에게 반환한다.

Client -> Request -> Dispatcher Servlet -> Handler Mapping -> Controller -> View -> Dispatcher Servlet
-> Response -> Client

@RestController에서 Json/Xml 형태로 객체 데이터를 반환하는 경우
1. Client는 URI 형식으로 웹 서비스에 요청을 보낸다.
2. Mapping되는 Hadnler와 그 Type을 찾는 DispatcherServlet이 요청을 인터셉트한다.
3. RestController는 해당 요청을 처리하고 데이터를 반환한다.

Client -> HTTP Request -> Dispatcher Servlet -> Handler Mapping -> RestController(자동 ResponseBody 추가)
-> HTTP Response -> Client
*/



// @RestController 어노테이션을 사용했기 때문에 하단 메서드 파라미터에 존재하는 @RequestBody 어노테이션은 생략 가능.
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    // 유저리스트 가져오기
    @GetMapping("/api/list")
    public List<UserDTO> list() {
        return userService.getUserList();
    }

    // 유저정보 등록하기
    @PostMapping("/api/write")
    public void createUser(@RequestBody UserDTO userDTO){
        userService.createUserData(userDTO);
    }

    // 유저정보 확인하기
    @GetMapping("/api/{id}")
    public UserDTO readUser(@PathVariable Long id){
        return userService.readUserData(id);
    }

    // 유저정보 수정하기
    @PutMapping("/api/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userService.updateUserData(id, userDTO);
    }

    // 유저정보 삭제하기
    @DeleteMapping("/api/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUserData(id);
    }





}
