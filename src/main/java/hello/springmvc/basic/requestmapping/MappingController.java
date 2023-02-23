package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MappingController {

    /**
     * 기본 요청
     * 둘다 허용 /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
     * /hello-basic URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
     * 대부분의 속성을 배열[] 로 제공하므로 다중 설정이 가능하다. {"/hello-basic", "/hello-go"}
     * @return OK
     */
    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("hello Basic");
        return "OK";
    }

    /*
    스프링 부트 3.0 이후
    스프링 부트 3.0 부터는 /hello-basic , /hello-basic/ 는 서로 다른 URL 요청을 사용해야 한다.
    기존에는 마지막에 있는 / (slash)를 제거했지만, 스프링 부트 3.0 부터는 마지막의 / (slash)를 유지한다.
    따라서 다음과 같이 다르게 매핑해서 사용해야 한다.
    매핑: /hello-basic URL 요청: /hello-basic
    매핑: /hello-basic/ URL 요청: /hello-basic/
     */

    /**
     * method 특정 HTTP 메서드 요청만 허용
     * GET
     * @return OK
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mapping Get V1");
        return "OK";
    }

    /**
     * 편리한 축약 애노테이션
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     * @return OK
     */
    @GetMapping(value = "/mapping-get-v2") // 이렇게 하는 게 더 직관적
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "OK";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @RequestMapping 은 URL 경로를 템플릿화 할 수 있는데, @PathVariable 을 사용하면 매칭 되는 부분을
     * 편리하게 조회할 수 있다.
     * @PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.
     * @PathVariable("userId") String userId -> @PathVariable userId
     * @param data
     * @return "OK"
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mapping userId = {}", data);
        return "OK";
    }
    /*
    최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.
    - /mapping/userA
    - /users/1
     */

    /**
     * PathVariable 다중 사용
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId = {}, orderId = {}", userId, orderId);
        return "OK";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (!=)
     * params={"mode=debug", "data=good"}
     * @return "OK"
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "OK";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode"
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (!=)
     * @return OK
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "OK";
        // 파라미터 매핑과 비슷하지만 HTTP 헤더를 사용한다.
        // POSTMAN으로 테스트 해야 한다.
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     * @return OK
     * HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.
     * 만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "OK";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     * @return OK
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "OK";
    }
    /*
    HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
    만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.
     */




}
