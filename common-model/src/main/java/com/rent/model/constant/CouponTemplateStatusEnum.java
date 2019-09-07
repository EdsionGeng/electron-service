package com.rent.model.constant;



import com.rent.model.core.IntArrayValuable;

import java.util.Arrays;

public enum CouponTemplateStatusEnum implements IntArrayValuable {

    ENABLE(1, "生效中"),
    DISABLE(2, "已失效"),
//    EXPIRE(3, "已过期"), TODO 芋艿，暂时不考虑过期的
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CouponTemplateStatusEnum::getValue).toArray();

    /**
     * 值
     */
    private final Integer value;
    /**
     * 名字
     */
    private final String name;

    CouponTemplateStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
