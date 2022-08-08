# 스프링 핵심 원리 심화편 정리
### ThreadLocal
크게 3가지 함수가 있다. set(), get(), remove()
쓰레드로컬은 동시성 문제를 해결할 수 있지만 사용 후 remove()를 하지 않으면 쓰레드풀을 사용할 시 전에 쓰던 데이터가 남아있기 때문에 큰 문제가 될 수 있다.


### 템플릿 메서드 패턴
<img width="592" alt="AbstractTemplate" src="https://user-images.githubusercontent.com/34621693/183357022-a0d0ba42-b0f9-4fac-89db-93d95552efbb.png">
변하는 것과 변하지 않는 것을 분리. 좋은 설계는 변하는 것과 변하지 않는 것을 분리하는 것이다. 
여기서 핵심 기능 부분은 변하고, 로그 추적기를 사용하는 부분은 변하지 않는 부분이다. 이 둘을 분리해서 모듈화해야 한다. 
템플릿 메서드 패턴(Template Method Pattern)은 이런 문제를 해결하는 디자인 패턴이다. 
부모 클래스에 알고리즘의 골격인 템플릿을 정의하고, 일부 변경되는 로직은 자식 클래스에 정의하는 것이다. 
이렇게 하면 자식 클래스가 알고리즘의 전체 구조를 변경하지 않고, 특정 부분만 재정의할 수 있다. 
결국 상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것이다. 

단점
템플릿 메서드 패턴은 상속을 사용한다. 따라서 상속에서 오는 단점들을 그대로 안고간다. 
특히 자식 클래스가 부모 클래스와 컴파일 시점에 강하게 결합되는 문제가 있다. 이것은 의존관계에 대한 문제이다. 
자식 클래스 입장에서는 부모 클래스의 기능을 전혀 사용하지 않는다. 
템플릿 메서드 패턴과 비슷한 역할을 하면서 상속의 단점을 제거할 수 있는 디자인 패턴이 바로 전략 패턴 (Strategy Pattern)이다. 


###전략 패턴
![Context](https://user-images.githubusercontent.com/34621693/183357344-11b0be18-1b74-476d-b33a-d7f49ca2c786.jpeg)
전략 패턴은 변하지 않는 부분을 Context 라는 곳에 두고, 변하는 부분을 Strategy 라는 인터페이스를 만들고 해당 인터페이스를 구현하도록 해서 문제를 해결한다. 
상속이 아니라 위임으로 문제를 해결하는 것이다. 전략 패턴에서 Context 는 변하지 않는 템플릿 역할을 하고, Strategy 는 변하는 알고리즘 역할을 한다. 
Context 클래스의 필드 부분에는 Strategy 인터페이스를 필드로 가지고 생성자로 받는다.
** 스프링에서 의존관계 주입에서 사용하는 방식이 전략 패턴이다.
Context 와 Strategy 를 한번 조립하고 나면 이후로는 Context 를 실행하기만 하면 된다. 
우리가 스프링으로 애플리케이션을 개발할 때 애플리케이션 로딩 시점에 의존관계 주입을 통해 필요한 의존관계를 모두 맺어두고 난 다음에 실제 요청을 처리하는 것 과 같은 원리이다.

단점
이 방식의 단점은 Context 와 Strategy 를 조립한 이후에는 전략을 변경하기가 번거롭다는 점이다. 
물론 Context 에 setter 를 제공해서 Strategy 를 넘겨 받아 변경하면 되지만, Context 를 싱글톤으로 사용할 때는 동시성 이슈 등 고려할 점이 많다. 
그래서 전략을 실시간으로 변경해야 하면 차라리 이전에 개발한 테스트 코드 처럼 Context 를 하나더 생성하고 그곳에 다른 Strategy 를 주입하는 것이 더 나은 선택일 수 있다. 

### 템플릿 콜백 패턴
![Template](https://user-images.githubusercontent.com/34621693/183362909-7610ce97-c1a8-43e5-acc9-e4d5f5962cf8.jpeg)
콜백 정의
<br/>프로그래밍에서 콜백(callback) 또는 콜애프터 함수(call-after function)는 다른 코드의 인수로서 넘겨주는 실행 가능한 코드를 말한다. 
콜백을 넘겨받는 코드는 이 콜백을 필요에 따라 즉시 실행할 수도 있고, 아니면 나중에 실행할 수도 있다. 

스프링에서는 JdbcTemplate , RestTemplate , TransactionTemplate , RedisTemplate 처럼 다양한 템플릿 콜백 패턴이 사용된다. 
스프링에서 이름에 XxxTemplate 가 있다면 템플릿 콜백 패턴으로 만들어져 있다 생각하면 된다. 


### 빈 등록
빈등록으로 자주 쓰이는 3가지
* 인터페이스와 구현 클래스 - 스프링 빈으로 수동 등록 
* 인터페이스 없는 구체 클래스 - 스프링 빈으로 수동 등록 
* 컴포넌트 스캔으로 스프링 빈 자동 등록 


### 프록시 패턴과 데코레이터 패턴

프록시의 주요 기능 
프록시를 통해서 할 수 있는 일은 크게 2가지로 구분할 수 있다. 
접근 제어 
* 권한에 따른 접근 차단 
* 캐싱
* 지연 로딩 
부가 기능 추가
* 원래 서버가 제공하는 기능에 더해서 부가 기능을 수행한다. 
* 예) 요청 값이나, 응답 값을 중간에 변형한다.
* 예) 실행 시간을 측정해서 추가 로그를 남긴다. 

