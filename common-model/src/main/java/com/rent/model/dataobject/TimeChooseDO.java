package com.rent.model.dataobject;

import lombok.Data;

@Data
public class TimeChooseDO extends DeletableDO {

    private Integer id;

    private String desc;

    private Integer days;

}
