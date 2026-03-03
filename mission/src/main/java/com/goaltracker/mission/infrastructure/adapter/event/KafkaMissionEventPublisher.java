package com.goaltracker.mission.infrastructure.adapter.event;

import com.goaltracker.mission.application.event.MissionCompletedEvent;
import com.goaltracker.mission.application.gateway.MissionEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMissionEventPublisher implements MissionEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaMissionEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishMissionCompleted(MissionCompletedEvent event){
        kafkaTemplate.send("mission-completed-topic", event);
        System.out.println("\uD83D\uDE80 Enviando evento de missão completada para o usuário: " + event.userId());
    }
}
