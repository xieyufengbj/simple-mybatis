package com.variflight.entity;

import lombok.Data;

/**
 * @author XieYufeng
 * @ClassName: TJobHistory
 * @description:
 * @date 2019/5/8 16:03
 */
@Data
public class TJobHistory {

    private Integer id;

    private Integer userId;

    private String compName;

    private Integer years;

    private String title;
}
