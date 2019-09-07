package com.rent.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 计算订单价格 DTO
 */
@Data
@Accessors(chain = true)
public class CalcOrderPriceDTO {

    @NotNull(message = "用户编号不能为空")
    private Integer userId;

    /**
     * 优惠劵编号
     */
    private Integer couponCardId;

    @NotNull(message = "商品数组不能为空")
    private List<Item> items;

    @Data
    @Accessors(chain = true)
    public static class Item {

        /**
         * SKU 编号
         */
        private Integer skuId;
        /**
         * 数量
         */
        private Integer quantity;
        /**
         * 是否选中
         * <p>
         * 注意下，目前只有在购物车的时候，才可能出现该属性为 false 。其它情况下，都会为 true 为主。
         */
        private Boolean selected;

        private Integer timeId;

        private Date startTime;

        private Date endTime;


        public Item() {
        }

        public Item(Integer skuId, Integer quantity, Boolean selected, Integer timeId, Date startTime, Date endTime) {
            this.skuId = skuId;
            this.quantity = quantity;
            this.selected = selected;
            this.timeId = timeId;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

}
