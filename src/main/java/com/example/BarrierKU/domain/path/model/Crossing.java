package com.example.BarrierKU.domain.path.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crossing")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Crossing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid", nullable = false)
    private String uid;

    @JsonProperty("sig_cd")
    @Column(name = "sig_cd", nullable = false)
    private String sigCd;

    @JsonProperty("f_node")
    @Column(name = "f_node", nullable = false)
    private String fNode;

    @JsonProperty("t_node")
    @Column(name = "t_node", nullable = false)
    private String tNode;

    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "degree", nullable = false)
    private Double degree;

    @Column(name = "width", nullable = false)
    private Double width;

    @Column(name = "type", nullable = false)
    private Integer type;

    @JsonProperty("ped_sig")
    @Column(name = "ped_sig", nullable = false)
    private Integer pedSig; // 보행신호 관련 필드
}