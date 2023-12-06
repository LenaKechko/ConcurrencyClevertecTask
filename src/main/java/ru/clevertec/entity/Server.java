package ru.clevertec.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс Сервер, который принимает запросы от Клиента,
 * складывает в их в listResponse.
 * В ответ на запрос Клиент получает текущий размер списка.
 * Работа ведется в потоке
 */
@Slf4j
public class Server implements Callable<Integer> {

    /**
     * Значение запроса от клиента
     */
    private final Integer value;
    /**
     * Список ответов клиенту
     */
    private static final List<Integer> listResponse = new ArrayList<>();
    /**
     * Поле для блокировки обрабатываемых запросов
     */
    private static final Lock lock = new ReentrantLock();

    /**
     * Конструктор
     *
     * @param value значение запроса от Клиента
     */
    public Server(Integer value) {
        this.value = value;
    }

    /**
     * Метод запускающий работу потока.
     * Запросы обрабатываются с задержкой от 100 до 1000 милисекунд.
     * Сервер может обрабатывать только один запрос за раз,
     * поэтому для разблокировки следующий запрос от Клиента ожидает 5 секунд.
     *
     * @return текущий размер списка ответов
     */

    @Override
    public Integer call() throws Exception {
        boolean locked = false;

        try {
            locked = lock.tryLock(5000, TimeUnit.MILLISECONDS);
            if (locked) {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * (1001 - 100) + 100));
                log.info("Запущена обработка запроса сервером. Значение " + value);
            }
        } finally {
            if (locked) {
                if (!listResponse.contains(value)) {
                    listResponse.add(value);
                }
                lock.unlock();
                log.info("Обработка сервером запроса со значением=" + value + " окончена");
            }
        }
        return listResponse.size();
    }

    /**
     * Статический метод вовращает размер списка ответов
     *
     * @return размер списка
     */
    public static int getListRequestSize() {
        return listResponse.size();
    }

    /**
     * Статический метод для очистки списка ответов
     */
    public static void cleanListRequest() {
        listResponse.clear();
    }
}
