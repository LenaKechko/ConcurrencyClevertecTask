package ru.clevertec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Server;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessTest {

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 100, 200})
    void requestOfClientShouldCalculateAccumulator(Integer value) {
        // given
        Client client = new Client(value);
        Server.cleanListRequest();
        Long expected = ((1L + value) * (value) / 2);

        // when
        Process.requestOfClient(client);
        Long actual = client.getAccumulator();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 100, 200})
    void requestOfClientShouldReturnEmptyListData(Integer value) {
        // given
        Client client = new Client(value);
        Server.cleanListRequest();

        // when
        Process.requestOfClient(client);
        int actual = client.getListDataSize();

        // then
        assertThat(actual)
                .isZero();
    }
}