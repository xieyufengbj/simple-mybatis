package com.variflight.mapper;


import com.variflight.entity.THealthReportFemale;

import java.util.List;

public interface THealthReportFemaleMapper {
    List<THealthReportFemale> selectByUserId(Integer userId);
}