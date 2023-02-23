package hello.springmvc.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
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

@Slf4j
@Controller
public class RequestBodyStringController {

    /*
    서블릿에서 학습한 내용을 떠올려보자.
    - HTTP message body에서 데이터를 직접 담아서 요청
        - HTTP API에서 주로 사용, JSON, XML, TEXT
        - 데이터 형식은 주로 JSON 사용
        - POST, PUT, PATCH

     요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우는
     @RequestParam, @ModelAttribute를 사용할 수 없다.
     (물론 HTML Form 형식으로 전달되는 경우는 요청 파라미터로 인정된다.)
     - 먼저 가장 단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자
     - HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.
     */

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("OK");
    }

    /**
     * InputStream (Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream (Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     *
     * @param inputStream
     * @param responseWriter
     * @throws IOException
     */
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);
        responseWriter.write("OK");
    }

    /**
     * HttpEntity: HTTP reader, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회 (@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter  사용 -> StringHttpMessageConverter 적용
     *
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환 (view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * @param httpEntity
     * @return
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody = {}", messageBody);

        return new HttpEntity<>("OK");
        /*
        HttpEntity: HTTP header, body 정보를 편리하게 조회
        - 메시지 바디 정보를 직접 조회
        - 요청 파라미터를 조회하는 기능과 관계 없음
        HttpEntity는 응답에도 사용 가능
        - 메시지 바디 정보 직접 반환
        - 헤더 정보 포함 기능
        - view 조회 X

        HttpEntity를 상속받은 다음 객체들도 같은 기능을 제공한다
        - RequestEntity
            - HttpMethod, URL 정보가 추가, 요청에서도 사용
        - ResponseEntity
            - HTTP 상태 코드 설정 가능, 응답에서 사용
            - return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)

        스프링MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체로 변환해서 전달해 주는데,
        이 때 HTTP 메시지 컨버터 라는 기능을 이용한다.
         */
    }

    /**
     * @RequestBody
     * - 메시지 바디 정보를 직접 조회 (@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter  사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 메시지 바디 정보 직접 반환 (view 조회 X)
     * - HttpMessageConverter  사용 -> StringHttpMessageConverter 적용
     *
     * @param messageBody
     * @return
     */
    @ResponseBody // HTTP 메시지 바디 정보 편리하게 조회
    // 헤더 정보가 필요할 경우에는 HttpEntity OR @RequestHeader 사용
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody = {}", messageBody);
        return "OK";
    }

    /*
    - 요청 파라미터를 조회하는 기능: @RequestParam, @ModelAttribute
    - HTTP 메시지 바디를 직접 조회하는 기능: @RequestBOdy
     */

}
