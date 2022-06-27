package com.fastcampus.helloSpringBatch.core.service;

import com.fastcampus.helloSpringBatch.dto.PlayerDto;
import com.fastcampus.helloSpringBatch.dto.PlayerSalaryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Year;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerSalaryServiceTest {

    private PlayerSalaryService playerSalaryService;

    @BeforeEach
    public void setup() {
        playerSalaryService = new PlayerSalaryService();
    }

    @Test
    public void calcSalary() {
        // Given
        Year mockYear = mock(Year.class);
        when(mockYear.getValue()).thenReturn(2022);
        Mockito.mockStatic(Year.class).when(Year::now).thenReturn(mockYear);
        // TEST가 언제되던지 실패하지 않게 설정하기 위해서... 처리

        PlayerDto mockPlayer = mock(PlayerDto.class);
        when(mockPlayer.getBirthYear()).thenReturn(1980);

        // When
        PlayerSalaryDto result = playerSalaryService.calcSalary(mockPlayer);

        // Then
        Assertions.assertEquals(result.getSalary(), 42000000);
    }
}