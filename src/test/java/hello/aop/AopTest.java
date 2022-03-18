package hello.aop;

import hello.proxy.ProxyApplication;
import hello.proxy.app.v3.OrderRepositoryV3;
import hello.proxy.app.v3.OrderServiceV3;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = ProxyApplication.class)//@SpringBootApplication 클래스를 찾을 수 있도록 클래스명 직접 입력
public class AopTest {

    @Autowired
    OrderServiceV3 orderService;

    @Autowired
    OrderRepositoryV3 orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService = {}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository = {}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}
