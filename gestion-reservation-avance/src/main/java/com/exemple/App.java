package com.exemple;

import com.exemple.Util.PaginationResult;
import com.exemple.model.Equipement;
import com.exemple.model.Reservation;
import com.exemple.model.Salle;
import com.exemple.model.Utilisateur;
import com.exemple.respository.SalleRepository;
import com.exemple.respository.SalleRepositoryImpl;
import com.exemple.service.SalleService;
import com.exemple.service.SalleServiceImpl;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-reservations");
        EntityManager em = emf.createEntityManager();

        try {
            // Initialisation des repositories et services
            SalleRepository salleRepository = new SalleRepositoryImpl(em);
            SalleService salleService = new SalleServiceImpl(em, salleRepository);

            // Initialisation des données de test
            initializeTestData(em);

            // Test 1: Recherche de salles disponibles par créneau
            testAvailableRooms(salleService);

            // Test 2: Recherche multi-critères
            testMultiCriteriaSearch(salleService);

            // Test 3: Pagination
            testPagination(salleService);

        } finally {
            em.close();
            emf.close();
        }
    }

    private static void initializeTestData(EntityManager em) {
        em.getTransaction().begin();

        Equipement projecteur = new Equipement("Projecteur", "Projecteur HD");
        Equipement ecran = new Equipement("Écran interactif", "Écran tactile 65 pouces");
        Equipement visioconference = new Equipement("Système de visioconférence", "Système complet avec caméra HD");

        em.persist(projecteur);
        em.persist(ecran);
        em.persist(visioconference);

        Utilisateur user1 = new Utilisateur("Dupont", "Jean", "jean.dupont@example.com");
        Utilisateur user2 = new Utilisateur("Martin", "Sophie", "sophie.martin@example.com");

        em.persist(user1);
        em.persist(user2);

        Salle salle1 = new Salle("Salle A101", 30);
        salle1.setBatiment("Bâtiment A");
        salle1.addEquipement(projecteur);

        Salle salle2 = new Salle("Salle B202", 15);
        salle2.setBatiment("Bâtiment B");
        salle2.addEquipement(ecran);

        em.persist(salle1);
        em.persist(salle2);

        LocalDateTime now = LocalDateTime.now();

        Reservation res1 = new Reservation(now.plusDays(1).withHour(9), now.plusDays(1).withHour(11), "Réunion");
        res1.setSalle(salle1);
        res1.setUtilisateur(user1);

        em.persist(res1);

        em.getTransaction().commit();
        System.out.println("Données de test initialisées !");
    }

    private static void testAvailableRooms(SalleService salleService) {
        LocalDateTime start = LocalDateTime.now().plusDays(1).withHour(9);
        LocalDateTime end = LocalDateTime.now().plusDays(1).withHour(11);

        List<Salle> availableRooms = salleService.findAvailableRooms(start, end);
        System.out.println("Salles disponibles :");
        for (Salle salle : availableRooms) {
            System.out.println("- " + salle.getNom());
        }
    }

    private static void testMultiCriteriaSearch(SalleService salleService) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("capaciteMin", 20);

        List<Salle> results = salleService.searchRooms(criteria);
        System.out.println("Résultats recherche multi-critères :");
        for (Salle salle : results) {
            System.out.println("- " + salle.getNom() + " (capacité : " + salle.getCapacite() + ")");
        }
    }

    private static void testPagination(SalleService salleService) {
        int pageSize = 2;
        int totalPages = salleService.getTotalPages(pageSize);

        for (int page = 1; page <= totalPages; page++) {
            List<Salle> pageRooms = salleService.getPaginatedRooms(page, pageSize);
            System.out.println("Page " + page + " :");
            for (Salle salle : pageRooms) {
                System.out.println("- " + salle.getNom());
            }
        }
        
        // PaginationResult

// 1️⃣ On récupère d’abord la première page
List<Salle> firstPage = salleService.getPaginatedRooms(1, pageSize);

         long totalItems;
        totalItems = salleService.getAllRooms().size();

// 3️⃣ On crée l’objet PaginationResult
PaginationResult<Salle> pagination = new PaginationResult<>(
    firstPage,   // les éléments de la page actuelle
    1,           // numéro de la page actuelle
    pageSize,    // taille de la page
    totalItems   // nombre total d'éléments
);

// 4️⃣ On affiche les informations
System.out.println("Total items : " + pagination.getTotalItems());
System.out.println("Total pages : " + pagination.getTotalPages());
        }
            }
        