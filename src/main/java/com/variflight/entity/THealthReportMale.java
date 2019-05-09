package com.variflight.entity;

import lombok.Data;

@Data
public class THealthReportMale extends HealthReport {

    private String checkProject;

    private String detail;

    private Integer userId;

}