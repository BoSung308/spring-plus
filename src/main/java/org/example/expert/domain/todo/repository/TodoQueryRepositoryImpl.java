package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepositoryImpl implements TodoQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {

        return Optional.ofNullable(queryFactory
                .select(todo)
                .from(todo)
                .leftJoin(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne()
        );
    }

    @Override
    public Page<TodoSearchResponse> searchTodosWithFilters(Pageable pageable,
                                                           LocalDateTime startDate,
                                                           LocalDateTime endDate,
                                                           String keyword,
                                                           String nickName
                                                           ) {

        // BooleanBuilder로 검색조건을 동적으로 사용
        BooleanBuilder builder = new BooleanBuilder();

        // 부분일치하는 제목 검색
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(todo.title.containsIgnoreCase(keyword));
        }

        // 부분일치하는 담당자 닉네임 검색
        if (nickName != null && !nickName.isEmpty()) {
            builder.and(user.nickName.containsIgnoreCase(nickName));
        }

        // 생성일 범위 검색
        if (startDate != null && endDate != null) {
            builder.and(todo.createdAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(todo.createdAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(todo.createdAt.loe(endDate));
        }

        List<TodoSearchResponse> results = queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,  // 이름 변경
                        todo.id,
                        todo.title,
                        // 담당자 수
                        select(manager.managerUser.count())
                                .from(manager)
                                .where(manager.todo.eq(todo)),
                        // 댓글 개수
                        select(comment.count())
                                .from(comment)
                                .where(comment.todo.eq(todo))
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .where(builder)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory
                .select(todo.count())
                .from(todo)
                .leftJoin(todo.user, user)
                .leftJoin(todo.comments)
                .where(builder)
                .fetchCount();


        return new PageImpl<>(results, pageable, total);
    }
}