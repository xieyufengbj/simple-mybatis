package com.variflight.mapper;
import com.variflight.entity.THealthReportMale;

import java.util.List;

public interface THealthReportMaleMapper {
	
    List<THealthReportMale> selectByUserId(Integer userID);
}