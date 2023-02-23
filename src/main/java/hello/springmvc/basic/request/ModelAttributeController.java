package hello.springmvc.basic.request;


import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ModelAttributeController {

    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨,
     * 뒤에 model을 설명할 때 자세히 설명
     *
     * @param helloData
     * @return
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "OK";
    }

    /*
    스프링MVC는 @ModelAttribute가 있으면 다음을 실행한다
    - HelloData 객체를 생성한다
    - 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
    - 그리고 해당 프로퍼티의 setter를 호출해서 파라미터 값을 입력(바인딩) 한다.
    - 예) 파라미터 이름이 username이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.


    # 프로퍼티
    객체에 getUsername(), setUsername() 메서드가 있으면, 이 객체는 username이라는 프로퍼티를 가지고 있다.
    username 프로퍼티의 값을 변경하면 setUsername()이 호출되고, 조회하면 getUsername()이 호출된다.

    # 바인딩 오류
    age=abc 처럼 숫자가 들어가야할 곳에 문자를 넣으면 BindException이 발생한다.
    이러한 바인딩 오류를 처리하는 방법은 검증 부분에서 다룬다.
     */


    /**
     * @ModelAttribute 생략 가능
     * String, int와 같은 단순 타입 = @RequestParam
     * argument resolver로 지정해둔 타입 외 = @ModelAttribute
     *
     * @param helloData
     * @return
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "OK";
    }

    /*
    @ModelAttribute는 생략할 수 있다.
    그런데 @RequestParam도 생략할 수 있으니 혼란이 발생할 수 있다.

    그래서 단순타입은 @RequestParam, 나머지는 @ModelAttribute를 쓴다.
     */

}
