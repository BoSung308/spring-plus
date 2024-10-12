# SPRING PLUS
----------

### 1. @Transactiona의 이해 - 정상적인 readOnly 사용
- Transactional(readOnly = true)가 클래스에 쓰여지는 것이 아닌, 메서드에 Transactional(readOnly = true)을 사용함으로써, 데이터 수정이 정상적으로 이루어지도록 수정

### 2. JWT의 이해 - 화면에서 nickName이 보여지도록 수정
- User 엔티티에 nickName 컬럼을 추가해주고 프론트엔드에서도 유저의 닉네임이 보여지도록 SignupRequest, AuthService, AuthUser, JwtUtil, AuthUserArgumentResolver 클래스에서도 수정

### 3. AOP의 이해 - AOP 코드 개선
- @After에서 @Before로 수정하며, 대상 메서드가 실행되기 전에 AOP 로깅이 정상적으로 실행되도록 수정

### 4. 테스트 코드 - Controller Test
- 예외가 발생할 때, 400 BAD_REQUEST가 발생해야했지만 200 code가 발생되어 오류 나던 현상을 수정 
--------
