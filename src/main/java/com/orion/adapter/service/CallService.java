package com.orion.adapter.service;

import com.orion.adapter.api.V1ApiDelegate;
import com.orion.adapter.avro.ACall;
import com.orion.adapter.avro.ACallId;
import com.orion.adapter.avro.AParticipant;
import com.orion.adapter.openapi.model.Call;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallService implements V1ApiDelegate {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    @Value("${kafka.call-topic-create}")
    private String createCallTopicName;

    @Value("${kafka.call-topic-delete}")
    private String deleteCallTopicName;

    @Override
    public ResponseEntity<String> createCall(Call call) {
        kafkaTemplate.send(createCallTopicName, createACallFromCall(call));
        log.info("CREATE CALL: " + call);
        return new ResponseEntity<>("Call request accepted", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> deleteCall(String callId) {
        kafkaTemplate.send(deleteCallTopicName, new ACallId(callId));
        log.info("DELETE CALL: " + callId);
        return new ResponseEntity<>("Call request accepted", HttpStatus.ACCEPTED);
    }

    private ACall createACallFromCall(Call call) {
        return new ACall(
            call.getId(),
            call.getCallerNumber(),
            call.getCalledNumber(),
            call.getEngagementDialogId(),
            call.getTimestamp(),
            new AParticipant(call.getParticipant().getId(), call.getParticipant().getName())
        );
    }
}
