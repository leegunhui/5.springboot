package com.korea.mBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private String error;
    private List<T> data;
}
