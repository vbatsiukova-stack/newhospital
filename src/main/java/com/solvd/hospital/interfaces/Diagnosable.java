package com.solvd.hospital.interfaces;

import com.solvd.hospital.people.Patient;

public interface Diagnosable {
    String diagnose(Patient patient);
}