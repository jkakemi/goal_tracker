package com.goaltracker.gamification.infrastructure.adapter.event;

import com.goaltracker.gamification.application.usecases.AddXpUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MissionCompletedConsumer {

    private final AddXpUseCase addXpUseCase;

    public MissionCompletedConsumer(AddXpUseCase addXpUseCase) {
        this.addXpUseCase = addXpUseCase;
    }

    @KafkaListener(topics = "mission-completed-topic", groupId = "gamification-group-v5")
    public void consume(MissionCompletedEvent event) {
        System.out.println("🎧 Mensagem recebida! Processando XP para o usuário: " + event.userId());
        addXpUseCase.execute(event.userId(), event.difficulty());
//        System.out.println("👤 Usuário: " + event.userId());
//        System.out.println("📚 Categoria: " + event.category());
//        System.out.println("🔥 Dificuldade: " + event.difficulty());
    }
}
