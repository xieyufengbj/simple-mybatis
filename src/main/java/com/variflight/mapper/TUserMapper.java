package com.variflight.mapper;

import com.variflight.entity.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<TUser> selectByEmailAndSex2(@Param("email")String email, @Param("sex")Byte sex);

    int insert1(TUser record);

    List<TUser> selectByEmailAndSex1(Map<String, Object> param);
}
