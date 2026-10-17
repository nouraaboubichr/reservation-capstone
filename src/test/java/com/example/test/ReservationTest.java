package com.example.test;

import com.example.model.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;

public class ReservationTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("ReservationPU");
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        if (em != null) em.close();
    }

    @AfterAll
    public static void close() {
        if (emf != null) emf.close();
    }

    @Test
    public void testCreateReservation() {
        em.getTransaction().begin();

        Utilisateur user = new Utilisateur();
        user.setNom("Test User");
        user.setEmail("test.user@example.com");
        em.persist(user);

        // CORRECTION : on crée la salle au lieu de supposer qu'elle existe
        Salle salle = new Salle();
        salle.setNom("Salle Test");
        salle.setCapacite(10);
        em.persist(salle);

        Reservation reservation = new Reservation();
        reservation.setUtilisateur(user);
        reservation.setSalle(salle);
        reservation.setDateDebut(LocalDateTime.now().plusDays(2));
        reservation.setDateFin(LocalDateTime.now().plusDays(2).plusHours(1));

        em.persist(reservation);
        em.getTransaction().commit();

        Assertions.assertNotNull(reservation.getId());
    }
}