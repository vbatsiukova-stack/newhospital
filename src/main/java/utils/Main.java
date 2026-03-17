package utils;

import model.MockConnection;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import main.java.enums.DepartmentType;
import main.java.enums.InsuranceType;
import main.java.enums.RoomType;
import exceptions.AppointmentConflictException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidInsuranceException;
import exceptions.LabTestNotReadyException;
import exceptions.PaymentFailedException;
import model.Appointment;
import model.Department;
import model.Hospital;
import model.Insurance;
import model.Invoice;
import model.LabTest;
import model.Money;
import model.Room;
import people.Doctor;
import people.Nurse;
import people.Patient;
import people.Staff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

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

        List<Staff> staffList = new ArrayList<>();
        staffList.add(d1);
        staffList.add(d2);
        staffList.add(n1);

        Department dep1 = new Department("Cardiology", DepartmentType.CARDIOLOGY);
        Department dep2 = new Department("Neurology", DepartmentType.NEUROLOGY);

        List<Department> departmentList = new ArrayList<>();
        departmentList.add(dep1);
        departmentList.add(dep2);

        Room room1 = new Room();
        room1.setType(RoomType.OPERATING);

        Room room2 = new Room();
        room2.setType(RoomType.GENERAL);

        List<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);

        Appointment ap1 = new Appointment(new Date(), p1, d1, "Checkup");
        Appointment ap2 = new Appointment(new Date(System.currentTimeMillis() + 60 * 60 * 1000L), p2, d1, "Consultation");
        Appointment ap3 = new Appointment(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000L), p3, d2, "Neurology visit");

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(ap1);
        appointmentList.add(ap2);
        appointmentList.add(ap3);

        LabTest lab1 = new LabTest("Blood Test", p1, d1, null);
        LabTest lab2 = new LabTest("Urine Test", p2, d1, "Ready");
        LabTest lab3 = new LabTest("MRI", p3, d2, null);

        List<LabTest> labTestList = new ArrayList<>();
        labTestList.add(lab1);
        labTestList.add(lab2);
        labTestList.add(lab3);

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

        // ===== Collection streaming demo =====

        logger.info("=== Collection streaming #1: all doctors ===");
        hospital.getAllDoctors()
                .forEach(doctor ->
                        logger.info(doctor.getLastName() + " " + doctor.getFirstName() + ", " + doctor.getSpecialization())
                );

        logger.info("=== Collection streaming #2: staff sorted by last name ===");
        hospital.getStaffSortedByLastName()
                .forEach(staff ->
                        logger.info(staff.getLastName() + " " + staff.getFirstName())
                );

        logger.info("=== Collection streaming #3: unique staff roles ===");
        hospital.getUniqueStaffRoles()
                .forEach(logger::info);

        logger.info("=== Collection streaming #4: appointments count by doctor ===");
        logger.info("Appointments for " + d1.getLastName() + ": " + hospital.countAppointmentsByDoctor(d1));

        logger.info("=== Collection streaming #5: first appointment by doctor ===");
        try {
            Appointment firstAppointment = hospital.findFirstAppointmentByDoctor(d1);
            logger.info("First appointment for " + d1.getLastName() + ": " + firstAppointment.getDateTime());
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
        }

        logger.info("=== Collection streaming #6: has operating room ===");
        logger.info("Hospital has operating room: " + hospital.hasOperatingRoom());

        logger.info("=== Collection streaming #7: lab tests without result ===");
        hospital.getLabTestsWithoutResult()
                .forEach(test -> logger.info(test.getTestName()));

        // ===== Record demo (Money) =====
        Invoice inv1 = new Invoice("INV-001", p1, new Date());
        inv1.setTotalAmount(1500.0);

        Invoice inv2 = new Invoice("INV-002", p2, new Date());
        inv2.setTotalAmount(750.0);

        Money m1 = new Money(BigDecimal.valueOf(inv1.calculateTotal()), Currency.getInstance("EUR"));
        Money m2 = new Money(BigDecimal.valueOf(inv2.calculateTotal()), Currency.getInstance("EUR"));

        logger.info("Record demo (Money): inv1 = " + m1);
        logger.info("Record demo (Money): inv2 = " + m2);

        // ===== Enum demo (DepartmentType) =====
        logger.info("Enum demo (DepartmentType): " + dep1.getType());

        // ===== Enum demo (RoomType) =====
        if (room1.getType() == RoomType.OPERATING) {
            logger.info("Enum demo (RoomType): Surgery can be performed here -> " + room1.getType());
        } else {
            logger.info("Enum demo (RoomType): Not suitable for surgery -> " + room1.getType());
        }

        // ===== Merge sort demo =====
        Patient[] patientsArray = { p1, p2, p3, p4 };

        logger.info("Patients BEFORE merge sort:");
        logPatients(patientsArray, logger);

        MergeSortUtil.mergeSort(patientsArray);

        logger.info("Patients AFTER merge sort:");
        logPatients(patientsArray, logger);

        // ===== Exception handling demo =====
        try {
            Staff foundStaff = hospital.findStaffByStaffId("S-001");
            logger.info("Found staff: " + foundStaff.getFirstName() + " " + foundStaff.getLastName());
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
        }

        // ===== Payment exception demo =====
        try {
            inv1.pay(2000);
            logger.info("Invoice " + inv1.getInvoiceNumber() + " paid successfully");
        } catch (PaymentFailedException e) {
            logger.error(e.getMessage());
        }

        // ===== Lab test exception demo =====
        try {
            lab1.getResultOrThrow();
        } catch (LabTestNotReadyException e) {
            logger.error(e.getMessage());
        }

        // ===== Insurance exception demo =====
        Insurance insurance = new Insurance("BestInsurance", "12");

        try {
            insurance.validate();
            logger.info("Insurance is valid");
        } catch (InvalidInsuranceException e) {
            logger.error(e.getMessage());
        }

        // ===== Enum demo (InsuranceType) =====
        insurance.setType(InsuranceType.PUBLIC);

        double bill = 1500.0;
        if (insurance.canCover(bill)) {
            logger.info("Enum demo (InsuranceType): " + insurance.getType() + " covers " + bill);
        } else {
            logger.info("Enum demo (InsuranceType): " + insurance.getType() + " does NOT cover " + bill);
        }

        // ===== Appointment conflict exception demo =====
        try {
            Date sameTime = new Date();

            Appointment conflict1 = new Appointment(sameTime, p1, d1, "Visit 1");
            Appointment conflict2 = new Appointment(sameTime, p2, d1, "Visit 2");

            Hospital conflictHospital = new Hospital();
            conflictHospital.setAppointments(new ArrayList<>());
            conflictHospital.scheduleAppointmentOrThrow(conflict1);
            conflictHospital.scheduleAppointmentOrThrow(conflict2);

            logger.info("Appointments scheduled successfully");
        } catch (AppointmentConflictException e) {
            logger.error(e.getMessage());
        }

        // ===== Method overloading demo (Hospital) =====
        try {
            Hospital overloadHospital = new Hospital();
            overloadHospital.setAppointments(new ArrayList<>());

            Date time = new Date();

            Appointment a3 = new Appointment(time, p1, d1);
            overloadHospital.scheduleAppointmentOrThrow(a3);

            overloadHospital.scheduleAppointmentOrThrow(
                    new Date(time.getTime() + 60 * 60 * 1000L),
                    p2,
                    d1
            );

            logger.info("Overloading demo executed");
        } catch (AppointmentConflictException e) {
            logger.error(e.getMessage());
        }

        // ===== Connection Pool demo =====
        logger.info("=== Connection Pool demo ===");

        ConnectionPool pool = ConnectionPool.getInstance();
        ExecutorService executor = Executors.newFixedThreadPool(7);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            int taskId = i;

            Callable<String> task = () -> {
                MockConnection connection = null;

                try {
                    logger.info("Task " + taskId + " waiting for connection");

                    connection = pool.getConnection();

                    logger.info("Task " + taskId + " got " + connection
                            + " | thread=" + Thread.currentThread().getName());

                    Thread.sleep(3000);

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
            boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);

            if (finished) {
                logger.info("All connection pool tasks finished");
            } else {
                logger.error("Timeout: not all connection pool tasks finished");
            }

            for (Future<String> future : futures) {
                logger.info("Future result: " + future.get());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Main thread interrupted: " + e.getMessage());
        } catch (ExecutionException e) {
            logger.error("Task execution failed: " + e.getMessage());
        }

        logger.info("Program finished");
    }

    private static void logPatients(Patient[] patients, AppLogger logger) {
        for (Patient patient : patients) {
            logger.info(patient.getLastName() + " " + patient.getFirstName() + ", age=" + patient.getAge());
        }
    }
}