package hello.springmvc.basic.response;


import hello.springmvc.basic.HelloData;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
//@RestController
/*
@Controller 대신에 @RestController를 사용하면
해당 컨트롤러에 모두 @ResponseBody가 적용되는 효과가 있다.
따라서 뷰 템플릿을 사용하는 것이 아니라,
HTTP 메시지 바디에 직접 데이터를 입력한다.
이름 그대로 Rest API (HTTP API)를 만들 때 사용하는 컨트롤러이다.

참고로 @ResponseBody는 클래스 레벨에 두면 전체 메서드에 적용되는데,
@RestController 애노테이션 안에
@ResponseBody가 적용되어 있다.
 */
public class ResponseBodyController {

    /**
     * 서블릿을 직접 다룰 때 처럼 HttpServletResponse 객체를 통해
     * HTTP 메시지 바디에 직접 OK 응답 메시지를 전달
     * @param response
     * @throws IOException
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("OK");
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * ResponseEntity 엔티티는 HttpEntity를 상속 받았는데,
     * HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다.
     *
     * ResponseEntity는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
     * @return
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
        // HttpStatus.CREATED로 변경하면 201 응답이 나가는 것을 확인할 수 있다.
    }


    /**
     * @ResponseBody를 사용하면 view를 사용하지 않고,
     * HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다.
     * ResponseEntity도 동일한 방식으로 동작한다.
     * @return
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "OK";
    }

    /**
     * ResponseEntity를 반환한다.
     * HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어서 반환
     * @return
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }


    /**
     * ResponseEntity는 HTTP 응답 코드를 설정할 수 있는데,
     * @ResponseBody를 사용하면 이런 것을 설정하기 까다롭다.
     *
     * @ReponseStatus(HttpStatus.OK) 해당 애노테이션을 사용하면 응답 코드도 설정할 수 있다.
     * 물론 애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다.
     * 프로그램 조건에 따라서 동적으로 변경하려면 ResponseEntity를 사용하면 된다.
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }

}
