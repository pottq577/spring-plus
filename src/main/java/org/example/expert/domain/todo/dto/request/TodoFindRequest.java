package org.example.expert.domain.todo.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoFindRequest {

    private final String weather;
    private final String startDate;
    private final String endDate;
}
