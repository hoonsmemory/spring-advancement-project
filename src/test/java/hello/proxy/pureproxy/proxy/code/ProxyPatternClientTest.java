package hello.proxy.pureproxy.proxy.code;

import org.junit.jupiter.api.Test;


public class ProxyPatternClientTest {

    @Test
    void noProxyTest() {
        RealSubject subject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(subject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealSubject subject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(subject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}