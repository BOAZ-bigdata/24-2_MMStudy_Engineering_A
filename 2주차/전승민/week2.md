# 2주차 정리
- 목차
1. 사용자 수에 따른 규모 확장성
2. 개략적인 규모 추정
3. 시스템 설계 면접 공략법

# 사용자 수에 따른 규모 확장성
- 간단한 시스템 구성 (단일 서버)
  - 웹,앱,DB,캐시 등이 모두 하나의 서버에서 실행됨
  <img width="400" alt="그림1-1" src="https://github.com/user-attachments/assets/4cf9d293-df0b-4b05-8dea-ee7d325aa6f3">

  - 도메인 이름을 가진 사용자는 DNS를 통해 IP 주소를 반환 받게됨
  - IP 주소를 통해 HTTP 요청이 전달 되고, 웹 서버는 HTML이나 JSON 형태의 응답을 반환함
 
## 데이터베이스
- 역할에 따라 여러 서버가 필요
  - 트래픽 처리 서버 & 데이터베이스 서버 (책에서는 독립성을 강조함)
<img width="400" alt="스크린샷 2024-07-22 오후 6 27 55" src="https://github.com/user-attachments/assets/8ecdcfa9-dbb1-4684-961f-518b997d0b7b">

- 관계형 DB (RDBMS, SQL) & 비 관계형 DB (NoSQL)
  - 관계형 DB :
      - 테이블,열,행 사용한것
      - MySQL, PostgreSQL ...
  - 비 관계형 DB :
      - key-value store, graph store, column store, document store
      -  Redis(key-value), MongoDB(document), Cassandra(column) ...
1. 아주 낮은 응답 지연시간이 요구됨 : NoSQL은 고성능과 낮은 지연시간을 제공하도록 설계됨
2. 비정형 관계
3. 데이터를 직렬화하거나 역직렬화 : 데이터를 연속적인 바이트 단위로 변환 후 저장하거나 전송하는 과정을 지원해줌
4. 아주 많은 양의 데이터를 저장할때 : 수평적 확장이 가능하기 때문

## 수직적 규모 확장(scale up) vs 수평적 규모 확장(scale out)
- scale up : 컴퓨터 자원을 추가하는 거 (cpu,ram 추가...)
  - 확장에 물리적 한계가 있음
  - 장애에 대한 자동복구, 다중화 방안을 제시하지 못함
- scale out : 여러 서버를 추가하는 거
  - 대규모 애플리케이션에 더 적합
## Load Balancer
<img width="400" alt="스크린샷 2024-07-22 오후 7 08 00" src="https://github.com/user-attachments/assets/83cf1e3e-5e78-4106-8699-5ab745a7136e">

- 웹 서버의 트래픽 부하를 고르게 분산하는 역할을 함
- 사용자는 로드밸런서에의 공개IP 주소로 접속을 함 (직접 웹 서버에 접속X)
- 로드밸런서는 웹 서버와 사설IP(같은 네트워크에 속한 서버 사이에 쓰이는 거)를 통해 접속함

## 데이터베이스 다중화
<img width="400" alt="스크린샷 2024-07-22 오후 7 16 28" src="https://github.com/user-attachments/assets/0e0bf37d-2565-40d9-b5bf-47c3fdf98e8d">

- 데이터 원본은 주서버, 사본은 부 서버에 저장
- 쓰기 연산은 마스터에서만 지원
- 부 데이터베이스는 읽기 연산만 가능
- 쓰기와 읽기를 분리하고, 읽기 데이터베이스 서버를 분산하면 쿼리의 수가 늘어나 성능이 좋아짐
- 물리적으로 다중화를 했기에 안정성과 가용성을 가짐
<img width="500" alt="스크린샷 2024-07-22 오후 7 36 26" src="https://github.com/user-attachments/assets/ec4866f7-f115-4d53-8dfc-07401e77297a">

# 개략적인 규모 추정

# 시스템 설계 면접 공략법

