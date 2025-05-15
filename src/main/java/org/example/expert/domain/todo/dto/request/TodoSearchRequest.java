package org.example.expert.domain.todo.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoSearchRequest {

    private final String keyword;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String nickname;
}