package com.jinhyeok.springpractice.service;

import com.jinhyeok.springpractice.data.UserDTO;
import com.jinhyeok.springpractice.entity.UserEntity;
import com.jinhyeok.springpractice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Optional<T> 클래스
// Optional은 Null Pointer Exception(NPE) 처리를 돕는 Wrapper Class다.
/*
보통 코드를 작성하고 실행을 해보면 NullPointerException 예외를 빈번하게 접하게 된다.
따라서 이러한 Null 값에 대한 예외를 고려하고 코드를 작성해야 하는데, 이는 번거로운 일이다.
이러한 일을 단순히 처리할 수 있도록 자바 8에서는 Optional<T>라는 클래스가 있다.
메소드
isPresent() -Optional에서 value가 존재하는지 판단
get() - Optional에서 value를 가져오는데, null일 경우 예외 발생

Optional 인스턴스 생성방법 2가지
1. Optional<T> os1 = Optional.of(new T());
2. Optional<T> os2 = Optional.ofNullable(new T());
Ex) Optional<String> os1 = Optional.of(new String("Toy1"));
    Optional<String> os2 = Optional.ofNullable(new String("Toy2"));
    os1.ifPresent( s -> System.out.println(s));     // 람다식 버전
    os2.ifPresent(System.out::println);             // 메소드 참조 버전

    // 자바의 람다식
    람다식(lambda)
    - 자바에서는 함수를 메서드라고 부르고 메서드의 형태로 존재
    - 자바 람다식의 구조

    (argument) -> { body } 구문을 사용하여 작성
    ---------- ---  ---------
    매개변수 리스트
               애로우
                    토큰 함수 바디

    @매개변수 리스트
    - 함수에 전달되는 매개변수들이 나열
    - 매개변수를 생략하면 컴파일러가 추론 기능을 이용하여 알아서 처리
    - 매개변수가 하나인 경우 괄호를 생략 가능

    @애로우 토큰
    - 매개변수 리스트와 함수 코드를 분리시키는 역할
    - "->" 기호: 매개변수들을 전달하여 함수 바디 { } 에 작성된 코드를 실행

    @함수바디
    - 함수의 코드
    - 중괄호 ({ }) 로 둘러싸는 것이 일반적이지만, 한 문장인 경우 중괄호 ({})를 생략 가능
    - 한 문장이더라도 return 문이 있으면 반드시 중괄호로 둘러싸야 함.

    람다식 만들기: 람다식은 함수형 인터페이스를 구현한 객체
    - 자바에서 람다식은 함수형 인터페이스에 선언된 추상 메서드를 구현하는 방식으로 작성

    @FunctionlInterface // 컴파일러에게 함수형 인터페이스임을 알리는 annotation
                            -> 컴파일러에게 인터페이스가 추상 메서드가 1개만 있는 함수형 인터페이스인지
                            확인하도록 하여, 처음부터 잘못된 인터페이스 작성을 막는 장점
    interface MyFunction {
        int calc(int x, int y); // 추상 메서드
    }

    - 함수형 인터페이스의 추상 메서드를 람다식으로 구현
    람다식의 작성 : 함수형 인터페이스의 추상 메서드에 코드를 작성
    인터페이스를 상속받은 클래스를 명시적으로 작성하지 않고 익명의 클래스가 만들어지는 방식
    자바에서 람다식은 함수형 인터페이스를 구현한 객체로 다루어 람다식을 인터페이스 타입의 변수에 치환 가능!

    // 매개변수가 있는 람다식
    MyFunction f1 = (x, y) -> { return x+y;};
    MyFunction f2 = (x, y) -> { return x-y;};
    Myfunction2 fsquare = (x) -> { return x*x;};

    // 매개변수가 없는 람다식
    MyFunction3 fpring = () -> {System.out.println("JinHyeok");};

    // 메서드의 인자로 람다식 전달
    static void printMultiply( int x, int y, MyFunction4 f){
        System.out.println("실행 결과: " + f.calc(x, y));
    }
    public LambdaEx4(){
        printMultiply(100, 100, (x. y) -> { return x*y;});
    }




    // 효율적이지 못한 사용법 예시
    public String getPhoneManufacturerName(Person person){
        Optional<Person> personOpt = Optional.ofNullable(person);

        // personOpt에 데이터가 존재한다면, Optional타입의 phoneOpt 생성 후 상위 클래스의 .getPhone() 메서드 참조
        if(personOpt.isPresent()){
            Optional<Phone> phoneOpt = Optional.ofNullable(personOpt.get().getPhone());
            // personOpt에 데이터가 존재한다면, Optional타입의 manufactureOpt 생성 후 상위 클래스의 .getManufacturer() 메서드 참조
            if(phoneOpt.isPresent()){
                Optional<Manufacturer> manufactureOpt = Optional.ofNullable(phoneOpt.get().getManufacturer();
                // manufactureOpt에 데이터가 존재한다면, 상위 클래스인 manufactureOpt의 getName()을 반환
                if(manufactureOpt.isPresent()){
                    return manufactureOpt.get().getName();
                }
            }
        }
        return "Samsung";
    }

    // 효울적인 사용법 예시
    // isPresent와 get 메서드보다는 orElse, orElseXXX

    public String getPhoneManufactureName(Person person){
        return Optional.ofNullable(person)
            .map(Person::getPhone)
            .map(Person::getMenufacturer)
            .map(Manufacturer::getName)
            .orElse("Samsung")
    }

    // orElse 메서드 보다는 orElseGet 메서드
    orElseGet 메서드는 Optional 객체에 값이 없을 때만 실행되지만, orElse 메서드는 그렇지 않다.
    값이 있든 없든 무조건 실행되기 때문에 메서드로 넘겨지는 매개변수의 생성 비용이 큰 경우 주의해야 한다.
    예를 들어, collections 클래스의 emptyList, emptyMap, emptySet 메서드와 같은 메서드는 매번 생성자를
    호출하는 것이 아니라 클래스의 정적 필드로 선언된 EMPTY_LIST, EMPTY_MAP, EMPTY_SET을 반환하므로
    비용이 적다. 하지만 이마저도 orElseGet 메서드를 활용해서 아래와 같이 변경할 수 있다.

    // 생성 비용이 크지 않아 나쁘지 않지만,
    Optional.ofNullable(someObj).orElse(Collections.emptyList());
    Optional.ofNullable(someObj).orElse(Collections.emptyMap());
    Optional.ofNullable(someObj).orElse(Collections.emptySet());

    // 이렇게 변경하는 것이 더 좋다.
    Optional.ofNullable(someObj).orElseGet(Collections::emptyList);
    Optional.ofNullable(someObj).orElseGet(Collections::emptyMap);
    Optional.ofNullable(someObj).orElseGet(Collections::emptySet);

    // 메서드 참조 - 생성 방법
    Method Reference를 만드는 유형에는 다음 3가지가 있다.
    1. 정적 메서드 참조 : Integer의 parseInt를 Integer::parseInt로 사용 가능
    2. 다양한 형식의 인스턴스 메서드 참조 : String의 length 메서드를 String::length로 사용 가능
    3. 기존 객체의 인스턴스 메서드 참조 : Transaction 객체를 할당받은 transaction 지역 변수가 있고, Transaction
    객체에는 getValue 메서드가 있다면, transaction::getValue로 사용 가능

    Lambda                                                  Method Reference
    (Stirng x) -> Integer.parseInt(x)                       Integer::parseInt
    (SimpleMemberVO x) -> SimpleMemberVO.of(x)              SimpleMemberVO::of
    (Instant x) -> x.toEpochMilli()                         Instant::toEpochMilli
    (Optional<BigDecimal> x) -> x.isPresent()               Optional::isPresent
    (List<Partitioninfo> x) -> x.stream()                   Collection::stream
    (Stirng x, String y) -> x.compareToIgnoreCase(y)        String::compareToIgnoreCase
    (Long x) -> memberRepository.findById(x)                memberRepository::findById
    (String x) -> member.setCountry(x)                      member::setCountry
    x -> new SmsCountryResultDTO(x)                         SmsCountryResultDTO::new

 */

