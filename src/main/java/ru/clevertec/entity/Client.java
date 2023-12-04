package ru.clevertec.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@Slf4j
public class Client {

    private final List<Integer> listData = new ArrayList<>();
    public static Long accumulator = 0L;

    ExecutorService executor = Executors.newCachedThreadPool(); // .newFixedThreadPool(2);

    public Client(int n) {
        IntStream.rangeClosed(1, n)
                .forEach(this.listData::add);
    }

    public Integer getValue() {
        int index = (int) (Math.random() * (listData.size()));
        return listData.remove(index);
    }

    public void requestOfClient() {
        Integer value = getValue();
        log.info("Отправка запроса от клиента со значением " + value);
        try {
            Future<Integer> response = executor.submit(new Server(value));
            accumulator += response.get();
            print();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception!!!!: " + e);
        }
    }

    public void print() {
        System.out.println(listData);
        System.out.println(accumulator);
    }

    public Long stop() {
        executor.shutdown();
        return accumulator;
    }
}
