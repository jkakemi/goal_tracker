package com.goaltracker.gamification.infrastructure.adapter.event;

import com.goaltracker.gamification.application.gateway.XpEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaXpEventPublisher implements XpEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaXpEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(XpAwardedEvent event) {
        kafkaTemplate.send("xp-awarded-topic", event);
        System.out.println("📢 Evento enviado! " + event.xpEarned() + " XP para o usuário: " + event.userId());
    }
}