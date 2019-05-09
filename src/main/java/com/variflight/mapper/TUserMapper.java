package com.variflight.mapper;

import com.variflight.entity.TUser;

import java.util.List;

/**
 * @author XieYufeng
 * @ClassName: TUserMapper
 * @description:
 * @date 2019/5/8 15:50
 */
public interface TUserMapper {

    List<TUser> selectForeach4In(String[] names);

    int insertForeach4Batch(List<TUser> users);

    int insertSelective(TUser record);

    int updateIfAndSetOper(TUser record);

    List<TUser> selectUserPosition1();

    List<TUser> selectUserPosition2();

    List<TUser> selectUserJobs1();

    List<TUser> selectUserJobs2();

    List<TUser> selectUserHealthReport();

    List<TUser> selectUserRole();
}
