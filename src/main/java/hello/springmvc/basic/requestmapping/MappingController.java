package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * @PathVariable("userId") String userId -> @PathVariable userId
     * @param data
     * @return "OK"
     */
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mapping userId = {}", data);
        return "OK";
    }

}
