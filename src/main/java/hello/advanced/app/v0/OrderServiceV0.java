package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service// 컴포넌트 스캔의 대상이 된다
@RequiredArgsConstructor//초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성한다.
public class OrderServiceV0 {

    private final OrderRepositoryV0 orderRepository;

    public void orderItem(String itemId) {
        //업무 로직 생략..

        orderRepository.save(itemId);
    }
}