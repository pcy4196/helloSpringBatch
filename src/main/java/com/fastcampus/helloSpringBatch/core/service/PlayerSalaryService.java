package com.fastcampus.helloSpringBatch.core.service;

import com.fastcampus.helloSpringBatch.dto.PlayerDto;
import com.fastcampus.helloSpringBatch.dto.PlayerSalaryDto;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class PlayerSalaryService {

    public PlayerSalaryDto calcSalary(PlayerDto player) {
        int salary = (Year.now().getValue() - player.getBirthYear()) * 1000000;
        return PlayerSalaryDto.of(player, salary);
    }
}
