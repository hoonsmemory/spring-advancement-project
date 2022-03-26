package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyCastingTest {

    @Test
    void jdkPorxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); //JDK 동적 프록시, true : CGLIB 프록시
        
        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServicePorxy = (MemberService)proxyFactory.getProxy();

        /**
         * JDK 동적 프록시는 인터페이스를 기반으로 프록시를 생성하기 때문에
         * MemberServiceImpl이 어떤 것인지 전혀 알지 못한다.
         * 따라서 MemberServiceImpl 타입으로는 캐스팅이 불가능하다.
         * 캐스팅을 시도하면 ClassCastException.class 예외가 발생한다.
         */
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServicePorxy;
        });
    }

    @Test
    void cglibPorxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServicePorxy = (MemberService)proxyFactory.getProxy();

        /**
         * CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
         * CGLIB는 구체 클래스를기반으로 프록시를 생성하기 때문이다. (부모 타입 캐스팅 가능)
         */
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServicePorxy;

    }
}
