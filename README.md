![header](https://capsule-render.vercel.app/api?type=wave&color=auto&height=300&section=header&text=어디갈래?&fontSize=90)
# 🎒국내여행정보 및 여행일정 관리서비스 '어디갈래'
### 멋쟁이 사자처럼 백엔드스쿨 5기 9팀 최종 프로젝트

## 1. 프로젝트 소개

### 구해줘 팀 구성원
````
🙌🏻 박채원, 양재원, 신예지, 성광현, 김가윤
````
### 프로젝트 개요

- ‘어디갈래’는 국내 여행에 관심이 있는 사용자들이 한국관광공사가 제공하는 국내 지역별 여행지를 확인하고 공유할 수 있는 서비스입니다.
- 가볍게 국내 여행을 떠나고 싶을 때, 지역별 대한 다른 유저들의 관심도와 후기를 참고해서 여행지를 선정하기 쉽도록 돕습니다.
- 여행 지역과 일정을 결정한 후 친구와 함께 실시간 채팅을 통해 동선을 함께 확인하고 정할 수 있도록 해서, 실제로 만나지 않고도 여행 계획을 공유할 수 있도록 합니다.

### 주요기능 소개

- 사용자는 로그인하지 않고도 각 여행정보를 확인할 수 있습니다.
- 시군구별, 관광지 유형별 여행정보를 조회할 수 있습니다.
- 여행지 이름을 클릭하면 상세페이지에서 여행지 정보를 확인하고 후기를 남길 수 있습니다.
- 관심있는 여행지를 등록해두고, 일정에 추가할 수 있습니다.
- 일정짜기 메뉴에서는 여행 날짜와 지역을 골라 일정을 생성할 수 있습니다.
- 생성된 일정의 세부페이지에서 일자별로 여행지를 등록하고 방문순서를 결정할 수 있고,
  그 과정을 메이트로 초대된 친구와 채팅방에서 대화하며 진행할 수 있습니다.
- 커뮤니티에서는 사용자들이 여행 후기를 공유할 수 있고, 공개된 일정을 확인할 수도 있습니다.

## 2. 프로젝트 개요

### 개발기간
* **전체 개발기간**: 23.08.09~23.09.13 (미완)
- **기획** : 23.08.09~23.08.14
- **백엔드 개발** : 23.08.15~23.09
- **프론트엔드 개발**: 23.09.04~23.09.12
- **기능 테스트 및 리팩토링**: 23.09.10~23.09.13
- **기능 개발 미완**

### 프로젝트 구조도
<p allign="center">
<img src="README/어디갈래%20-%20프로젝트%20구조도.png">

### ERD
<p allign="center">
<img src="README/어디갈래%20-%20erd.png">
</p>

## 3. 개발환경 및 사용기술
 **Back-end**<br>
  - Language : <img src="https://img.shields.io/badge/java-000000?style=flat-square&logo=jameson&logoColor=white"/></a>
  - Framework & Build Tool : <img src="https://img.shields.io/badge/SpringBoot-000000?style=flat-square&logo=springboot&logoColor=#6DB33F"/></a>
   , <img src="https://img.shields.io/badge/gradle-000000?style=flat-square&logo=gradle&logoColor=#02303A"/></a>
  - IDE : <img src="https://img.shields.io/badge/IntelliJidea-000000?style=flat-square&logo=intellijidea&logoColor=white"/></a>
  - DataBase : <img src="https://img.shields.io/badge/MySQL-000000?style=flat-square&logo=mysql&logoColor=#4479A1"/></a>
  - Authentication Framework (로그인, 회원가입, jwt 발급) : <img src="https://img.shields.io/badge/Spring Security-000000?style=flat-square&logo=springsecurity&logoColor=#6DB33F"/></a>
  - DataServer : <img src="https://img.shields.io/badge/Redis-000000?style=flat-square&logo=redis&logoColor=#DC382D"/></a>
  - Messaging Protocol : <img src="https://img.shields.io/badge/Stomp-000000?style=flat-square&logo=&logoColor=white"/></a>

 **Front-End**<br>
  - <img src="https://img.shields.io/badge/HTML-000000?style=flat-square&logo=html5&logoColor=#E34F26"/></a>, 
    <img src="https://img.shields.io/badge/IntelliJidea-000000?style=flat-square&logo=javascript&logoColor=#F7DF1E"/></a>,
    <img src="https://img.shields.io/badge/Vue3-000000?style=flat-square&logo=vuedotjs&logoColor=#4FC08D"/></a>,
    <img src="https://img.shields.io/badge/Vuetify3-000000?style=flat-square&logo=vuetify&logoColor=#1867C0"/></a>,
    <img src="https://img.shields.io/badge/NaverMapAPI-000000?style=flat-square&logo=naver&logoColor=#03C75A"/></a>
 **API**<br>
  - 공공데이터 - 한국관광공사_국문 관광정보 서비스_GW
  - 네이버지도 API - Web Dynamic Map, Static Map

 **협업 Tools**<br>
  - <img src="https://img.shields.io/badge/discord-5865F2?style=flat-square&logo=discord&logoColor=white"/></a>
   <img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=#000000"/></a>
   <img src="https://img.shields.io/badge/GitHub-000000?style=flat-square&logo=github&logoColor=#181717"/></a>

## 4. 개발기간 및 진행

<aside>
⭐ **전체 개발 기간 23.08.09~23.09.13 (미완)**

- **기획** : 23.08.09~23.08.14
    - 주제선정, DB 설계, Task 분담, 컨벤션 협의 및 API 작성
    - Task별 일정 작성
- **백엔드 개발** : 23.08.15~23.09.05
    - Task별 주요 기능 개발 23.08.15~23.09.05
    - 상세 기능 구현 및 리팩토링 23.09.06~23.09.12
- **프론트엔드 개발**: 23.09.04~23.09.12
- **기능 테스트 및 배포** : 23.09.10~23.09.13
</aside>

## 5. 세부 기능
### **회원가입 및 회원정보 관리, 로그인 및 인증**

- 회원가입
    - 가입시 email과 닉네임 중복확인, 비밀번호 제약사항 검증, 회원 프로필 생성
- 로그인 및 인증
    - 회원 정보와 입력내역 확인 후 jwt(access token) 발급
    - [미구현] refresh token, 인증만료 토큰
- 회원정보 관리
    - 회원정보와 프로필 조회 및 수정, 비밀번호 수정, 탈퇴기능
    - 회원탈퇴 - 탈퇴시 soft delete로 구현.
        - 프로필 이미지 삭제, 프로필 엔티티 isDeleted true
        - mate와 즐겨찾기에서 isDeleted true 표시하고, 조회되지 않도록 함
- 프론트
    - 회원가입 폼 및 검증로직, 회원정보 수정, 회원즐겨찾기 기타 회원 관련기능 보완
    - [미구현] 회원탈퇴 및 비밀번호 수정시 비밀번호 인증폼 거치기
    - [미구현] 회원 닉네임 클릭시 회원정보 조회
    - [미구현] 회원 검색이 즐겨찾기 여부 표시

### **회원 검색 및 즐겨찾기**

- 회원 검색
    - 검색어를 포함하는 닉네임, email을 가진 유저 전부 조회하여 List로 반환
    - [미구현] 회원검색시 로그인한 회원을 즐겨찾기에 추가했는지 여부 표시
- 회원 즐겨찾기 추가
    - 검색 목록에서 회원 즐겨찾기 추가 가능
    - 즐겨찾기한 회원 조회, 나를 추가한 회원 조회
- [프론트] 마이페이지에서 즐겨찾기 및 검색 기능 이용 가능

### **여행정보 조회**

- 여행지 조회
    - 여행지 리스트 조회 - 시/도 + 시/군/구 + 컨텐츠 타입 별로 조회, 임의의 여행지 클릭 시 상세정보페이지로 이동
    - 여행지 상세정보 조회
- 여행지 관심등록(즐겨찾기) 기능
- 여행지 상세정보 페이지
    - 여행지 지도와 설명 등의 정보 조회
    - 후기 작성, 수정, 삭제
- 여행 지도 기능
    - 여행지 리스트 조회 - 시/도, 시/군/구 별로 조회
    - 조회된 여행지들 지도에 마커표시 및 정보창 기능
    - 여행지 리스트에서 임의의 여행지 이미지 클릭 시 해당 마커 정보창 오픈
    - 여행지 리스트에서 임의의 여행지의 이름 클릭 시 해당 여행지 상세정보 페이지로 이동
- 마이페이지 - 내가 쓴 후기, 내가 관심등록한 여행지 모아보기

### **여행메이트 기능**

- 초대기능
    - 메이트에 초대할 유저 검색
    - 일정에 메이트 초대 기능
    - 초대 받은 리스트 조회
    - 초대 항목 수락/거절
    - [미구현] 초대중인 리스트 조회
- 실시간 채팅 기능
    - 일정메이트와 동일한 채팅방 접근
    - 채팅방에 일정 제목과 멤버 수 조회
    - 채팅방에 일자가 바뀔 시 년,월,일 날짜 출력
    - 채팅메시지 아래 시간 표기
    - 전송버튼과 엔터키로 메시지 전송
- [프론트 미구현]
    - 일정 탙퇴
    - 채팅방명 변경

### **일정 생성 및 동선 확인 기능**

- 일정만들기
    - 일정 제목과 설명, 여행날짜(기간 5일이내) 선택 후 일정 생성
    - 해당 일정의 상세일정 페이지에서 일자별 여행지 선택, 경로 생성
- [프론트]
    - 마이페이지에서 내 일정 조회 가능
    - 일정 목록에서 선택 후 수정 버튼을 누르고, 다시 일정 생성버튼을 눌러 세부 일정으로 진입
    - 세부일정페이지에서 관심등록한 여행지를 일정에 추가
        - 지도에 마커 표시, 하단 일자별 목록에 방문순서대로 표시
    - 드래그앤드롭으로 순서와 일자를 옮길 수 있음

### **커뮤니티 - 일정 공유 및 게시글 작성 기능**

- 게시글 작성
    - 글꼴 강조, 기울이기, 밑줄, 중간 줄 기능
    - 이미지 복사 붙여넣기 또는 파일 드래그앤 드랍으로 이미지 삽입
- 게시글 목록
    - 페이지 단위 조회 기능
- 게시글 상세 조회
    - 댓글 페이지 단위 조회 기능
    - 댓글/대댓글 기능
- 게시글 공개 기능
![Footer](https://capsule-render.vercel.app/api?type=waving&color=auto&height=300&section=footer)