둘다 프록시를 사용하는 방법이지만 GOF 디자인 패턴에서는 이 둘을 의도(intent)에 따라서 프록시 패턴과 데코레이터 패턴으로 구분한다.
* 프록시 패턴: 접근 제어가 목적
* 데코레이터 패턴: 새로운 기능 추가가 목적 


### 프록시 패턴
![Subject](https://user-images.githubusercontent.com/34621693/183362979-cb3ab661-6e6d-4678-b498-23b4157b1a2e.jpeg)
호출 구조 : Client(Proxy).operation() —> Proxy(RealSubject).operation() —> RealSubject.operation()

프록시 패턴의 핵심은 RealSubject 코드와 클라이언트 코드를 전혀 변경하지 않고, 프록시를 도입해서 접근 제어를 했다는 점이다. 그리고 클라이언트 코드의 변경 없이 자유롭게 프록시를 넣고 뺄 수 있다. 실제 클라이언트 입장에서는 프록시 객체가 주입되었는지, 실제 객체가 주입되었는지 알지 못한다. 

### 데코레이터 패턴
![Component](https://user-images.githubusercontent.com/34621693/183363059-f70d53ca-f7bd-41aa-b4a6-63cdd3b8dc43.jpeg)
호출 구조 : Client(TimeDecorator).operation() —> TimeDecorator().operation(MessageDecorator) —> MessageDecorator(RealComponent).operation() —> RealComponent.operation()
** 프록시 패턴은 체이닝이 가능하다. (부가기능을 추가할 수 있다.)

데코레이터 패턴: 원래 서버가 제공하는 기능에 더해서 부가 기능을 수행한다. 
예) 요청 값이나, 응답 값을 중간에 변형한다. 예) 실행 시간을 측정해서 추가 로그를 남긴다. 

프록시 패턴 vs 데코레이터 패턴 
여기까지 진행하면 몇가지 의문이 들 것이다. 
Decorator 라는 추상 클래스를 만들어야 데코레이터 패턴일까? 
프록시 패턴과 데코레이터 패턴은 그 모양이 거의 비슷한 것 같은데? 

의도(intent) 사실 프록시 패턴과 데코레이터 패턴은 그 모양이 거의 같고, 상황에 따라 정말 똑같을 때도 있다. 그러면 둘을 어떻게 구분하는 것일까? 디자인 패턴에서 중요한 것은 해당 패턴의 겉모양이 아니라 그 패턴을 만든 의도가 더 중요하다. 따라서 의도에 따라 패턴을 구분한다. 

프록시 패턴의 의도: 다른 개체에 대한 접근을 제어하기 위해 대리자를 제공 
데코레이터 패턴의 의도: 객체에 추가 책임(기능)을 동적으로 추가하고, 기능 확장을 위한 유연한 대안 제공 

정리 
프록시를 사용하고 해당 프록시가 접근 제어가 목적이라면 프록시 패턴이고, 새로운 기능을 추가하는 것이 목적이라면 데코레이터 패턴이 된다. 


### 인터페이스 기반 프록시와 클래스 기반 프록시

