/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exemple.service;

import com.exemple.model.Salle;
import com.exemple.respository.SalleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author hp
 */
public class SalleServiceImpl  implements SalleService {
    
    private EntityManager entityManager;
    private SalleRepository salleRepository;
    
    public SalleServiceImpl(EntityManager entityManager, SalleRepository salleRepository) {
        this.entityManager = entityManager;
        this.salleRepository = salleRepository;
    }
    
    @Override
    public List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end) {
        return salleRepository.findAvailableRooms(start, end);
    }
    
    @Override
    public List<Salle> searchRooms(Map<String, Object> criteria) {
        return salleRepository.findByCriteria(criteria);
    }
    
    @Override
    public List<Salle> getPaginatedRooms(int page, int size) {
        if (page < 1) page = 1;
        return salleRepository.findAllPaginated(page, size);
    }
    
    @Override
    public int getTotalPages(int size) {
        long count = salleRepository.count();
        return (int) Math.ceil((double) count / size);
    }
    
    @Override
    public Salle getRoomById(Long id) {
        return salleRepository.findById(id);
    }
    
    @Override
    public List<Salle> getAllRooms() {
        return salleRepository.findAll();
    }
    
    @Override
    public void saveRoom(Salle salle) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            salleRepository.save(salle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    @Override
    public void updateRoom(Salle salle) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            salleRepository.update(salle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    @Override
    public void deleteRoom(Salle salle) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            salleRepository.delete(salle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        }
    }