@Service    // 서비스 구현에서 사용되며 비즈니스 로직을 수행하는 클래스라는 것을 나타낸다.
@AllArgsConstructor
public class UserService {
    // Create, Read, Update, Delete의 기능을 수행하는 로직을 담는 클래스

    // 클래스와 데이터베이스 테이블을 매핑하기 위해서는 Jpa 라이브러리가 필요한데
    // Jpa 저장소를 상속받아 구현한 인터페이스인 userRepository를 필드변수로 선언.
    private UserRepository userRepository;

    // transaction의 특징
    /*
        1. 원자성 : 한 트랜잭션 내에서 실행한 작업들은 하나로 간주한다. 즉, 모두 성공 또는 실패
        2. 일관성 : 트랜잭션은 일관성 있는 데이터베이스 상태를 유지한다.
        3. 격리성 : 동시에 실해오디는 트랜잭션들이 서로 영향을 미치지 않도록 격리해야 한다.
        4. 지속성 : 트랜잭션을 성공적으로 마치면 결과가 항상 저장되어야 한다.

        선언적 트랜잭션이 적용된 범위에서는 트랜잭션 기능이 포함된 프록시 객체가 생성되어 자동으로 commit 혹은 rollback
    */

    // 유저정보 리스트 가져오기
    @Transactional  // 메소드의 시작과 끝이 하나의 transaction으로 처리 // 선언적 트랜잭션
    public List<UserDTO> getUserList(){
        // 데이터베이스 테이블과 매핑된 userRepository에 존재하는 모든 엔티티를 조회!
        List<UserEntity> userEntities = userRepository.findAll();
        // 조회한 엔티티 데이터 객체를 담을 userDTOList를 생성!
        List<UserDTO> userDTOList = new ArrayList<>();
        // 반복문을 실행하여 조회된 데이터를 userDTO에 담고
        // userDTO 객체를 리스트 타입인 userDTOList에 추가시켜준다!
        for(UserEntity userEntity : userEntities){
            UserDTO userDTO = UserDTO.builder()
                    .id(userEntity.getId())
                    .userid(userEntity.getUserid())
                    .userpw(userEntity.getUserpw())
                    .name(userEntity.getName())
                    .gender(userEntity.getGender())
                    .hp(userEntity.getHp())
                    .regdate(userEntity.getRegdate())
                    .build();
            userDTOList.add(userDTO);
            // 데이터가 추가된 userDTOList를 그림으로 표현하자면 아래 모양과 같다.
            // [ { 데이터1 }, { 데이터2 }, { 데이터 3 }, ... ]
            // { 데이터1 }, { 데이터2 }... 이 객체에는 id, userid, userpw, name, gender, hp, regdate가 담겨있다.
        }
        return userDTOList;
    }

