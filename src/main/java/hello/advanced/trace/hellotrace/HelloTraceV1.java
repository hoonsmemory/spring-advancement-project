package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component //싱글톤으로 사용하기 위해 스프링 빈으로 등록한다. 컴포넌트 스캔의 대상이 된다.
public class HelloTraceV1 {

    //로그의 레벨을 나타낸다.
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    //로그의 시작.
    //시작로그를 출력한다.
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();

        //트랜잭션 ID, 트랜잭션 lever,
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        //로그 출력(상태 반환)
       return new TraceStatus(traceId, startTimeMs, message);
    }

    //로그의 종료.
    public void end(TraceStatus status) {
        complete(status, null);
    }

    //예외 발생 시 동작.
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    //end(), exception()의 요청 흐름을 한곳에서 편리하게 처리한다.
    //실행 시간을 측정하고 로그를 남긴다.
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    //하나의 트랜잭션의 레벨을 나타내기 위해 사용된다.
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
