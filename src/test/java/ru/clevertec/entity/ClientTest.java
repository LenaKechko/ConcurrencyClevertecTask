package ru.clevertec.entity;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {

    private Client client;

    @RepeatedTest(5)
    void getValue() {
        // given
        int countElement = 5;
        client = new Client(countElement);

        // when
        Integer actual = client.getValue();

        // then
        assertThat(actual)
                .isBetween(1, countElement);
    }

    @Test
    void getListDataSize() {
        // given
        int expected = 5;
        client = new Client(expected);

        // when
        Integer actual = client.getListDataSize();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void addToAccumulator(Integer number) {
        // given
        client = new Client(1);
        Long expected = client.getAccumulator() + number;

        // when
        client.addToAccumulator(number);
        Long actual = client.getAccumulator();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

}