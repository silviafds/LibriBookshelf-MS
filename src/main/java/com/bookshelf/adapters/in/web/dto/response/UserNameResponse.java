package com.bookshelf.adapters.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNameResponse {
    private String nameUser;

    public UserNameResponse(Long userId, String usuárioIndisponível, Object o) {
    }
}
