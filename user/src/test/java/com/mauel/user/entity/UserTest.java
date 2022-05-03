package com.mauel.user.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    @DisplayName("성별 타입 변환(enum <-> char)")
    @Test
    public void testSexConverting(){
        User user=User.builder()
                .sex(Sex.MALE).build();

        assertEquals(user.getSex(), Sex.MALE);
    }
}
