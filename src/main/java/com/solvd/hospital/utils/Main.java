package com.solvd.hospital.utils;

import com.solvd.hospital.model.MockConnection;
import com.solvd.hospital.utils.HospitalTextAnalyzer;

import java.util.concurrent.*;
import com.solvd.hospital.enums.*;
import com.solvd.hospital.exceptions.*;
import com.solvd.hospital.model.*;
import com.solvd.hospital.people.*;

import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        AppLogger logger = new AppLogger("app.log");
        logger.info("Program started");

        // ===== Base data =====
        Patient p1 = new Patient("Anna", "Koval", "111", "Street 1", "P-01", 30);
        Patient p2 = new Patient("Ihor", "Bondar", "222", "Street 2", "P-02", 25);
        Patient p3 = new Patient("Maria", "Andriienko", "333", "Street 3", "P-03", 40);
        Patient p4 = new Patient("Oleh", "Bondar", "444", "Street 4", "P-04", 20);

        Doctor d1 = new Doctor("Dmytro", "Shevchenko", "555", "Street 5", "D-01", "Cardiology");
        Doctor d2 = new Doctor("Iryna", "Melnyk", "777", "Street 7", "D-02", "Neurology");
        Nurse n1 = new Nurse("Olena", "Marchenko", "666", "Street 6", "N-01", "Senior", "Night");

        List<Staff> staffList = new ArrayList<>(List.of(d1, d2, n1));

        Department dep1 = new Department("Cardiology", DepartmentType.CARDIOLOGY);
        Department dep2 = new Department("Neurology", DepartmentType.NEUROLOGY);

        List<Department> departmentList = new ArrayList<>(List.of(dep1, dep2));

        Room room1 = new Room();
        room1.setType(RoomType.OPERATING);

        Room room2 = new Room();
        room2.setType(RoomType.GENERAL);

        List<Room> roomList = new ArrayList<>(List.of(room1, room2));

        Appointment ap1 = new Appointment(new Date(), p1, d1, "Checkup");
        Appointment ap2 = new Appointment(new Date(System.currentTimeMillis() + 3600000), p2, d1, "Consultation");
        Appointment ap3 = new Appointment(new Date(System.currentTimeMillis() + 7200000), p3, d2, "Neurology visit");

        List<Appointment> appointmentList = new ArrayList<>(List.of(ap1, ap2, ap3));

        LabTest lab1 = new LabTest("Blood Test", p1, d1, null);
        LabTest lab2 = new LabTest("Urine Test", p2, d1, "Ready");
        LabTest lab3 = new LabTest("MRI", p3, d2, null);

        List<LabTest> labTestList = new ArrayList<>(List.of(lab1, lab2, lab3));

        Hospital hospital = new Hospital(
                "City Hospital",
                "Main Street 10",
                "+380000000000",
                "hospital@example.com",
                "www.hospital.com",
                departmentList,
                roomList,
                staffList,
                appointmentList,
                labTestList
        );

        // ===== Connection Pool demo =====
        logger.info("=== Connection Pool demo ===");

        ConnectionPool pool = ConnectionPool.getInstance();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int taskId = i;

            Callable<String> task = () -> {
                MockConnection connection = null;
                try {
                    logger.info("Task " + taskId + " waiting for connection");

                    connection = pool.getConnection();

                    logger.info("Task " + taskId + " got " + connection);

                    Thread.sleep(2000);

                    return "Task " + taskId + " finished using " + connection;
                } finally {
                    if (connection != null) {
                        pool.releaseConnection(connection);
                        logger.info("Task " + taskId + " released " + connection);
                    }
                }
            };

            futures.add(executor.submit(task));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);

            for (Future<String> future : futures) {
                logger.info(future.get());
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // ===== TEXT PARSING =====
        try {
            HospitalTextAnalyzer.analyze("src/main/resources/input.txt", "output.csv");
            logger.info("Text parsing completed. Check output.csv");
        } catch (Exception e) {
            logger.error("Text parsing failed: " + e.getMessage());
        }

        logger.info("Program finished");
    }
}