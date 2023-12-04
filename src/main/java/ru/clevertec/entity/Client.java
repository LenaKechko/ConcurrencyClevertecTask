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

    private final List<Integer> listData = new ArrayList<>();
    public static Long accumulator = 0L;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    public Client(int n) {
        IntStream.rangeClosed(1, n)
                .forEach(this.listData::add);
    }

    public Integer getValue() {
        int index = (int) (Math.random() * (listData.size()));
        return listData.remove(index);
    }

    public void remove(int size) {
        IntStream.rangeClosed(1, size)
                .forEach((i) -> {
                    Integer value = getValue();
                    log.info("Отправка запроса от клиента со значением " + value);
                    try {
                        TimeUnit.MILLISECONDS.sleep((long) (Math.random() * (501 - 100) + 100));
                        Future<Integer> response = executor.submit(new Server(value));
                        accumulator += response.get();
                        print();
                    } catch (InterruptedException | ExecutionException e) {
                        System.err.println("Exception!!!!: " + e);
                    }
                });
    }

    public void print() {
        System.out.println(listData);
        System.out.println(accumulator);
    }

    public Long stop() {
        executor.shutdown();
        return accumulator;
    }

//    @Override
//    public void run() {
//        int value = (int) (Math.random() * (listData.size()));
//        Future<Integer> response = remove(value);
//        try {
//            accumulator += response.get();
//            print();
//        } catch (InterruptedException | ExecutionException e) {
//            System.err.println("Exception!!!!: " + e);
//        }
//    }
}
