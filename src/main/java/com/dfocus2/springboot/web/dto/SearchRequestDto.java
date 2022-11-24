package com.dfocus2.springboot.web.dto;

import lombok.Getter;

@Getter
public class SearchRequestDto {
    private String key;
    private String value;

    public SearchRequestDto(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
