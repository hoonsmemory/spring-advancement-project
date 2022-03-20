package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6Advice {
    /**
     * 어드바이스 종류
     * > @Around : 메서드 호출 전후에 수행, 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능
     * > @Before : 조인 포인트 실행 이전에 실행@AfterReturning : 조인 포인트가 정상 완료후 실행
     * > @AfterThrowing : 메서드가 예외를 던지는 경우 실행
     * > @After : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)
     *
     * 실행 순서: @Around, @Before, @After, @AfterReturning, @AfterThrowing
     * 리턴 순서: @AfterThrowing, @AfterReturning, @After, @Around
     */

    @Around("hello.aop.order.aop.pointcut.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            // ProceedingJoinPoint만이 다음 어드바이스나 타겟을 호출한다.
            Object result = joinPoint.proceed();
            //@AfterThrowing
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.error("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.pointcut.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[트랜잭션 시작 전 동작] {}", joinPoint.getSignature());
    }

    /**
     * > @Around처럼 result의 값 return하지 않기 때문에 변경할 수는 없다.
     * result 타입은 해당 메서드의 타입과 일치해야한다.
     * ex) 메서드의 매개변수 타입이 String일 경우 result의 타입도 String으로 적용해야함.
     */
    @AfterReturning(value = "hello.aop.order.aop.pointcut.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[트랜잭션 완료 후 동작] {} result = {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.pointcut.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[이벤트 실행 중 오류 발생 시 동작] {} Error Message = {}", joinPoint.getSignature(), ex);
    }

    @After(value = "hello.aop.order.aop.pointcut.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        //finally
        log.info("[이벤트 실행 완료 후 동작] {} ", joinPoint.getSignature());
    }
}
