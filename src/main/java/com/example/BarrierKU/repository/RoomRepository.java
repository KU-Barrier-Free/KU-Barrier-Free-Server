package com.example.BarrierKU.repository;

import com.example.BarrierKU.domain.Indoor.Room;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {

    private final EntityManager em;

    public void save(Room room) {
        em.persist(room);
    }

    public Room findById(Long id) {
        return em.find(Room.class, id);
    }

    public List<Room> findAll() {
        return em.createQuery("select r from Room r", Room.class)
                .getResultList();
    }

    public List<Room> findByName(String name) {
        return em.createQuery("select r from Room r where r.roomName = :name", Room.class)
                .setParameter("name", name) // 쿼리 name 설정
                .getResultList();
    }
}
