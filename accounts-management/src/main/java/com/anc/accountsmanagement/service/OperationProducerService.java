package com.anc.accountsmanagement.service;


import com.anc.accountsmanagement.entities.Operation;
import com.anc.accountsmanagement.repositories.OperationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class OperationProducerService {
    private static final String[] TOPICS = {"DEPOSIT", "WITHDRAW"};
    private static final String[] CLIENTS = {"Account 1", "Account 2", "Account 3"};
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OperationRepository operationRepository;

    public OperationProducerService(KafkaTemplate<String, Object> kafkaTemplate, OperationRepository operationRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.operationRepository = operationRepository;
    }

    @Bean
    public void sendBill() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            long id = new Random().nextInt(10000);
            int stringIndex = new Random().nextInt(TOPICS.length);
            float amount = new Random().nextFloat() * 100000;
            Optional<Operation> operationOptional = operationRepository.findById(1L);
            if (operationOptional.isPresent()) {
                Operation operation = operationOptional.get();
                operation.setNumber(id);
                operation.setAmount(amount);
                kafkaTemplate.send(TOPICS[stringIndex], CLIENTS[new Random().nextInt(CLIENTS.length - 1)], operation);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }
}
