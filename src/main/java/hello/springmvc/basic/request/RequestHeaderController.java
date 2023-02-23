package hello.springmvc.basic.request;


import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // == private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestHeaderController.class);
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(
        HttpServletRequest request,
        HttpServletResponse response,
        HttpMethod httpMethod, // HTTP 메서드를 조회한다 (org.springframework.http.HttpMethod)
        Locale locale, // Locale 정보를 조회한다
        @RequestHeader MultiValueMap<String, String> headerMap, // 모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
        @RequestHeader("host") String host, // 특정 HTTP 헤더를 조회한다 (속성: 필수값여부- required, 기본값속성- defaultValue)
        @CookieValue(value = "myCookie", required = false) String cookie // 특정 쿠키를 조회한다 (속성: 필수값여부, 기본값)
    ) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("headerHost={}", host);
        log.info("myCookie={}", cookie);

        /*
        MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
        HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다.
        keyA=value1&keyA=value2
         */
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("keyA", "value1");
        map.add("keyA", "value2");

        // [value1, value2]
        List<String> values = map.get("keyA");
        log.info("map={}", values);

        return "OK";
    }

}
