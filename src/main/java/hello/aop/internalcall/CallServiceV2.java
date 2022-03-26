package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    CallServiceV2 callServiceV2;

    //private final ApplicationContext applicationContext; //applicationContext 기능이 너무 많기 때문에  ObjectProvider 사용
    private final ObjectProvider<CallServiceV2> callServiceV2ObjectProvider;
    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2ObjectProvider) {
        this.callServiceV2ObjectProvider = callServiceV2ObjectProvider;
    }
    
    public void external() {
        log.info("call external");
        // CallServiceV2 callServiceV2 = callServiceV2ObjectProvider.getBean(CallServiceV2.class);
        /**
         * ObjectProvider 는 객체를 스프링 컨테이너에서 조회하는 것을 스프링 빈 생성 시점이 아니라
         * 실제 객체를 사용하는 시점으로 지연할 수 있다.
         */
        CallServiceV2 callServiceV2 = callServiceV2ObjectProvider.getObject();
        callServiceV2.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
