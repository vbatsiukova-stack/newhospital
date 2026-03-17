package utils;

import model.MockConnection;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableMain {

    public static void main(String[] args) {
        AppLogger logger = new AppLogger("app.log");
        logger.info("Program started - CompletableFuture demo");

        ConnectionPool pool = ConnectionPool.getInstance();
        ExecutorService executor = Executors.newFixedThreadPool(7);

        List<CompletableFuture<String>> futures = IntStream.rangeClosed(1, 7)
                .mapToObj(taskId -> CompletableFuture.supplyAsync(() -> {
                    MockConnection connection = null;

                    try {
                        logger.info("Task " + taskId + " waiting for connection");

                        connection = pool.getConnection();

                        logger.info("Task " + taskId + " got " + connection
                                + " | thread=" + Thread.currentThread().getName());

                        Thread.sleep(3000);

                        return "Task " + taskId + " finished using " + connection;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return "Task " + taskId + " interrupted";
                    } finally {
                        if (connection != null) {
                            try {
                                pool.releaseConnection(connection);
                                logger.info("Task " + taskId + " released " + connection);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                logger.error("Release interrupted for task " + taskId);
                            }
                        }
                    }
                }, executor))
                .collect(Collectors.toList());

        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        results.forEach(result -> logger.info("CompletableFuture result: " + result));

        executor.shutdown();

        try {
            if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
                logger.info("All CompletableFuture tasks finished");
            } else {
                logger.error("Timeout: not all CompletableFuture tasks finished");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Main thread interrupted");
        }

        logger.info("Program finished - CompletableFuture demo");
    }
}