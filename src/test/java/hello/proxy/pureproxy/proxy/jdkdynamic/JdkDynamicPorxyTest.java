package hello.proxy.pureproxy.proxy.jdkdynamic;

import hello.proxy.pureproxy.proxy.jdkdynamic.code.AImpl;
import hello.proxy.pureproxy.proxy.jdkdynamic.code.AInterface;
import hello.proxy.pureproxy.proxy.jdkdynamic.code.TimeInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicPorxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();

        //동적 프록시에 적용할 핸들러 로직
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        //동적 프록시를 생성하기 위한 필요한 정보 : 클래스 로더 정보, 인터페이스, 핸들러 로직
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}
