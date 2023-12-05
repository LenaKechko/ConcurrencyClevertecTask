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
    private final Integer value;
    private static final List<Integer> listRequest = new ArrayList<>();
    private static final Lock lock = new ReentrantLock();

    public Server(Integer value) {
        this.value = value;
    }

    @Override
    public Integer call() throws Exception {
        boolean locked = false;

        try {
            locked = lock.tryLock(2000, TimeUnit.MILLISECONDS);
            if (locked) {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * (1001 - 100) + 100));
                log.info("Запущена обработка запроса сервером. Значение " + value);
                if (!listRequest.contains(value)) {
                    listRequest.add(value);
                    return listRequest.size();
                }
            }
        } finally {
            if (locked) {
                lock.unlock();
                log.info("Обработка сервером запроса со значением=" + value + " окончена");
            }
        }
        return 0;
    }

    public int getListRequestSize() {
        return listRequest.size();
    }
}
