package com.example.gkl.fxControllers;

import com.example.gkl.fxControllers.OrderController;
import com.example.gkl.model.User;
import com.example.gkl.hibernateControllers.UserHib;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class OrderInfoTest {

    private OrderController controller;

    @BeforeEach
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");


        UserHib userHib = new UserHib(entityManagerFactory);
        User currentUser = userHib.getUserByCredentials("Testuotojas2", "Testuotojas2@");


        controller = new OrderController();
        controller.setData(entityManagerFactory, currentUser);


        controller.orderListManager = new ListView<>();
        controller.filterDateStart = new DatePicker();
        controller.filterDateEnd = new DatePicker();
    }

    @Test
    public void testFilterByDate_NonEmptyOrderList() {
        controller.filterDateStart.setValue(LocalDate.of(2024, Month.APRIL, 1));
        controller.filterDateEnd.setValue(LocalDate.now());
        controller.filterByDate();
        assertFalse(controller.orderListManager.getItems().isEmpty(), "Order list should not be empty");
    }
    @Test
    public void testFilterByDate_EmptyOrderList() {
        controller.filterDateStart.setValue(LocalDate.of(2024, Month.JANUARY, 1));
        controller.filterDateEnd.setValue(LocalDate.of(2024, Month.JANUARY, 30));
        controller.filterByDate();
        assertTrue(controller.orderListManager.getItems().isEmpty(), "Order list should be empty");
    }


}