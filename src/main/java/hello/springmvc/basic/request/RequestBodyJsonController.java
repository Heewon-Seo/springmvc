package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {"username": "hello", "age": 20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    /*
    이번에는 HTTP API에서 주로 사용하는 JSON 데이터 형식을 조회해보자
     */

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // HttpServletRequest를 사용해서 직접 HTTP 메시지 바디에서 데이터를 읽어와서, 문자로 변환한다.
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody= {}", messageBody);
        // 문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper를 사용해서 자바 객체로 변환한다.
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        response.getWriter().write("OK");
    }

    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환 (view 조회 X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @param messageBody
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    // @RequestBody를 사용해서 HTTP 메시지에서 데이터를 꺼내서 messageBody에 저장
    public String requestBodyV2(@RequestBody String messageBody) throws IOException {
        // 문자로 된 JSON 데이터인 messageBody를 objectMapper를 통해서 자바 객체로 변환
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "OK";
    }

    /*
    문자로 변환하고 다시 JSON으로 변환하는 과정이 불편한다.
    @ModelAttribute처럼 한번에 객체로 변화할 수는 없을까?
     */

    /**
     * @RequestBody 생략 불가능 (@ModelAttribute가 적용되어 버림)
     * **생략하면 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게 됨!
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter
     * (content-type: application/json)
     *
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) { // @RequestBody에 직접 만든 객체르 지정할 수 있다
        log.info("username={} , age={}", data.getUsername(), data.getAge());
        return "OK";
    }
    /*
    HttpEntity , @RequestBody를 사용하면
    HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.
    HTTP 메시지 컨버터는 문자 뿐만 아니라 <JSON도 객체>로 변환해주는데,
    우리가 방금 V2에서 했던 작업을 대신 처리해준다.
    (HelloData data = objectMapper.readValue(messageBody, HelloData.class);)

    **주의**
    HTTP 요청시에 content-type이 application/json인지 꼭! 확인해야 한다.
    그래야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.
     */


    // 물론 앞서 배운것과 같이 HttpEntity를 사용해도 된다.
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "OK";
    }


    /**
     * @RequestBody 생략 불가능 (@ModelAttribute가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter
     * (content-type: application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환 (view 조회 X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter
     * (Accept: application/json)
     *
     * @param data
     * @return
     */
    @ResponseBody
    // @ResponseBody 응답: 객체 -> HTTP 메시지 컨버터 -> JSON 응답
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        // @RequestBody: JSON 요청 -> HTTP 메시지 컨버터 -> 객체
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }


}