인터페이스 기반 프록시 vs 클래스 기반 프록시
* 인터페이스가 없어도 클래스 기반으로 프록시를 생성할 수 있다.
* 클래스 기반 프록시는 해당 클래스에만 적용할 수 있다. 
* 인터페이스 기반 프록시는 인터페이스만 같으면 모든 곳에 적용할 수 있다.
* 클래스 기반 프록시는 상속을 사용하기 때문에 몇가지 제약이 있다. 
    * 부모 클래스의 생성자를 호출해야 한다. - super(null)..
    * 클래스에 final 키워드가 붙으면 상속이 불가능하다. 
    * 메서드에 final 키워드가 붙으면 해당 메서드를 오버라이딩 할 수 없다. 


클래스 기반 프록시의 단점 
* super(null) : OrderServiceV2 : 자바 기본 문법에 의해 자식 클래스를 생성할 때는 항상 super() 로 부모 클래스의 생성자를 호출해야 한다. 
      이 부분을 생략하면 기본 생성자가 호출된다. 그런데 부모 클래스인 OrderServiceV2 는 기본 생성자가 없고, 생성자에서 파라미터 1개를 필수로 받는다. 
      따라서 파라미터를 넣어서 super(..) 를 호출해야 한다. 
* 프록시는 부모 객체의 기능을 사용하지 않기 때문에 super(null) 을 입력해도 된다. 
* 인터페이스 기반 프록시는 이런 고민을 하지 않아도 된다. 

인터페이스 기반 프록시의 단점
* 인터페이스 기반 프록시의 단점은 인터페이스가 필요하다는 그 자체이다. 인터페이스가 없으면 인터페이스 기반 프록시를 만들 수 없다.
* 인터페이스 기반 프록시는 캐스팅 관련해서 단점이 있다.


### 리플렉션
리플렉션은 클래스나 메서드의 메타정보를 사용해서 동적으로 호출하는 메서드를 변경할 수 있다. 

주의 
리플렉션을 사용하면 클래스와 메서드의 메타정보를 사용해서 애플리케이션을 동적으로 유연하게 만들 수 있다. 
하지만 리플렉션 기술은 런타임에 동작하기 때문에, 컴파일 시점에 오류를 잡을 수 없다. 예를 들어서 지금까지 살펴본 코드에서 getMethod("callA") 안에 들어가는 문자를 실수로 getMethod("callZ") 로 작성해도 컴파일 오류가 발생하지 않는다. 
그러나 해당 코드를 직접 실행하는 시점에 발생하는 오류인 런타임 오류가 발생한다. 
가장 좋은 오류는 개발자가 즉시 확인할 수 있는 컴파일 오류이고, 가장 무서운 오류는 사용자가 직접 실행할 때 발생하는 런타임 오류다. 
따라서 리플렉션은 일반적으로 사용하면 안된다. 지금까지 프로그래밍 언어가 발달하면서 타입 정보를 기반으로 컴파일 시점에 오류를 잡아준 덕분에 개발자가 편하게 살았는데, 리플렉션은 그것에 역행하는 방식이다. 
리플렉션은 프레임워크 개발이나 또는 매우 일반적인 공통 처리가 필요할 때 부분적으로 주의해서 사용해야 한다. 


