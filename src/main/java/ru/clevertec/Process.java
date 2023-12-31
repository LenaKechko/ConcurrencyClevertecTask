package ru.clevertec;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Server;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;


/**
 * Класс отвечающий за работу и запуск многопоточности.
 */
@Slf4j
public class Process {

    /**
     * В методе в пуле потоков происходит обработка запросов от Клиента к Серверу.
     * Количество операций соответствует количеству запросов
     *
     * @param client объект Клиента
     */
    public static void requestOfClient(Client client) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Server.cleanListRequest();
        int countRequest = client.getListDataSize();
        List<Future<Integer>> responses =
                IntStream.rangeClosed(1, countRequest)
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
