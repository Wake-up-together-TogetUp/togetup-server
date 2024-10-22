package com.wakeUpTogetUp.togetUp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wakeUpTogetUp.togetUp.config.AbstractRepositoryTestContainers;
import org.junit.jupiter.api.Test;


public class MySqlConnectionTest extends AbstractRepositoryTestContainers {

    @Test
    void testConnection() {
        assertThat(entityManager).isNotNull();
    }

}
