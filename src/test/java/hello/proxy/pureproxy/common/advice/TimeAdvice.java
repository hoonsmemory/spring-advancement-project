package hello.proxy.pureproxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        //타겟 클래스를 호출하고 그 결과를 받는다.
        //프록시 팩토리를 구현했을 때 target에 대한 정보를 넘기기 때문에
        //invocation안에 클래스에 대한 정보가 다 포함되어있다.
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime = {}", resultTime);

        return result;
    }
}
