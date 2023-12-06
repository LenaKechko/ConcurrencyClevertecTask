package ru.clevertec.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Client {

    private final List<Integer> listData;
    @Getter
    public Long accumulator = 0L;

    public Client(int n) {
        this.listData = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
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
                log.error("Not a lot time for wait: " + e);
            }
        };
    }

    public int getListDataSize() {
        return listData.size();
    }

    public void addToAccumulator(Integer value) {
        accumulator += value;
    }

}
