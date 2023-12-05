package ru.clevertec.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class ServerTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private Server server;

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 10, 100})
    void getListRequestSizeShouldReturnNumberEqualValue(Integer value) {
        // given
        int expected = value;

        // when
        IntStream.rangeClosed(1, value)
                .forEach((i) -> {
                    server = new Server(i);
                    executor.submit(server);
                });
        try {
            if (executor.awaitTermination((long) value, TimeUnit.SECONDS)) {
                executor.shutdown();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int actual = Server.getListRequestSize();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }
}