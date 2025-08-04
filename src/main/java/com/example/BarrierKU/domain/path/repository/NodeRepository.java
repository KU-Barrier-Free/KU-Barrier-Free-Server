package com.example.BarrierKU.domain.path.repository;

import com.example.BarrierKU.domain.path.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface NodeRepository extends JpaRepository<Node, Long> {
}


