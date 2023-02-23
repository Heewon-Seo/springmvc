/*
HTTP 응답 - 정적 리소스, 뷰 템플릿

응답 데이터는 이미 앞에서 일부 다룬 내용이지만, 응답 부분에 초점을 맞추어서 정리
스프링 (서버)에서 응답 데이터를 만드는 방법은 크게 3가지이다.

- 정적 리소스
    - 예) 웹 브라우저에 정적인 HTML, CSS, JS를 제공할 때는 정적 리소스를 사용한다.
- 뷰 템플릿 사용
    - 예) 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.
- HTTP 메시지 사용
    - HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로,
    HTTP 메시지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다.
 */

package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;

@Controller
public class ResponseViewController {

}
