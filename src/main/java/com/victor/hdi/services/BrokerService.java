package com.victor.hdi.services;

import com.google.inject.ImplementedBy;
import com.victor.hdi.domain.BrokerDTO;

@ImplementedBy(BrokerServiceImpl.class)
public interface BrokerService {

    BrokerDTO getBroker(Long document);

}
