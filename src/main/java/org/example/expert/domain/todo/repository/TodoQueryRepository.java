package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QTodo qTodo = QTodo.todo;
    private final QUser qUser = QUser.user;

    public Optional<Todo> findByIdWithUser(long todoId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(qTodo)
            .leftJoin(qTodo.user, qUser).fetchJoin()
            .where(qTodo.id.eq(todoId))
            .fetchOne());
    }
}