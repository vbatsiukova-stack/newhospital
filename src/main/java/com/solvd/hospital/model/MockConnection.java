package com.solvd.hospital.model;

public class MockConnection {

    private final int id;

    public MockConnection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String executeTask() {
        return "Connection-" + id + " executed task";
    }

    @Override
    public String toString() {
        return "Connection-" + id;
    }
}