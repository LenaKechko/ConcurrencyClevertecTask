package ru.clevertec;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Client;

import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            System.out.println("Введите n: ");
            Scanner sc = new Scanner(System.in);
            int size = sc.nextInt();
            Client client = new Client(size);
            Process.requestOfClient(client);
            log.info("Total accumulator = " + client.getAccumulator());
            Thread.sleep(5000);
        }
    }
}