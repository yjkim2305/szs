# 세금 계산 자동화 API

---
## 프로젝트 개요
사용자의 소득 정보를 바탕으로 **세금 계산을 자동화하는 API 서비스** 입니다.\
사용자는 회원가입 및 로그인 후 세금 관련 정보를 스크래핑하여\
**결정세액을 자동으로 계산**할 수 있습니다.

---
## 기술 스택
* Java 17
* Spring Boot 3.4.2
* JPA
* H2 Embedded In-memory
* Gradle
* Swagger
* JWT (Json Web Token)
* OpenFeign
* Junit5
* Mockito

---
## 기능 목록
1. 사용자 관리
   * **회원가입**
     * 허용된 사용자만 회원가입이 가능
     * 중복된 사용자 아이디는 가입할 수 없음
   * **로그인**
     * 사용자는 아이디와 비밀번호로 로그인 가능
     * 로그인 성공 시 JWT(Access Token 과 Refresh Token) 발급
     * JWT를 이용한 인증 및 권한 검증
   * **Access Token 갱신**
     * 쿠키에 저장된 Refresh Token으로 Access Token 갱신
2. 세금 관리
   * **소득 정보 스크래핑**
     * 사용자의 소득 정보를 외부 API에서 스크래핑하여 저장
   * **결정세액 조회**
     * 사용자의 소득 정보를 기반으로 결정세액 자동 계산
     * 계산된 세금 정보를 사용자에게 제공

---
## 프로젝트 구조 및 아키텍처
이 프로젝트는 **도메인형 레이어드 아키텍처**를 기반으로 설계되었습니다.

### 도메인형 레이어드 아키텍처
도메인형 레이어드 아키텍처는 각 기능별(도메일별)로 계층을 분리하여 설계하는 방식 입니다. \
각 도메인은 독립적인 비즈니스 로직을 가지며, 특정 도메인에 대한 변경이 다른 도메인에 영향을 최소화하도록 설계 됩니다.

### 프로젝트 구조
```
szsyoungjunkim
├─ common
│  ├─ api
│  │  └─ response
│  ├─ config
│  ├─ converter
│  ├─ domain
│  │  └─ entity
│  ├─ exception
│  └─ util
├─ deduction
│  ├─ api
│  │  └─ exception
│  ├─ application
│  │  ├─ dto
│  │  ├─ repository
│  │  └─ service
│  ├─ domain
│  │  └─ enums
│  ├─ facade
│  ├─ feign
│  │  ├─ request
│  │  └─ response
│  └─ infrastructure
│     ├─ entity
│     └─ repository
├─ refresh
│  ├─ api
│  │  ├─ exception
│  │  └─ response
│  ├─ application
│  │  ├─ dto
│  │  ├─ repository
│  │  └─ service
│  ├─ domain
│  └─ infrastructure
│     ├─ entity
│     └─ repository
├─ refund
│  ├─ api
│  │  ├─ exception
│  │  └─ response
│  ├─ application
│  │  ├─ dto
│  │  ├─ repository
│  │  └─ service
│  ├─ domain
│  │  └─ enums
│  ├─ facade
│  │  └─ dto
│  └─ infrastructure
│     ├─ entity
│     └─ repository
└─ user
   ├─ api
   │  ├─ exception
   │  ├─ request
   │  └─ response
   ├─ application
   │  ├─ dto
   │  ├─ repository
   │  └─ service
   ├─ domain 
   ├─ facade 
   └─ infrastructure
      ├─ entity
      └─ repository

```
### 아키텍처 설명
#### **1. API Layer (컨트롤러 계층)**
- HTTP 요청을 받아 적절한 서비스를 호출하는 역할.
- RESTful API를 제공.

#### **2. Application Layer (비즈니스 로직 계층)**
- 도메인 로직을 처리하고, 여러 도메인 간의 상호작용을 조율.
- 핵심적인 비즈니스 로직을 구현.

#### **3. Domain Layer (도메인 모델 계층)**
- 도메인 비즈니스 로직 정의
- 주요 도메인 및 Enum을 포함.

#### **4️. Infrastructure Layer (데이터 저장소 및 외부 연동)**
- 데이터베이스 및 외부 API 연동을 담당.

#### **5️. Facade Layer (통합 서비스)**
- 여러 서비스 로직을 조합하여 하나의 API로 제공하는 역할.

---
## ERD
`deducts` : 소득공제(국민연금,신용카드) 년/월별 내역 테이블    
`refund` : 결정세액 계산에 필요한 테이블(종합소득금액, 세액공제)   
`refresh` : 사용자에 대한 refresh token 테이블      
`users` : 사용자 테이블  

![Image](https://github.com/user-attachments/assets/2e4d91dd-7f52-444f-bb90-2c6d92daf001)
---
## API 명세
API 문서는 Swagger에서 확인할 수 있습니다.
* http://localhost:8080/3o3/swagger-ui/index.html#/