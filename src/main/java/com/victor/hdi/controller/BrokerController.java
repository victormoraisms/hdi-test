package com.victor.hdi.controller;


import com.google.inject.Inject;
import com.victor.hdi.domain.BrokerDTO;
import com.victor.hdi.domain.Response;
import com.victor.hdi.domain.ResponseError;
import com.victor.hdi.services.BrokerService;
import com.victor.hdi.services.BrokerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/broker")
public class BrokerController {

    @Autowired
    private BrokerServiceImpl brokerService;

    @GetMapping("/checkActive/{document}")
    public ResponseEntity<Response> checkActiveBroker(@PathVariable("document") Long document){

        BrokerDTO broker = brokerService.getBrokerStatus(document);

        if(broker != null){
            return ResponseEntity.status(HttpStatus.OK).body(broker);
        }else{
            ResponseError error = new ResponseError();
            error.setBusinessErrorMessage("Usuário inativo.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @GetMapping("/updateStatus/{document}")
    public ResponseEntity<Response> updateBrokerStatus(@PathVariable("document") Long document){
        BrokerDTO updatedBroker;

        try{

            updatedBroker =  brokerService.updateBrokerStatus(document);

        }catch (Exception e){
            ResponseError error = new ResponseError();
            error.setBusinessErrorMessage("Usuário inativo.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedBroker);

    }

}
