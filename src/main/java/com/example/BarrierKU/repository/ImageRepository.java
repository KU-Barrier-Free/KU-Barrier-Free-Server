package com.example.BarrierKU.repository;

import com.example.BarrierKU.domain.Image.RoomImage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void save(RoomImage roomImage) {
        em.persist(roomImage);
    }

    public RoomImage findById(Long id) {
        return em.find(RoomImage.class, id);
    }

    public List<RoomImage> findAll() {
        return em.createQuery("select img from RoomImage img", RoomImage.class)
                .getResultList();
    }

}
