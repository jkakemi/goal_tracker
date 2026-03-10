package com.goaltracker.user.infrastructure.consumer;

import com.goaltracker.user.application.usecases.CalculatorXpUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerXp {

    private final CalculatorXpUseCase calculatorXpUseCase;

    public ConsumerXp(CalculatorXpUseCase calculatorXpUseCase) {
        this.calculatorXpUseCase = calculatorXpUseCase;
    }

    @KafkaListener(topics = "mission-completed-topic", groupId = "gamification-group")
    public void CalculatorXp(XpMissionDto dto){
        calculatorXpUseCase.execute(dto.userId(), dto.xpEarned());
    }
}
