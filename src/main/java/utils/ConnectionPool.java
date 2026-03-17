package utils;

import model.MockConnection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static volatile ConnectionPool instance;

    private static final int POOL_SIZE = 5;

    private final BlockingQueue<MockConnection> connections;

    private ConnectionPool() {

        connections = new ArrayBlockingQueue<>(POOL_SIZE);

        for (int i = 1; i <= POOL_SIZE; i++) {
            connections.add(new MockConnection(i));
        }
    }

    public static ConnectionPool getInstance() {

        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }

        return instance;
    }

    public MockConnection getConnection() throws InterruptedException {
        return connections.take();
    }

    public void releaseConnection(MockConnection connection) throws InterruptedException {
        connections.put(connection);
    }
}