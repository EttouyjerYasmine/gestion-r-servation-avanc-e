/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exemple.respository;

/**
 *
 * @author hp
 */

import com.exemple.model.Salle;
import static java.nio.file.Files.size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SalleRepository {
    // Méthode pour trouver les salles disponibles par créneau
    List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end);
    
    // Méthode pour la recherche multi-critères
    List<Salle> findByCriteria(Map<String, Object> criteria);
    
    // Méthode pour la pagination
    List<Salle> findAllPaginated(int page, int size);
    
    // Méthode pour compter le nombre total de salles (pour la pagination)
    long count();
   
List<Salle> searchRooms(Map<String,Object> criteria);

int getTotalPages(int pageSize);

List<Salle> getPaginatedRooms(int page, int pageSize);

List<Salle> getAllRooms();

    
    // Autres méthodes CRUD de base
    Salle findById(Long id);
    List<Salle> findAll();
    void save(Salle salle);
    void update(Salle salle);
    void delete(Salle salle);

    public List<Salle> getAll();

    
    }