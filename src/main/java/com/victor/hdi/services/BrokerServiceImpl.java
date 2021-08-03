package com.victor.hdi.services;

import com.victor.hdi.domain.BrokerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class BrokerServiceImpl {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient localApiClient;

    @Autowired
    public BrokerServiceImpl(@Lazy WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    public BrokerDTO getBroker(Long document){

        return localApiClient.get().
                uri("/broker/" + document).
                retrieve().
                bodyToMono(BrokerDTO.class)
                .block(REQUEST_TIMEOUT);

    }

    public BrokerDTO getBrokerStatus(Long document){

        BrokerDTO broker = getBroker(document);
        BrokerDTO brokerData;

        if(broker != null){
            brokerData = localApiClient.get().
                    uri("/brokerData/" + broker.getCode()).
                    retrieve().
                    bodyToMono(BrokerDTO.class)
                    .block(REQUEST_TIMEOUT);
        }else{
            return null;
        }

        if(brokerData.isActive()){
            broker.setCommissionRate(brokerData.getCommissionRate());
            broker.setCode(brokerData.getCode());
            broker.setActive(brokerData.isActive());
            return broker;
        }else{
            return null;
        }

    }

    public BrokerDTO updateBrokerStatus(Long document){

        BrokerDTO broker = getBroker(document);
        BrokerDTO brokerData = null;

        if(broker != null){
            brokerData = localApiClient.get().
                    uri("/brokerData/" + broker.getCode()).
                    retrieve().
                    bodyToMono(BrokerDTO.class)
                    .block(REQUEST_TIMEOUT);
        }else{
            return null;
        }

        if(brokerData.isActive()){
           brokerData.setActive(false);

        }else{
            brokerData.setActive(true);
        }

        broker = localApiClient.put().uri("/brokerData/" + broker.getCode())
                .body(BodyInserters.fromValue(brokerData))
                .retrieve()
                .bodyToMono(BrokerDTO.class)
                .block(REQUEST_TIMEOUT);

        return broker;

    }

    @Bean
    public WebClient localApiClient() {
        return WebClient.create("https://607732991ed0ae0017d6a9b0.mockapi.io/insurance/v1");
    }

}
