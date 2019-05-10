package com.variflight.mapper;

import com.variflight.entity.TJobHistory;
import com.variflight.entity.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TJobHistoryMapper {

    List<TJobHistory> selectByUserId(int userId);

    List<TUser> selectByEmailAndSex2(@Param("email")String email, @Param("sex")Byte sex);
}