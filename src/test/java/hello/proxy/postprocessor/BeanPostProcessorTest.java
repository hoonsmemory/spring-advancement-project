package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    void basicConfig() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
        B beanA = (B) applicationContext.getBean("beanA");
        beanA.helloB();

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));
    }

    @Slf4j
    @Configuration
    static class BasicConfig {

        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AToBPostProcessor postProcessor() {
            return new AToBPostProcessor();
        }
    }

    @Slf4j
    static class A implements C {
        public void helloA() {
            log.info("hello A");
        }
    }

    interface C {

    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }

    /**
     * 빈후처리기
     */
    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {} bean = {}", beanName, bean);

            //bean이 A일 경우 스프링 빈 저장소에 B 인스턴스를 저장한다.
            if (bean instanceof A) {
                return new B();
            }

            return bean;
        }
    }
}
