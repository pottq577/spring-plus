package org.example.expert.domain.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoSearchRequest {

    private String keyword;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Nullable
    private LocalDateTime start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Nullable
    private LocalDateTime end;

    private String nickname;
}