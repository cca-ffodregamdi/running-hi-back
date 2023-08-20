# 🏃RUNNING HI 러닝하이 : Backend



<br>

## 👋 2. 팀원 소개
<table>
  <tr>
    <td align="center"><a href="https://github.com/Subak-Uncle"><img src="https://avatars.githubusercontent.com/Subak-Uncle" width="150px;" alt="">
    <td align="center"><a href="https://github.com/Dylan-SonJungin"><img src="https://avatars.githubusercontent.com/Dylan-SonJungin" width="150px;" alt="">
    <td align="center"><a href="https://github.com/raxchaz"><img src="https://avatars.githubusercontent.com/raxchaz" width="150px;" alt="">
    <td align="center"><a href="https://github.com/numerical43"><img src="https://avatars.githubusercontent.com/numerical43" width="150px;" alt="">
    <td align="center"><a href="https://github.com/hodin030"><img src="https://avatars.githubusercontent.com/hodin030" width="150px;" alt="">
    <td align="center"><a href="https://github.com/fakerdeft"><img src="https://avatars.githubusercontent.com/fakerdeft" width="150px;" alt="">

  </tr>
  <tr>
    <td align="center"><a href="https://github.com/Subak-Uncle"><b>김종완</b></td>
    <td align="center"><a href="https://github.com/Dylan-SonJungin"><b>손정인</b></td>
    <td align="center"><a href="https://github.com/raxchaz"><b>라현지</b></td>
    <td align="center"><a href="https://github.com/numerical43"><b>강수의</b></td>
    <td align="center"><a href="https://github.com/hodin030e"><b>이효진</b></td>
    <td align="center"><a href="https://github.com/fakerdeft"><b>조만제</b></td>
  </tr>

  <tr>
    <td align="center">Kim Jong Wan</td>
    <td align="center">Son Jung In</td>
    <td align="center">Ra Hyeon Ji</td>
    <td align="center">Kang Su Ui</td>
    <td align="center">Lee Hyo Jin</td>
    <td align="center">Cho Man Je</td>
  </tr>
    <tr>
    <td align="center"><strong>통계, 관리자 게시글, 키워드</strong></td>
    <td align="center"><strong>댓글, 즐겨찾기</strong></td>
    <td align="center"><strong>프론트엔드</strong></td>
    <td align="center"><strong>회원 게시글, 문의사항</strong></td>
    <td align="center"><strong>신고</strong></td>
    <td align="center"><strong>회원가입/로그인, 회원 관련</strong></td>
  </tr>
</table>

<br>

## ⚒️ 3. 기술 스택
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Mysql-4479A1?style=flat-square&logo=Mysql&logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=Springboot&logoColor=white">

<br>

## ✨ 4. 협업 도구
<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=Swagger&logoColor=black"/> <img src="https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=Figma&logoColor=white"/> <img src="https://img.shields.io/badge/Miro-F7DF1E?style=flat-square&logo=Miro&logoColor=black"/>

<br>

