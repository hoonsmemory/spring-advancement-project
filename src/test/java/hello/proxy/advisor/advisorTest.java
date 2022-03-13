package hello.proxy.advisor;

import hello.proxy.pureproxy.common.advice.TimeAdvice;
import hello.proxy.pureproxy.common.service.ServiceImpl;
import hello.proxy.pureproxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class advisorTest {

    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //하나의 advisor는 하나의 pointcut과 하나의 advice를 갖는다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트 컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //하나의 advisor는 하나의 pointcut과 하나의 advice를 갖는다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MymethodMatcher();
        }
    }

    static class MymethodMatcher implements MethodMatcher {

        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);
            log.info("포인트컷 호출 method = {} targetClass = {}", method.getName(), targetClass);
            log.info("포인트컷 결과 result = {}", result);

            return result;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트 컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //하나의 advisor는 하나의 pointcut과 하나의 advice를 갖는다.
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save*", "find*");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }    

}
