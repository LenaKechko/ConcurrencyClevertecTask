package ru.clevertec.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class Client {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final List<Integer> listData = new ArrayList<>();
    private final int countRequest;
    public static Long accumulator = 0L;

    public Client(int n) {
        this.countRequest = n;
        IntStream.rangeClosed(1, n).forEach(this.listData::add);
    }

    public Integer getValue() {
        int index = (int) (Math.random() * (listData.size()));
        return listData.remove(index);
    }

    public Runnable getRequest(Integer value) {
        return () -> {
            log.info("Отправка запроса от клиента со значением " + value);
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * (501 - 100) + 100));
            } catch (InterruptedException e) {
                log.error("Exception!!!!: " + e);
            }
        };
    }

    public void requestOfClient() {
        List<Future<Integer>> responses =
                IntStream.rangeClosed(1, countRequest)
                        .mapToObj((i) -> {
                                    Integer value = this.getValue();
                                    executor.execute(getRequest(value));
                                    return executor.submit(new Server(value));
                                }
                        ).toList();
        for (Future<Integer> response : responses) {
            try {
                accumulator += response.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Exception!!!!: " + e);
            }
        }
        if (!executor.isTerminated()) {
            executor.shutdown();
        }
    }

    public int getListDataSize() {
        return listData.size();
    }

    public Long getAccumulator() {
        return accumulator;
    }
}
