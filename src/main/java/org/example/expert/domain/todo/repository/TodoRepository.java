package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 조건 없이 조회
    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    // 날씨만 있을 때
    @EntityGraph(attributePaths = {"user"})
    Page<Todo> findAllByWeatherOrderByModifiedAtDesc(Pageable pageable, String weather);

    @Query("""
        SELECT t
        FROM Todo t
        WHERE t.weather = :weather
        ORDER BY t.modifiedAt DESC
        """)
    @EntityGraph(attributePaths = {"user"})
    Page<Todo> findAllByWeather(Pageable pageable, String weather);

    // 날짜만 있을 때
    @EntityGraph(attributePaths = {"user"})
    Page<Todo> findAllByModifiedAtBetweenOrderByModifiedAtDesc(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
        SELECT t
        FROM Todo t
        WHERE t.modifiedAt BETWEEN :startDate AND :endDate
        ORDER BY t.modifiedAt DESC
        """)
    @EntityGraph(attributePaths = {"user"})
    Page<Todo> findAllByDateRange(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    // 둘 다 있을 때
    @EntityGraph(attributePaths = {"user"})
    Page<Todo> findAllByWeatherAndModifiedAtBetweenOrderByModifiedAtDesc(Pageable pageable, String weather, LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
        SELECT t
        FROM Todo t
        WHERE t.weather = :weather AND t.modifiedAt BETWEEN :startDate AND :endDate
        ORDER BY t.modifiedAt DESC
        """)
    @EntityGraph(attributePaths = {"user"})
    Page<Todo> findAllByWeatherAndDateRange(Pageable pageable, String weather, LocalDateTime startDate, LocalDateTime endDate);


    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
