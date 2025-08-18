package com.example.BarrierKU.domain.indoor;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Department {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "department")
    private List<DepartmentNumber> departmentNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Room> rooms = new ArrayList<>();
}
