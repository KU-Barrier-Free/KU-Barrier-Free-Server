package com.example.BarrierKU.repository;

import com.example.BarrierKU.domain.Indoor.Facilities;
import com.example.BarrierKU.domain.Indoor.Room;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FacilitiesRepository {

    private final EntityManager em;

    public void save(Facilities facilities) {
        em.persist(facilities);
    }

    public Facilities findById(Long id) {
        return em.find(Facilities.class, id);
    }

    public List<Facilities> findAll() {
        return em.createQuery("select f from Facilities f", Facilities.class)
                .getResultList();
    }

    public List<Facilities> findByName(String name) {
        return em.createQuery("select f from Facilities f where f.name = :name", Facilities.class)
                .setParameter("name", name) // 쿼리 name 설정
                .getResultList();
    }
}