    // 유저정보 저장하기
    @Transactional  // 매개변수로 userDTO 객체를 전달받는다.
    public void createUserData(UserDTO userDTO){
        userRepository.save(userDTO.toEntity());
    }

    // 유저정보 읽어오기
    @Transactional
    public UserDTO readUserData(Long id){
        // NPE (Null Pointer Exception) 에러를 처리할 수 있도록 Optional 클래스로 감싸준다.(wrapper클래스)
        // 파라미터에서 전달받은 id값으로 해당 엔티티를 조회!
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        // 반환된 데이터 값을 userEntity에 저장!
        // 만약 데이터가 null일 경우에는 get() 메소드는 예외를 발생시킨다.
        UserEntity userEntity = userEntityOptional.get();

        // userEntity의 값을 메소드체이닝 방식으로 userDTO의 각 필드변수에 저장한 후에 리턴!
        return UserDTO.builder()
                .id(userEntity.getId())
                .userid(userEntity.getUserid())
                .userpw(userEntity.getUserpw())
                .name(userEntity.getName())
                .gender(userEntity.getGender())
                .hp(userEntity.getHp())
                .regdate(userEntity.getRegdate())
                .build();
    }

    // 유저정보 수정하기
    @Transactional
    public void updateUserData(Long id, UserDTO userDTO){
        // 파라미터에서 전달받은 id값으로 해당 엔티티를 조회!
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        // userEntityOptional의 값이 존재하면 람다식 실행!
        // 파라미터로 전달받은 userDTO의 각 필드변수를 가져와서 select 변수에 set!
        userEntityOptional.ifPresent(select -> {
            select.setUserid(userDTO.getUserid());
            select.setUserpw(userDTO.getUserpw());
            select.setName(userDTO.getName());
            select.setGender(userDTO.getGender());
            select.setHp(userDTO.getHp());
        });
    }

    // 유저정보 삭제하기
    public void deleteUserData(Long id){
        userRepository.deleteById(id);
    }

}
