package ru.clevertec;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Client;

@Slf4j
public class Main {
    public static void main(String[] args) {
//        System.out.println("Введите n: ");
//        Scanner sc = new Scanner(System.in);
//        int size = sc.nextInt();
        int size = 6;
        Client client = new Client(size);
//        client.requestOfClient();
//        client.remove(size);
//        client.print();
//        ExecutorService executor = Executors.newSingleThreadExecutor();
        client.requestOfClient();

//        List<Future<Integer>> futures =
//        IntStream.rangeClosed(1, size)
//                .forEach((i) -> executor.submit(new Server(client.remove())));
//                        .toList();

//                IntStream.rangeClosed(1, size)
//                        .forEach((i) -> executor.submit(new Client(size)));
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

        System.out.println(client.getAccumulator());

//        for (int i = 1; i < size; i++) {
//            Callable<Integer> callable
        // здесь будут вызываться запросы от клиента на сервер
        //            new Thread(new Client(i));
//        }
    }
}