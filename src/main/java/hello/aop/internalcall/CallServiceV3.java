package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;




@Slf4j
@Component
public class CallServiceV3 {


    //@Autowired
    @Resource(name = "internalService")
    InternalService internalService;
    
    public void external() {
        log.info("call external");
        internalService.internal(); //구조를 분리하여 InternalService를 호출
    }

}
