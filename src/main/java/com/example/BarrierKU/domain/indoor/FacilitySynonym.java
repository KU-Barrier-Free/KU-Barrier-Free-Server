package com.example.BarrierKU.domain.indoor;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class FacilitySynonym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_synonym_id")
    private Long id;

    @Column(nullable = false)
    private String baseName;

    @Column(nullable = false)
    private String synonym;

}

