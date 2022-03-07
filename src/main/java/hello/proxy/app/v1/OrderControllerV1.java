package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 스프링MVC는 타입에 @Controller 또는 @RequestMapping 애노테이션이 있어야 스프링 컨트롤러로 인식한다.
 * 그리고 스프링 컨트롤러로 인식해야, HTTP URL이 매핑되고 동작한다.
 * 이 애노테이션은 인터페이스에 사용해도 된다.
 */
@RequestMapping
@ResponseBody // HTTP 메시지 컨버터를 사용해서 응답한다. 이 애노테이션은 인터페이스에 사용해도 된다.
public interface OrderControllerV1 {

    @GetMapping("/proxy/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/proxy/v1/no-log")
    String noLog();

}
