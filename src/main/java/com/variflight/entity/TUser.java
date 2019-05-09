package com.variflight.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author XieYufeng
 * @ClassName: TUser
 * @description:
 * @date 2019/5/8 16:01
 */
@Data
public class TUser implements Serializable {

    private Integer id;

    private String userName;

    private String realName;

    private Byte sex;

    private String mobile;

    private String email;

    private String note;

    private TPosition position;

    private List<TJobHistory> jobs ;

    private List<HealthReport> healthReports;

    private List<TRole> roles;

}
