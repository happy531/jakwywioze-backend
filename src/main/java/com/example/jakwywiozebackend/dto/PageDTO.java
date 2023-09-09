package com.example.jakwywiozebackend.dto;

import lombok.Data;

import java.util.List;
@Data
public class PageDTO<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
}
