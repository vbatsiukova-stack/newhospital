package utils;

import people.Patient;
import model.MyLinkedList;

public class TestMain {

    public static void main(String[] args) {

        MyLinkedList<Patient> patients = new MyLinkedList<>();

        Patient p1 = new Patient("Anna", "Smith", "123456789", "New York", "P1", 25);
        Patient p2 = new Patient("Bob", "Brown", "987654321", "Chicago", "P2", 30);
        Patient p3 = new Patient("Kate", "White", "555123456", "Boston", "P3", 40);

        patients.add(p1);
        patients.add(p2);
        patients.add(p3);

        System.out.println("=== Initial Patients ===");
        System.out.println(patients);
        System.out.println("Size: " + patients.size());

        System.out.println("\npeople.Patient at index 1:");
        System.out.println(patients.get(1));

        patients.remove(0);

        System.out.println("\n=== After Removing First people.Patient ===");
        System.out.println(patients);
        System.out.println("Size: " + patients.size());
    }
}
