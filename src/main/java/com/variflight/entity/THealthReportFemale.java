package com.variflight.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class THealthReportFemale extends HealthReport {

    private String item;


    private BigDecimal score;



    private Integer userId;

}