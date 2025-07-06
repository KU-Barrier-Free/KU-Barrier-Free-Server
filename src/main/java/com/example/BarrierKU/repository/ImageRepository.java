package com.example.BarrierKU.repository;

import com.example.BarrierKU.domain.Image.Image;
import com.example.BarrierKU.domain.Indoor.Facilities;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

    public Image findById(Long id) {
        return em.find(Image.class, id);
    }

    public List<Image> findAll() {
        return em.createQuery("select img from Image img", Image.class)
                .getResultList();
    }

}
