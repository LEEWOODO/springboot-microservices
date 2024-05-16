``` springboot3 zipkin + sleuth 설치
1. pom.xml에 추가
     	<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-observation</artifactId>
		</dependency>
		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-tracing-bridge-brave</artifactId>
		</dependency>
		<dependency>
			<groupId>io.zipkin.reporter2</groupId>
			<artifactId>zipkin-reporter-brave</artifactId>
		</dependency>
		<dependency>
			<groupId>io.github.openfeign</groupId>
			<artifactId>feign-micrometer</artifactId>
		</dependency>
		
2. application.properties 적용 (모든 Service)
        # zipkin + sleuth logging
        management.tracing.sampling.probability=1.0 
        logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]
        logging.level.org.springframework.web=DEBUG
    
3. zipkin 설치 : docker run --rm -it --name zipkin -p 9411:9411 openzipkin/zipkin
4. zipkin 접속 : http://localhost:9411/zipkin/ or http://127.0.0.1:9411/zipkin/
```
