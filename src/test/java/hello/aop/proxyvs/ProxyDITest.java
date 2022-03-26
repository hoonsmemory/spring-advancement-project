package hello.aop.proxyvs;

import hello.aop.AopApplication;
import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(classes = AopApplication.class, properties = "spring.aop.proxy-target-class=false")
@SpringBootTest(classes = AopApplication.class, properties = "spring.aop.proxy-target-class=true")
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void goJdkProxy() {
        log.info("memberService class = {}", memberService.getClass());
        /**
         * hello.aop.member.MemberServiceImpl으로 넘어와야 하는데
         * com.sun.proxy.$Proxy62으로 넘어와 타입 예외가 발생(의존관계 주입 불가능)
         * 따라서 부모타입인 인터페이스를 주입 받아야한다.
         */
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }

    @Test
    void goCglibProxy() {
        log.info("memberService class = {}", memberService.getClass());
        /**
         * CGLIB는 구체 클래스를 기반으로 만들어지기 때문에 부모 타입까지 캐스팅 가능하다.
         */
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }


}
