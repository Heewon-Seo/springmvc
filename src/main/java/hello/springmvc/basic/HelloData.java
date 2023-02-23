package hello.springmvc.basic;

import lombok.Data;

// @Getter, @Setter, @ToString, @EqualAndHashCode, @RequiredArgsConstructor를 자동으로 적용
@Data
public class HelloData {
    private String username;
    private int age;

}