### JDK 동적 프록시
![$proxy1](https://user-images.githubusercontent.com/34621693/183363139-32eeb1e6-264b-41fb-8078-1b8dda47f774.jpeg)
동적 프록시 기술을 사용하면 개발자가 직접 프록시 클래스를 만들지 않아도 된다. 
이름 그대로 프록시 객체를 동적으로 런타임에 개발자 대신 만들어준다. 그리고 동적 프록시에 원하는 실행 로직을 지정할 수 있다. 
JDK 동적 프록시는 인터페이스를 기반으로 프록시를 동적으로 만들어준다. 따라서 인터페이스가 필수이다.

적용법
1. 프록시 템플릿을 만들 때 InvocationHandler 를 위임해주어야하며, 대상 타겟을 필드에 선언해주어야 한다.(private final Object target)
2. public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 함수를 오버라이딩 해야한다.
    * proxy : 프록시 자신, method : 호출한 메서드, args : 메서드를 호출할 때 전달한 인수
  3. Interface impl = new Impl();
      Interface proxy = (Interface) Proxy.newProxyInstance(Interface.class.getClassLoader(), new Class[]{Interface.class}, new InvocationHandler(impl));
    * 빈을 생성할 때 위와 같이 Proxy.newProxyInstance를 생성 후 리턴해주어야 한다.


### CGLIB
CGLIB: Code Generator Library 
CGLIB는 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리이다. 
CGLIB를 사용하면 인터페이스가 없어도 구체 클래스만 가지고 동적 프록시를 만들어낼 수 있다. 
CGLIB는 원래는 외부 라이브러리인데, 스프링 프레임워크가 스프링 내부 소스 코드에 포함했다. 
따라서 스프링을 사용한다면 별도의 외부 라이브러리를 추가하지 않아도 사용할 수 있다. 
참고로 CGLIB를 직접 사용하는 경우는 거의 없다. 
그 이유는 스프링의 ProxyFactory 라는 것이 이 기술을 편리하게 사용하게 도와주기 때문에, 너무 깊이있게 파기 보다는 CGLIB가 무엇인지 대략 개념만 잡으면 된다.

적용법
1. 프록시 템플릿을 만들 때 MethodInterceptor 를 위임해주어야하며, 대상 타겟을 필드에 선언해주어야 한다.(private final Object target)
2. public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable 함수를 오버라이딩 해야한다.
    * obj : CGLIB가 적용된 객체, method : 호출된 메서드, args : 메서드를 호출하면서 전달된 인수, proxy : 메서드 호출에 사용 

  3. Interface impl = new Impl();
      Interface proxy = (Interface) Proxy.newProxyInstance(Interface.class.getClassLoader(), new Class[]{Interface.class}, new InvocationHandler(impl));
    * 빈을 생성할 때 위와 같이 Proxy.newProxyInstance를 생성 후 리턴해주어야 한다.

CGLIB 제약 클래스 기반 프록시는 상속을 사용하기 때문에 몇가지 제약이 있다. 
* 부모 클래스의 생성자를 체크해야 한다. —> CGLIB는 자식 클래스를 동적으로 생성하기 때문에 기본 생성자가 필요하다.
* 클래스에 final 키워드가 붙으면 상속이 불가능하다. —> CGLIB에서는 예외가 발생한다. 
* 메서드에 final 키워드가 붙으면 해당 메서드를 오버라이딩 할 수 없다. —> CGLIB에서는 프록시 로직이 동작하지 않는다. 


### 프록시 팩토리
![jdk proxy](https://user-images.githubusercontent.com/34621693/183363171-78b15531-662f-41e1-908e-8da7ce0d4836.jpeg)
프록시 팩토리는 인터페이스가 있으면 JDK 동적 프록시를 사용하고, 구체 클래스만 있다면 CGLIB를 사용한다. 


### 포인트컷, 어드바이스, 어드바이저 
![스프링 핵심 원리 심화편](https://user-images.githubusercontent.com/34621693/183363241-b4bce363-4ee5-454e-a1a5-54d7336c7f90.jpeg)
1. 클라이언트가 프록시의 save() 를 호출한다. 
2. 포인트컷에 Service 클래스의 save() 메서드에 어드바이스를 적용해도 될지 물어본다. 
3. 포인트컷이 true 를 반환한다. 따라서 어드바이스를 호출해서 부가 기능을 적용한다. 
4. 이후 실제 인스턴스의 save() 를 호출한다. 

역할과 책임 
* 이렇게 구분한 것은 역할과 책임을 명확하게 분리한 것이다.
* 포인트컷은 대상 여부를 확인하는 필터 역할만 담당한다.
* 어드바이스는 깔끔하게 부가 기능 로직만 담당한다.
* 둘을 합치면 어드바이저가 된다. 스프링의 어드바이저는 하나의 포인트컷 + 하나의 어드바이스로 구성된다. 

중요
![advisors](https://user-images.githubusercontent.com/34621693/183363253-37e7fa1b-2f05-4274-b275-38485ed5a294.png)
스프링의 AOP를 처음 공부하거나 사용하면, AOP 적용 수 만큼 프록시가 생성된다고 착각하게 된다. 
스프링은 AOP를 적용할 때, 최적화를 진행해서 지금처럼 프록시는 하나만 만들고, 하나의 프록시에 여러 어드바이저를 적용한다.  정리하면 하나의 target 에 여러 AOP가 동시에 적용되어도, 스프링의 AOP는 target 마다 하나의 프록시만 생성한다. 이부분을 꼭 기억해두자. 


### 포인트컷( Pointcut )
어디에 부가 기능을 적용할지, 어디에 부가 기능을 적용하지 않을지 판단하는 필터링 로직이다. 주로 클래스와 메서드 이름으로 필터링 한다. 
이름 그대로 어떤 포인트(Point)에 기능을 적용할지 하지 않을지 잘라서(cut) 구분하는 것이다. 

결과적으로 포인트컷은 다음 두 곳에 사용된다. 
** 프록시를 만드는 단계에서 쓰는 포인트컷과 실제 실행단계에서 쓰는 포인트컷 구분을 잘 해야한다.
1. 프록시 적용 여부 판단 - 생성 단계(빈 후처리기) - 프록시 적용 대상 여부를 체크해서 꼭 필요한 곳에만 프록시를 적용한다. (빈 후처리기 - 자동 프록시 생성) 
* 자동 프록시 생성기는 포인트컷을 사용해서 해당 빈이 프록시를 생성할 필요가 있는지 없는지 체크한다.
* 클래스 + 메서드 조건을 모두 비교한다. 이때 모든 메서드를 체크하는데, 포인트컷 조건에 하나하나 매칭해본다. 만약 조건에 맞는 것이 하나라도 있으면 프록시를 생성한다. 
    * 예) orderControllerV1 은 request() , noLog() 가 있다. 여기에서 request() 가 조건에 만족하므로 프록시를 생성한다. 
* 만약 조건에 맞는 것이 하나도 없으면 프록시를 생성할 필요가 없으므로 프록시를 생성하지 않는다. 

2. 어드바이스 적용 여부 판단 - 사용 단계 - 프록시의 어떤 메서드가 호출 되었을 때 어드바이스를 적용할 지 판단한다. (프록시 내부)
* 프록시가 호출되었을 때 부가 기능인 어드바이스를 적용할지 말지 포인트컷을 보고 판단한다. 
* 앞서 설명한 예에서 orderControllerV1 은 이미 프록시가 걸려있다. 
* orderControllerV1 의 request() 는 현재 포인트컷 조건에 만족하므로 프록시는 어드바이스를 먼저 호출하고, target 을 호출한다. 
* orderControllerV1 의 noLog() 는 현재 포인트컷 조건에 만족하지 않으므로 어드바이스를 호출하지 않고 바로 target 만 호출한다. 

참고: 프록시를 모든 곳에 생성하는 것은 비용 낭비이다. 꼭 필요한 곳에 최소한의 프록시를 적용해야 한다. 
그래서 자동 프록시 생성기는 모든 스프링 빈에 프록시를 적용하는 것이 아니라 포인트컷으로 한번 필터링해서 어드바이스가 사용될 가능성이 있는 곳에만 프록시를 생성한다. 

스프링이 제공하는 포인트컷 
스프링은 무수히 많은 포인트컷을 제공한다. 대표적인 몇가지만 알아보자. 
* NameMatchMethodPointcut : 메서드 이름을 기반으로 매칭한다. 내부에서는 PatternMatchUtils 를 사용한다.  예) *xxx* 허용
* JdkRegexpMethodPointcut : JDK 정규 표현식을 기반으로 포인트컷을 매칭한다. 
* TruePointcut : 항상 참을 반환한다.
* AnnotationMatchingPointcut : 애노테이션으로 매칭한다. 
* AspectJExpressionPointcut : aspectJ 표현식으로 매칭한다. 

표현식
execution([리턴 타입] [패키지] [클래스] [메소드] [매게 변수])


### 어드바이스( Advice )
프록시가 호출하는 부가 기능이다. 단순하게 프록시 로직이라 생각하면 된다. 

어드바이스 종류 
@Around : 메서드 호출 전후에 수행, 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능 
@Before : 조인 포인트 실행 이전에 실행 
@AfterReturning : 조인 포인트가 정상 완료후 실행 @AfterThrowing : 메서드가 예외를 던지는 경우 실행 @After : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally) 

중요 
어드바이스 순서는 @Order를 통해 줄 수 있다. 
하지만 @Order는 클래스 단위로 줄 수 있기 때문에 만약 2개 이상의 advice를 사용하는데 순서가 중요하다면 클래스를 분리하여야 한다.


어드바이저( Advisor )
단순하게 하나의 포인트컷과 하나의 어드바이스를 가지고 있는 것이다. 쉽게 이야기해서 포인트컷1 + 어드바이스1이다. 




### 빈 후처리기 - BeanPostProcessor
![page2image26022384](https://user-images.githubusercontent.com/34621693/183363282-33588704-1887-48f4-aaae-9e7294d68cb3.png)
1. 생성: 스프링 빈 대상이 되는 객체를 생성한다. ( @Bean , 컴포넌트 스캔 모두 포함) 2. 전달: 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다. 3. 후 처리 작업: 빈 후처리기는 전달된 스프링 빈 객체를 조작하거나 다른 객체로 바뀌치기 할 수 있다. 
4. 등록: 빈 후처리기는 빈을 반환한다. 전달 된 빈을 그대로 반환하면 해당 빈이 등록되고, 바꿔치기 하면 다른 객체가 빈 저장소에 등록된다. 

스프링이 빈 저장소에 등록할 목적으로 생성한 객체를 빈 저장소에 등록하기 직전에 조작하고 싶다면 빈 후처리기를 사용하면 된다. 빈 포스트 프로세서( BeanPostProcessor )는 번역하면 빈 후처리기인데, 이름 그대로 빈을 생성한 후에 무언가를 처리하는 용도로 사용한다. 

빈 후처리기를 사용하려면 BeanPostProcessor 인터페이스를 구현하고, 스프링 빈으로 등록하면 된다.(등록된 모든 빈(@Component로 등록된 빈도 포함)이 빈후처리기를 거쳐간다.)
postProcessBeforeInitialization : 객체 생성 이후에 @PostConstruct 같은 초기화가 발생하기 전에 호출되는 포스트 프로세서이다. postProcessAfterInitialization : 객체 생성 이후에 @PostConstruct 같은 초기화가 발생한 다음에 호출되는 포스트 프로세서이다. (빈 초기화 완료 후)

참고 - @PostConstruct의 비밀 @PostConstruct 는 스프링 빈 생성 이후에 빈을 초기화 하는 역할을 한다. 
그런데 생각해보면 빈의 초기화 라는 것이 단순히 @PostConstruct 애노테이션이 붙은 초기화 메서드를 한번 호출만 하면 된다. 
쉽게 이야기해서 생성된 빈을 한번 조작하는 것이다. 따라서 빈을 조작하는 행위를 하는 적절한 빈 후처리기가 있으면 될 것 같다. 스프링은 CommonAnnotationBeanPostProcessor 라는 빈 후처리기를 자동으로 등록하는데, 여기에서 @PostConstruct 애노테이션이 붙은 메서드를 호출한다. 
따라서 스프링 스스로도 스프링 내부의 기능을 확장하기 위해 빈 후처리기를 사용한다.


### 자동 프록시 생성기 - AutoProxyCreator
![pointcut](https://user-images.githubusercontent.com/34621693/183363308-ccefde48-0048-4e4c-b1b6-9552e5a34cfa.png)
 자동 프록시 생성기의 작동 과정을 알아보자 1. 생성: 스프링이 스프링 빈 대상이 되는 객체를 생성한다. ( @Bean , 컴포넌트 스캔 모두 포함) 2. 전달: 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다. 3. 모든 Advisor 빈 조회: 자동 프록시 생성기 - 빈 후처리기는 스프링 컨테이너에서 모든 Advisor(return type이 Advisor) 를 조회한다. 4. 프록시 적용 대상 체크: 앞서 조회한 Advisor 에 포함되어 있는 포인트컷을 사용해서 해당 객체가 프록시를 적용할 대상인지 아닌지 판단한다. 
이때 객체의 클래스 정보는 물론이고, 해당 객체의 모든 메서드를 포인트컷에 하나하나 모두 매칭해본다. 
그래서 조건이 하나라도 만족하면 프록시 적용 대상이 된다. 예를 들어서 10개의 메서드 중에 하나만 포인트컷 조건에 만족해도 프록시 적용 대상이 된다. 5. 프록시 생성: 프록시 적용 대상이면 프록시를 생성하고 반환해서 프록시를 스프링 빈으로 등록한다. 만약 프록시 적용 대상이 아니라면 원본 객체를 반환해서 원본 객체를 스프링 빈으로 등록한다. 6. 빈 등록: 반환된 객체는 스프링 빈으로 등록된다. 

스프링 부트 자동 설정으로 AnnotationAwareAspectJAutoProxyCreator(자동 프록시 생성기) 라는 빈 후처리기가 스프링 빈에 자동으로 등록된다. 이름 그대로 자동으로 프록시를 생성해주는 빈 후처리기이다. 이 빈 후처리기는 스프링 빈으로 등록된 Advisor 들을 자동으로 찾아서 프록시가 필요한 곳에 자동으로 프록시를 적용해준다. 
Advisor 안에는 Pointcut 과 Advice 가 이미 모두 포함되어 있다. 
따라서 Advisor 만 알고 있으면 그 안에있는 Pointcut으로 어떤 스프링빈에 프록시를 적용해야 할 지 알수있다. 그리고 Advice로부가 기능을 적용하면 된다. 

자동 프록시 생성기는 2가지 일을 한다.
1. @Aspect 를 보고 어드바이저( Advisor )로 변환해서 저장한다. (사전에 스프링 컨테이너 빈으로 등록되어 있어야 한다.)
![advisor](https://user-images.githubusercontent.com/34621693/183363367-41682220-b384-4c59-b154-83ed7f032e5a.jpeg)
@Aspect를 어드바이저로 변환해서 저장하는 과정
1. 실행: 스프링 애플리케이션 로딩 시점에 자동 프록시 생성기를 호출한다. 
2. 모든 @Aspect 빈 조회: 자동 프록시 생성기는 스프링 컨테이너에서 @Aspect 애노테이션이 붙은 스프링 빈을 모두 조회한다. 3. 어드바이저 생성: @Aspect 어드바이저 빌더를 통해 @Aspect 애노테이션 정보를 기반으로 어드바이저를 생성한다. 
4. @Aspect 기반 어드바이저 저장: 생성한 어드바이저를 @Aspect 어드바이저 빌더 내부에 저장한다. 

@Aspect 어드바이저 빌더 BeanFactoryAspectJAdvisorsBuilder 클래스이다. 
@Aspect 의 정보를 기반으로 포인트컷, 어드바이스, 어드바이저를 생성하고 보관하는 것을 담당한다. 
@Aspect 의 정보를 기반으로 어드바이저를 만들고, @Aspect 어드바이저 빌더 내부 저장소에 캐시한다. 
캐시에 어드바이저가 이미 만들어져 있는 경우 캐시에 저장된 어드바이저를 반환한다. 


2. 어드바이저를 기반으로 프록시를 생성한다. 
![pointcut](https://user-images.githubusercontent.com/34621693/183363400-051e7117-e1fc-4c80-95d5-941e898dff62.jpeg)
자동 프록시 생성기의 작동 과정
1. 생성: 스프링 빈 대상이 되는 객체를 생성한다. ( @Bean , 컴포넌트 스캔 모두 포함) 2. 전달: 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다. 3-1. Advisor 빈 조회: 스프링 컨테이너에서 Advisor 빈을 모두 조회한다. 3-2. @Aspect Advisor 조회: @Aspect 어드바이저 빌더 내부에 저장된 Advisor 를 모두 조회한다. 
4. 프록시 적용 대상 체크: 앞서 3-1, 3-2에서 조회한 Advisor 에 포함되어 있는 포인트컷을 사용해서 해당 객체가 프록시를 적용할 대상인지 아닌지 판단한다. 
이때 객체의 클래스 정보는 물론이고, 해당 객체의 모든 메서드를 포인트컷에 하나하나 모두 매칭해본다. 그래서 조건이 하나라도 만족하면 프록시 적용 대상이 된다. 
예를 들어서 메서드 하나만 포인트컷 조건에 만족해도 프록시 적용 대상이 된다. 5. 프록시 생성: 프록시 적용 대상이면 프록시를 생성하고 프록시를 반환한다. 그래서 프록시를 스프링 빈으로 등록한다. 만약 프록시 적용 대상이 아니라면 원본 객체를 반환해서 원본 객체를 스프링 빈으로 등록한다. 6. 빈 등록: 반환된 객체는 스프링 빈으로 등록된다. 


### @Aspect 
![public class LogTraceAspect](https://user-images.githubusercontent.com/34621693/183363438-ada9ea1c-a4c0-4515-9133-6d4fd34e00fe.png)
1. @Aspect : 애노테이션 기반 프록시를 적용할 때 필요하다. 
2. @Around("execution(* hello.proxy.app..*(..))") 
    * @Around 의 값에 포인트컷 표현식을 넣는다. 표현식은 AspectJ 표현식을 사용한다. 
    * @Around 의 메서드는 어드바이스( Advice )가 된다. 
        * * : 모든 반환 타입
        * hello.proxy.app.. : 해당 패키지와 그 하위 패키지 
        * *(..) : * 모든 메서드 이름, (..) 파라미터는 상관 없음 
3. ProceedingJoinPoint joinPoint : 어드바이스에서 살펴본 MethodInvocation invocation 과 유사한 기능이다. 내부에 실제 호출 대상, 전달 인자, 그리고 어떤 객체와 어떤 메서드가 호출되었는지 정보가 포함되어 있다. 
4. joinPoint.proceed() : 실제 호출 대상( target )을 호출한다. 


### AOP 적용 방식(위빙)
컴파일 시점: 실제 대상 코드에 애스팩트를 통한 부가 기능 호출 코드가 포함된다. AspectJ를 직접 사용해야 한다. 클래스 로딩 시점: 실제 대상 코드에 애스팩트를 통한 부가 기능 호출 코드가 포함된다. AspectJ를 직접 사용해야 한다. 런타임 시점: 실제 대상 코드는 그대로 유지된다. 대신에 프록시를 통해 부가 기능이 적용된다. 따라서 항상 프록시를 통해야 부가 기능을 사용할 수 있다. 스프링 AOP는 이 방식을 사용한다. 
단점 : final class, 기본 생성자 필요..


### AOP 적용 위치
AOP는 지금까지 학습한 메서드 실행 위치 뿐만 아니라 다음과 같은 다양한 위치에 적용할 수 있다. 
적용 가능 지점(조인 포인트): 생성자, 필드 값 접근, static 메서드 접근, 메서드 실행 이렇게 AOP를 적용할 수 있는 지점을 조인 포인트(Join point)라 한다. AspectJ를 사용해서 컴파일 시점과 클래스 로딩 시점에 적용하는 AOP는 바이트코드를 실제 조작하기 때문에 해당 기능을 모든 지점에 다 적용할 수 있다. 프록시 방식을 사용하는 스프링 AOP는 메서드 실행 지점에만 AOP를 적용할 수 있다. 
프록시는 메서드 오버라이딩 개념으로 동작한다. 따라서 생성자나 static 메서드, 필드 값 접근에는 프록시 개념이 적용될 수 없다. 프록시를 사용하는 스프링 AOP의 조인 포인트는 메서드 실행으로 제한된다. 
프록시 방식을 사용하는 스프링 AOP는 스프링 컨테이너가 관리할 수 있는 스프링 빈에만 AOP를 적용할 수 있다. 


### AOP 용어
![orderService](https://user-images.githubusercontent.com/34621693/183363478-bc89aabb-03cb-40da-ade6-c33977953287.jpeg)
**조인 포인트(Join point)**
* 어드바이스가 적용될 수 있는 위치, 메소드 실행, 생성자 호출, 필드 값 접근, static 메서드 접근 같은 프로그램 실행 중 지점
* 조인 포인트는 추상적인 개념이다. AOP를 적용할 수 있는 모든 지점이라 생각하면 된다.
* 스프링 AOP는 프록시 방식을 사용하므로 조인 포인트는 항상 메소드 실행 지점으로 제한된다.

<br/>**포인트컷(Pointcut)**
* 조인 포인트 중에서 어드바이스가 적용될 위치를 선별하는 기능
* 주로 AspectJ 표현식을 사용해서 지정
* 프록시를 사용하는 스프링 AOP는 메서드 실행 지점만 포인트컷으로 선별 가능

<br/>**타켓(Target)**
* 어드바이스를 받는 객체(핵심 기능이 있는 메서드), 포인트컷으로 결정

<br/>**어드바이스(Advice)** 
* 부가 기능 
* 특정 조인 포인트에서 Aspect에 의해 취해지는 조치 
* Around(주변), Before(전), After(후)와 같은 다양한 종류의 어드바이스가 있음 애스펙트(Aspect) 
* 어드바이스 + 포인트컷을 모듈화 한 것 @Aspect 를 생각하면 됨 
* 여러 어드바이스와 포인트 컷이 함께 존재 
<br/>**어드바이저(Advisor)**
* 하나의 어드바이스와 하나의 포인트 컷으로 구성 
* 스프링 AOP에서만 사용되는 특별한 용어

<br/>**위빙(Weaving)**
* 포인트컷으로 결정한 타켓의 조인 포인트에 어드바이스를 적용하는 것 
* 위빙을 통해 핵심 기능 코드에 영향을 주지 않고 부가 기능을 추가 할 수 있음 
* AOP 적용을 위해 애스펙트를 객체에 연결한 상태 
    * 컴파일 타임(AspectJ compiler)
    * 로드 타임
    * 런타임, 스프링 AOP는 런타임, 프록시 방식

<br/>**AOP 프록시**
* AOP 기능을 구현하기 위해 만든 프록시 객체, 스프링에서 AOP 프록시는 JDK 동적 프록시 또는 CGLIB 프록시이다. 
