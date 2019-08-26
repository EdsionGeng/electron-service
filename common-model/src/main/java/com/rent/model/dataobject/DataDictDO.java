package com.rent.model.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据字典
 *
 * 使用 {@link #enumValue} 作为聚合。例如说：
 *
 * enumValue ：gender 性别
 *      value：1 男
 *      value：2 女
 */
@TableName("data_dict")
@Data
@Accessors(chain = true)
public class DataDictDO extends DeletableDO {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 大类枚举值
     */
    private String enumValue;
    /**
     * 小类数值
     */
    private String value;
    /**
     * 展示名
     */
    private String displayName;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 备注
     */
    private String memo;

}
