package com.variflight.mapper;

import com.variflight.entity.TJobHistory;
import java.util.List;

public interface TJobHistoryMapper {

    List<TJobHistory> selectByUserId(int userId);
}