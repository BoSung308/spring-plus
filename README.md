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

### 5. JPA의 이해 - JPA 검색 추가(JPQL 활용)
- @Query 애너테이션을 사용하여 동적으로 쿼리로 조건에 맞도록 작성하고, Todo 객체들을 조회한다. Pageable 파라미터를 통해 페이징 처리를 하며, Page로 결과를 반환하도록 사용

### 6. JPA Cascade 
- CascadeType.Persist를 사용하여, 부모 엔티티를 저장할 때, 자식 엔티티도 함게 저장하도록 사용

### 7. N+1 문제 해결
- @Query("SELECT c FROM Comment c JOIN c.user WHERE c.todo.id = :todoId")
-> @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.todo.id = :todoId") JOIN FETCH를 사용해줘서 지연 로딩을 회피하고 Comment 엔티티와 연관된 엔티티를 한번에 가져오게 설정. 이렇게 FETCH JOIN을 사용함으로써, DB 쿼리가 한번만 실행하고 N+1을 해결.

  
--------
