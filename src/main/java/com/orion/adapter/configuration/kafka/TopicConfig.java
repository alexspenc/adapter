package com.orion.adapter.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Value("${kafka.call-topic-create}")
    private String topicCreate;
    @Value("${kafka.call-topic-delete}")
    private String topicDelete;

    @Bean
    public NewTopic createCallTopic() {
        return TopicBuilder.name(topicCreate).build();
    }

    @Bean
    public NewTopic deleteCallTopic() {
        return TopicBuilder.name(topicDelete).build();
    }
}
