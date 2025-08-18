package com.example.BarrierKU.domain.indoor;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DepartmentNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