## 🧱 5. 프로젝트 아키텍처
<!-- 모놀리식 아키텍처 -->
![마이크로서비스_모놀리식서비스_다이어그램](https://github.com/cca-ffodregamdi/running-hi-back/assets/98208452/20724af9-66cb-42bf-9550-f29235839dcc)
> 출처: https://library.gabia.com/contents/infrahosting/9154/



<br>

<!--
## 🗒️ 6. 요구 사항
- 기능별로 각각의 상황을 테스트하는 코드를 구현한다.
- 새로운 기능, 버그 fix 등의 코드 수정이 있다면 이슈를 작성한다.

<br>
-->

## 📌 6. 컨벤션

<br>

### 6-1 커뮤니케이션 컨벤션

- 하루에 한 번, **개발 타임라인**에 당일 처리한 일(한 일)과 다음 일정을 업데이트 해주세요.
- 모여서 회의가 불가능할 경우 **Discord**를 통해 회의합니다.
- 회의록, 논의 사항은 **GIt DIscussion** 과 **Notion에** 정리합니다.
- 협업 툴 : **Github**, **Notion**, **Discord**

<br>

### 6-2. 코드 컨벤션

#### 🎉 코드 컨벤션의 중요성

코드 컨벤션을 사전에 명확히 정의함으로써, 우리는 서로의 코드를 더욱 쉽게 이해할 수 있으며, 오해가 줄어들 것이라 생각합니다. 

물론, 각자의 고유한 코드 스타일에서의 장점이 있을 수 있지만, 그것이 다른 팀원들이 이해를 하지 못한다면 그 코드는 장점으로 보이지 않을 것입니다. 

우리가 공통된 코드 컨벤션을 정의함으로써, 개별 스타일의 장점을 유지하면서도 모두가 쉽게 이해할 수 있는 코드를 작성할 수 있을 것입니다. 이러한 노력은 결국 프로젝트의 효율성과 품질을 높여주며, 우수한 결과물을 창출할 것으로 기대합니다!


#### 🎉 클래스 명칭


```
🐤 [ 도메인 이름 ] + [ Command / Query ] + [ Domain / Infra ] + [ Controller / Service / Repository]
```

#### 🎉 메소드 명칭

```
C : create + [ 명사 ]

R : find + [ 명사 ]

U : update + [ 명사 ]

D : delete + [ 명사 ]
```

메소드 명은 🐫(Camel Case)로 표기할 것!

#### 🎉 API 응답

![image](https://github.com/cca-ffodregamdi/running-hi-back/assets/115992753/4dd76c8d-dcc3-486d-830c-cda93a5ecb39)

> 출처 : https://wildeveloperetrain.tistory.com/m/240


응답의 형태는 Common 패키지에서 공용으로 사용되며 공통 양식을 유지할 것!


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

#### 🎉 DTO


DTO(Data Transfer Object)를 request와 response로 나누어 제작할 것!


#### 🎉 Test Code

DisplayName 명칭 통일

```
🐏 [ 도메인 이름 ] + [ 생성 / 조회 / 수정 / 삭제 ] + 테스트 : {테스트 내용}(성공 확인 시 ‘success’ 기입)
ex) `키워드 생성 테스트 : success`
```

<br>

## 3. GitHub : PR & Commit  컨벤션

#### ✅ **Git Convention**
| **Convention**  | **내용**                                                         |
|-----------------|----------------------------------------------------------------|
| **Feat**        | 새로운 기능 추가                                                      |
| **BugFix**         | 버그 수정                                                          |
| **Test**        | 테스트 코드, 리펙토링 테스트 코드 추가, Production Code(실제로 사용하는 코드) 변경 없음     |
| **Comment**     | 필요한 주석 추가 및 변경                                                 |
| **Rename**      | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우                                   |
| **Remove**      | 파일을 삭제하는 작업만 수행한 경우                                            |
| **Design**      | CSS 등 사용자 UI 디자인 변경                                            |
| **Refactor** | 프로덕션 코드 리팩토링                                                   |
| **API** | 서버 API 통신                                                   |
| **Deploy** | 배포 관련                                                   |
| **Setting** | 개발환경 세팅                                                   |
| **Request** | 기능 요청                                                   |

---------------------------------------------------

#### ✅ **PR 제목**

**Back End**

```
🐘 [Label 이름(첫 글자 대문자)] - {작업 컨텍스트(대문자)} pr 내용
ex) [Feature] - {USER} 엔티티 설계
```

**Front End**

```
🦒 [Label 이름(첫 글자 대문자)] - {작업한 페이지} pr 내용
ex) [Feature] - {ADMIN} 컴포넌트 제작
```

#### ✅ **PR & Commit 규칙**

- main branch에 바로 push 금지! develop branch로 Pull requests 하기.
- git convention을 지키기.
- PR 전에 이슈 발행 필수, PR 할 때 이슈 번호 입력 필수!
- 이슈 하나는 본인이 하루 내에 해결할 수 있는 양으로 선정하기.
- PR에 적극적으로 코드 리뷰 남기기 (LGTM 금지🙅).
- 두 명 이상의 PR 승인이 이루어져야 Merge 가능.
- Action이 통과해야만 Merge 가능.
- Action 실패 시 원인 파악 및 테스트 성공 시까지 수정.
