package interfaces;

import people.Patient;

public interface Diagnosable {
    String diagnose(Patient patient);
}