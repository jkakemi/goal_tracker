package com.goaltracker.gamification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MissionCompletedConsumer {
    @KafkaListener(topics = "mission-completed-topic", groupId = "gamification-group")
    public void consume(MissionCompletedEvent event) {
        System.out.println("🎧 Opa! Chegou mensagem do Mission Service!");
        System.out.println("👤 Usuário: " + event.userId());
        System.out.println("📚 Categoria: " + event.category());
        System.out.println("🔥 Dificuldade: " + event.difficulty());
    }
}
