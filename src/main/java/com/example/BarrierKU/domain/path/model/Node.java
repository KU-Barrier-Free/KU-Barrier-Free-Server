package com.example.BarrierKU.domain.path.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "node")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid", nullable = false)
    private String uid;

    @JsonProperty("sig_cd")
    @Column(name = "sig_cd", nullable = false)
    private Integer sigCd;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point location;
}
