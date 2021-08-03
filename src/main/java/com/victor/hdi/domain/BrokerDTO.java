package com.victor.hdi.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class BrokerDTO extends Response {

    private String name;

    private Long document;

    private String code;

    private Date createDate;

    private Double commissionRate;

    private boolean isActive;

    public BrokerDTO() {
    }

    @Builder
    public BrokerDTO(String name, Long document, String code, Date createDate) {
        this.name = name;
        this.document = document;
        this.code = code;
        this.createDate = createDate;
    }
}
