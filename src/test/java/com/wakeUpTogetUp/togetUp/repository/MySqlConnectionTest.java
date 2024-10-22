package com.wakeUpTogetUp.togetUp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wakeUpTogetUp.togetUp.config.AbstractRepositoryTestContainers;
import javax.persistence.Query;
import org.junit.jupiter.api.Test;


public class MySqlConnectionTest extends AbstractRepositoryTestContainers {

    @Test
    void testConnection() {
        assertThat(entityManager).isNotNull();
    }

    @Test
    void testMissionObjectTableRowCount() {

        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM mission_object");
        Long count = ((Number) query.getSingleResult()).longValue();

        assertThat(count).isEqualTo(69);
    }

    @Test
    void testAvatarSpeechTableRowCount() {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM avatar_speech");
        Long count = ((Number) query.getSingleResult()).longValue();

        assertThat(count).isEqualTo(84);
    }
}
