package ru.clevertec.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Server implements Callable<Integer> {
    private Integer value = 0;
    private static final List<Integer> listRequest = new ArrayList<>();
    private static final Lock lock = new ReentrantLock();

    public Server(Integer value) {
        this.value = value;
    }

    @Override
    public Integer call() throws Exception {
        log.info("Запущена обработка запроса сервером. Значение " + value);
        lock.lock();
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * (1001 - 100) + 100));
            if (!listRequest.contains(value)) {
                listRequest.add(value);
                return listRequest.size();
            }
        } finally {
            lock.unlock();
            log.info("Обработка сервером запроса со значением=" + value + " окончена");
        }
        return 0;
    }
}
