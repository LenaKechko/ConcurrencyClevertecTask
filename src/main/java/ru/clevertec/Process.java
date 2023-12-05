package ru.clevertec;

import ru.clevertec.entity.Client;
import ru.clevertec.entity.Server;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Process {

    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void requestOfClient(Client client) {
        List<Future<Integer>> responses =
                IntStream.rangeClosed(1, client.getListDataSize())
                        .mapToObj((i) -> {
                                    Integer value = client.getValue();
                                    executor.execute(client.getRequest(value));
                                    return executor.submit(new Server(value));
                                }
                        ).toList();
        for (Future<Integer> response : responses) {
            try {
                client.addToAccumulator(response.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Response is nothing: " + e);
            }
        }
        if (!executor.isTerminated()) {
            executor.shutdown();
        }
    }
}
