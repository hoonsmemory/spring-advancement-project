package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {


    CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("CallServiceV1 setter = {}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }
    /**
     * 내부 호출을 할 경우 호출된 메서드는 프록시를 거치지 않는다.
     */
    public void external() {
        log.info("call external");
        callServiceV1.internal(); //자기 자신을 의존관계 주입 받기
    }

    public void internal() {
        log.info("call internal");
    }
}
