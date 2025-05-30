package org.example.expert.domain.todo.repository;

import ch.qos.logback.core.util.StringUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QTodo qTodo = QTodo.todo;
    private final QUser qUser = QUser.user;
    private final QManager qManager = QManager.manager;

    public Optional<Todo> findByIdWithUser(long todoId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(qTodo)
            .leftJoin(qTodo.user, qUser).fetchJoin()
            .where(qTodo.id.eq(todoId))
            .fetchOne());
    }

    public Page<TodoSearchResponse> search(TodoSearchRequest request, Pageable pageable) {
        List<TodoSearchResponse> contents = queryFactory
            .select(Projections.constructor(
                TodoSearchResponse.class,
                qTodo.title,
                qTodo.managers.size(),
                qTodo.comments.size()
            ))
            .from(qTodo)
            .leftJoin(qTodo.managers, qManager)
            .where(
                containsKeyword(request.getKeyword()),
                containsNickname(request.getNickname()),
                betweenDate(request.getStart(), request.getEnd())
            )
            .orderBy(qTodo.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(qTodo.count())
            .from(qTodo)
            .leftJoin(qTodo.managers, qManager)
            .where(
                containsKeyword(request.getKeyword()),
                containsNickname(request.getNickname()),
                betweenDate(request.getStart(), request.getEnd())
            )
            .fetchOne();

        return new PageImpl<>(contents, pageable, total != null ? total : 0L);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (StringUtil.isNullOrEmpty(keyword)) {
            return null;
        }
        return qTodo.title.contains(keyword);
    }

    private BooleanExpression betweenDate(LocalDateTime start, LocalDateTime end) {
        if (start == null && end == null) {
            return null;
        }
        return qTodo.createdAt.between(start, end);
    }

    private BooleanExpression containsNickname(String nickname) {
        if (StringUtil.isNullOrEmpty(nickname)) {
            return null;
        }
        return qManager.user.nickname.contains(nickname);
    }
}