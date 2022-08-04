package hello.proxy.pureproxy.proxy.cglib;

import hello.proxy.pureproxy.common.service.ConcreteService;
import hello.proxy.pureproxy.proxy.cglib.code.TimeMethodInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class); //타겟 클래스 지정
        enhancer.setCallback(new TimeMethodInterceptor(target)); //동적으로 만들 템플릿 지정
        ConcreteService proxy = (ConcreteService) enhancer.create(); // 프록시 생성

        proxy.call();
    }
}
