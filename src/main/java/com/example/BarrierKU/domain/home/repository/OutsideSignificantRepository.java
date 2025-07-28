package com.example.BarrierKU.domain.home.repository;

import com.example.BarrierKU.domain.outdoor.OutsideSignificant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OutsideSignificantRepository extends JpaRepository<OutsideSignificant, Long> {

    @Query("select os from OutsideSignificant os left join fetch os.outsideSignificantImages where os.id = :outsideSignificantId")
    Optional<OutsideSignificant> findOutsideSignificantWithImages(@Param("outsideSignificantId") Long outsideSignificantId);
}
