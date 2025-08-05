package com.example.BarrierKU.domain.path.model;

public enum PathType {
    SHORTEST,       // 일반 최단 경로
    NO_STAIRS,      // 계단 없는 경로
    BARRIER_FREE    // 경사도 & 계단 제외 & 보폭 반영 배리어프리 경로
}
