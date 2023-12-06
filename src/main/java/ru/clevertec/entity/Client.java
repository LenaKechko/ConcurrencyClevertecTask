package ru.clevertec.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс Клиент, который принимает формирует запросы на Сервер,
 * хранящиеся в listData.
 * В результате данный список будет пуст
 */
@Slf4j
public class Client {

    /**
     * Поле со списком значений для запросов
     */
    private final List<Integer> listData;
    /**
     * Вычисляемое в процессе обработки значение
     */
    @Getter
    public Long accumulator = 0L;

    /**
     * Конструктов формирующий список значений для запросов от Клиента от 1 до n
     */
    public Client(int n) {
        this.listData = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Метод рандомно берущий значение из списка значений для запросов Клиента
     *
     * @return значение из списка listData
     */
    public Integer getValue() {
        int index = (int) (Math.random() * (listData.size()));
        return listData.remove(index);
    }

    /**
     * Поток для запуска отправки запроса на Сервер
     *
     * @param value значение
     * @return объект потока
     */
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

    /**
     * Метод для возврата размера списка запросов
     *
     * @return целое значение размера списка
     */
    public int getListDataSize() {
        return listData.size();
    }

    /**
     * Изменение вычисляемого значения. Находится сумма значений всех ответов от сервера
     *
     * @param value значение от сервера
     */
    public void addToAccumulator(Integer value) {
        accumulator += value;
    }

}
