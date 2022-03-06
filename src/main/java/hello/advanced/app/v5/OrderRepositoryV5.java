package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository //컴포넌트 스캔의 대상이 된다. 따라서 스프링 빈으로 자동 등록된다.
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    //저장
    public void save(String itemId) {
        template.execute("OrderRepository.save()", () -> {
            //예외가 발생하는 상황도 확인하기 위해 파라미터 itemId 의 값이 ex 로 넘어오면
            //IllegalStateException 예외가 발생하도록 했다.
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            //리포지토리는 상품을 저장하는데 약 1초 정도 걸리는 것으로 가정하기 위해 1초 지연을 주었다. (1000ms)
            sleep(1000);
            return null;
        });
    }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
