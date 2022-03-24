package hello.aop.pointcut;

import hello.aop.AopApplication;
import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(classes = AopApplication.class)
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}", memberService);
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {}

        /**
         * logArgs1 : joinPoint.getArgs()[0] 와 같이 매개변수를 전달 받는다.
         * logArgs2 : args(arg,..) 와 같이 매개변수를 전달 받는다.
         * logArgs3 : @Before 를 사용한 축약 버전이다. 추가로 타입을 String 으로 제한했다.
         * this : 프록시 객체를 전달 받는다.
         * target : 실제 대상 객체를 전달 받는다.
         * > @target , @within : 타입의 애노테이션을 전달 받는다.
         * > @annotation : 메서드의 애노테이션을 전달 받는다. 여기서는 annotation.value() 로 해당 애노테이션의 값을 출력하는 모습을 확인할 수 있다.
         */


        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable{
            Object args1 = joinPoint.getArgs()[0];

            log.info("[logArgs1]{}, args1 = {}", joinPoint.getSignature(), args1);

            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable{
            log.info("[logArgs2]{}, arg = {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg) throws Throwable{
            log.info("[logArgs3] args = {}",arg);
        }

        /**
         *  스프링 컨테이너에 들어있는 대상(프록시가 될 수 있다.)
         */
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) throws Throwable{
            log.info("[this] obj = {}", obj.getClass());
        }

        /**
         *  실제 타켓이 되는 객체(프록시가 있다면 프록시 소스 안에서의 호출되는 대상)
         */
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) throws Throwable{
            log.info("[target] {} obj = {}",joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTargetArgs(JoinPoint joinPoint, ClassAop annotation) throws Throwable{
            log.info("[@target] {} annotation = {}",joinPoint.getSignature(), annotation.getClass());
        }

        @Before("allMember() && @within(annotation)")
        public void atWithinArgs(JoinPoint joinPoint, ClassAop annotation) throws Throwable{
            log.info("[@within] {} annotation = {}",joinPoint.getSignature(), annotation.getClass());
        }

        @Before("allMember() && @annotation(annotation)")
        public void atannotation(JoinPoint joinPoint, MethodAop annotation) throws Throwable{
            log.info("[@annotation] {} annotationValue = {}",joinPoint.getSignature(), annotation.value());
        }
    }

}
