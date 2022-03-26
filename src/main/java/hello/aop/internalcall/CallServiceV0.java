package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    /**
     * 내부 호출을 할 경우 호출된 메서드는 프록시를 거치지 않는다.
     */
    public void external() {
        log.info("call external");
        internal(); //내부 메서드 호출(this.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
