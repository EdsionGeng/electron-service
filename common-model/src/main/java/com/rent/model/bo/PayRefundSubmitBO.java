package com.rent.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 退款单结果 BO
 */
@Data
@Accessors(chain = true)
public class PayRefundSubmitBO {

    /**
     * 退款
     */
    private Integer id;

}
