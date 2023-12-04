<div align="center">
    
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fcca-ffodregamdi%2Frunning-hi-back&count_bg=%23FFA49F&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=views&edge_flat=false)](https://hits.seeyoufarm.com)
## 🏃 RUNNING HI : 러닝하이

### ▼ 사이트 이동 ▼
<a href="https://running-hi.com">
<img width="250" alt="image" src="https://github.com/cca-ffodregamdi/.github/assets/119282494/ff52abf3-6873-4f68-8d35-c2ed0e5102e4">
</a>
</div>

<br>

## 🧱 프로젝트 아키텍처
![image](https://github.com/cca-ffodregamdi/running-hi-back/assets/98208452/085bb740-bcbf-4054-9043-3ed0b1c106a0)

<br>

## ⚒️ 기술 스택
### Tool
![IntelliJ](https://img.shields.io/badge/InteliiJ-000000?style=flat&logo=intellijidea&logoColor=white)

### Language
![Java](https://img.shields.io/badge/Java-007396style=flat&logo=java&logoColor=white)

### Library & Framework
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/SpringDataJPA-6DB33F?style=flat&logo=springdatajpa&logoColor=white)
<br>
![GitHub Actions](https://img.shields.io/badge/GitHubActions-2088FF?style=flat&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat&logo=nginx&logoColor=white)

### Database
![MYSQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat&logo=firebase&logoColor=white)

### AWS
![AWS EC2](https://img.shields.io/badge/AWS-EC2-FF9900?style=flat&logo=amazonec2&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS-RDS-527FFF?style=flat&logo=amazonrds&logoColor=white)
![AWS Route 53](https://img.shields.io/badge/AWS-Route53-8C4FFF?style=flat&logo=amazonroute53&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS-S3-569A31?style=flat&logo=amazons3&logoColor=white)

<br>

## 📌 코드 컨벤션
#### 🎉 코드 컨벤션의 중요성
코드 컨벤션을 사전에 명확히 정의함으로써, 우리는 서로의 코드를 더욱 쉽게 이해할 수 있으며, 오해가 줄어들 것이라 생각합니다. 

물론, 각자의 고유한 코드 스타일에서의 장점이 있을 수 있지만, 그것이 다른 팀원들이 이해를 하지 못한다면 그 코드는 장점으로 보이지 않을 것입니다. 

우리가 공통된 코드 컨벤션을 정의함으로써, 개별 스타일의 장점을 유지하면서도 모두가 쉽게 이해할 수 있는 코드를 작성할 수 있을 것입니다. 이러한 노력은 결국 프로젝트의 효율성과 품질을 높여주며, 우수한 결과물을 창출할 것으로 기대합니다!

#### 🎉 클래스 명칭
```
🐤 [ 도메인 이름 ] + [ Command / Query ] + [ Domain / Infra ] + [ Controller / Service / Repository]
```

<br>

#### 🎉 메소드 명칭
```
C : create + [ 명사 ]

R : find + [ 명사 ]

U : update + [ 명사 ]

D : delete + [ 명사 ]
```

메소드 명은 🐫(Camel Case)로 표기할 것!

<br>

#### 🎉 API 응답

![image](https://github.com/cca-ffodregamdi/running-hi-back/assets/115992753/4dd76c8d-dcc3-486d-830c-cda93a5ecb39)

> 출처 : https://wildeveloperetrain.tistory.com/m/240


응답의 형태는 Common 패키지에서 공용으로 사용되며 공통 양식을 유지할 것!

<br>

#### 🎉 예외 처리


Common 패키지에서 Exception Response Handler 클래스를 생성하여 전체 도메인에서 발생하는 예외를 공용으로 관리할 것!

**Create**

- Not Found
- illegal Argument

**Read**

- Not Found
- Not Match Writer : 작성자가 본인이 아닐 때
- Unauthorized Access : 본인이거나 관리자가 아닐 때, 즉 권한이 없을 때

**Update**

- Not Found
- illegal Argument
- Not Match Writer : 작성자가 본인이 아닐 때
- Unauthorized Access : 본인이거나 관리자가 아닐 때, 즉 권한이 없을 때

**Delete**

- Not Found
- illegal Argument
- Not Match Writer : 작성자가 본인이 아닐 때
- UnauthorizedAccess : 본인이거나 관리자가 아닐 때, 즉 권한이 없을 때

<br>

#### 🎉 DTO


DTO(Data Transfer Object)를 request와 response로 나누어 제작할 것!

<br>

#### 🎉 Test Code

DisplayName 명칭 통일

```
🐏 [ 도메인 이름 ] + [ 생성 / 조회 / 수정 / 삭제 ] + 테스트 : {테스트 내용}(성공 확인 시 ‘success’ 기입)
ex) `키워드 생성 테스트 : success`
```

<br>

