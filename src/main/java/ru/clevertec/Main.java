package ru.clevertec;

import ru.clevertec.entity.Client;
import ru.clevertec.entity.Server;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Введите n: ");
//        Scanner sc = new Scanner(System.in);
//        int size = sc.nextInt();
        int size = 10;
        Client client = new Client(size);
        client.remove(size);
//        client.print();
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        List<Future<Integer>> futures =
//        IntStream.rangeClosed(1, size)
//                .forEach((i) -> client.remove());
//                        .toList();

//        client.print();
//        for (Future<Integer> future : futures) {
//            try {
//                int result = future.get();
//                System.out.println("Вернулся ответ от сервера = " + result);
//
//            } catch (InterruptedException | ExecutionException e) {
//                System.err.println("Exception!!!!: " + e);
//            }
//        }
        // Останавливаем пул потоков


//        executor.shutdown();

        System.out.println(client.stop());
//        for (int i = 1; i < size; i++) {
//            Callable<Integer> callable
        // здесь будут вызываться запросы от клиента на сервер
//            new Thread(new MyLockClass(i));
        //            new Thread(new Client(i));
//        }
    }
}