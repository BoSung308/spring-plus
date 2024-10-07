package org.example.expert.domain.comment.repository;

import org.example.expert.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /*
    ----------------------------------------------------------------------------------------------------------------

     N+1 해결 방법을 알아보던 도중 @EntityGraph를 사용해서 해결하는 방법이 있던데 궁금한 점이 있어 주석으로 남깁니다.

     1. 제가 사용한 해결 방법이 맞는지 궁금합니다.

     2. EntityGraph를 사용하면, 로딩 전략을 엔티티 레벨에서 쉽게 적용이 가능하고, 여러 리포지토리 메서드에서 재사용성이 용이하다고 합니다.
        뿐만 아니라, 복잡한 로직 단순화, EntityGraph를 적절히 사용한다면 성능이 최적화 등 이점이 많다고 느껴집니다.
        그에 비해 FETCH JOIN은 하나의 쿼리에서만 사용이 가능해 재사용성이 저하, 연관 Entity가 많을수록 쿼리가 복잡해지는 것 같습니다.
        그렇다면 EntityGraph는 쿼리가 복잡해질 것 같을 때, 즉 쿼리를 단순하게 유지하고 싶을 때 사용을 해야하는 것일까요?

     3. 2번에서 말한 내용이 맞다면, FETCH JOIN은 어떠한 특정 조건? 혹은 상황에서 써야할 것 같다고 판단됩니다. 여기서 말하는 상황이
        어떤 상황인지 감이 잡히지가 않는 것 같아, 예시를 들어주시면 감사하겠습니다.

     4. 여기서 FETCH와 EntityGraph 중 고민하다가 FETCH를 사용한 이유는, 단순하게 재사용성 없음/간단한 쿼리 이기 때문에 FETCH를 사용했는데
        제가 생각한 방식이 맞을까요 ??

     5. 그냥 간단하게, 간단한 쿼리 = FETCH JOIN, 복잡한 쿼리를 단순하게 유지, 재사용성 향상 = EntityGraph로 생각하면 되는데 어렵게
        생각하고 있나라는 생각도 듭니다.
        혹시 제가 말한 내용 중 잘못 알고 있는 내용이 있다면 바로잡아주시길 부탁드리겠습니다. 그리고 조금 더 첨언해주실 내용이 있다면
        말씀해주신다면 감사하겠습니다.

        + 과제 제출란에 적기엔 궁금한것과 내용이 길어져 주석으로 달았습니다,,

    ----------------------------------------------------------------------------------------------------------------*/

    /*@EntityGraph(attributePaths = {"user"})
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.todo.id = :todoId")
    List<Comment> findByTodoIdWithUser(@Param("todoId") Long todoId);*/

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.todo.id = :todoId")
    List<Comment> findByTodoIdWithUser(@Param("todoId") Long todoId);
}
