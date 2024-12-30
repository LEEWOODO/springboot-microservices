# springboot-microservices

## mysql docker 설치
- docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:latest
- 계정 root / root
## rabbitMQ docker 설치
- docker run -d -p 15672:15672 -p 5672:5672 --name rabbitmq rabbitmq
- 플러그인 설치 docker exec rabbitmq rabbitmq-plugins enable rabbitmq_management
- 계정 guest / guest
## zipkin docker 설치
- docker run -d -p 9411:9411 openzipkin/zipkin
## 서비스 실행순서
1. ServiceRegistryApplication
2. ApiGatewayApplication