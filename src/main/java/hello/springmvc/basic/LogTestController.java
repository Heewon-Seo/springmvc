package hello.springmvc.basic;

/*
SLF4J - http://www.slf4j.org (롬복 사용 가능)
Logback - http://logback.qos.ch

로그 라이브러리는 Logback, Log4J, Log4J2 등등 수 많은 라이브러리가 있는데, 그것을 통합해서
인터페이스로 제공하는 것이 바로 SLF4J 라이브러리다.
쉽게 이야기해서 SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다.
실무에서는 스프링 부트가 기본으로 제공하는 Logback을 대부분 사용한다.
 */
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
- @Contoller는 반환값이 String이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
- @RestController는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
  따라서 실행 결과로 OK 메시지를 받을 수 있다. @ResponseBody와 관련이 있는데, 뒤에서 더 자세히 설명한다.
 */
@RestController
@Slf4j // 롬복을 사용하면 밑에 필드 선언을 안해도 된다!
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        // 로그를 사용하지 않아도 a+b 계산 로직이 먼저 실행됨, 이런 방식으로 사용하면 안됨
//        log.debug("String concat log = " + name);

        /*
        올바른 로그 사용법
        log.debug("data="+data);
        - 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data=" + data 가 실제 실행이 되어 버린다.
        - 결과적으로 문자 더하기 연산이 발생한다.
        log.debug("data={}", data)
        - 로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다.
        - 따라서 앞과 같은 의미없는 연산이 발생하지 않는다.
         */

        return "OK";
    }


}
