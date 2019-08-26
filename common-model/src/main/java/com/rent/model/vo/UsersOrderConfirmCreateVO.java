package com.rent.model.vo;


import com.rent.model.bo.CouponCardAvailableBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UsersOrderConfirmCreateVO {

    /**
     * 商品分组数组
     */
    private List<ItemGroup> itemGroups;
    /**
     * 费用
     */
    private Fee fee;
    /**
     * 优惠劵列表 TODO 芋艿，后续改改
     */
    private List<CouponCardAvailableBO> couponCards;
    /**
     * 优惠劵优惠金额
     */
    private Integer couponCardDiscountTotal;
    /**
     * 商品分组
     *
     * 多个商品，参加同一个活动，从而形成分组。
     */
    @Data
    @Accessors(chain = true)
    public static class ItemGroup {

        /**
         * 优惠活动
         */
        // TODO 芋艿，目前只会有【满减送】的情况，未来有新的促销方式，可能需要改成数组
        // TODO 芋艿，后面改成 VO
        private PromotionActivityBO activity;
        /**
         * 商品数组
         */
        private List<Sku> items;

    }

    @Data
    @Accessors(chain = true)
    public static class Sku {

        // SKU 自带信息
        /**
         * sku 编号
         */
        private Integer id;
        /**
         * SPU 信息
         */
        private Spu spu;
        /**
         * 图片地址
         */
        private String picURL;
        /**
         * 规格值数组
         */
        private List<ProductAttrAndValuePairBO> attrs; // TODO 后面改下
        /**
         * 价格，单位：分
         */
        private Integer price;
        /**
         * 库存数量
         */
        private Integer quantity;

        // 非 SKU 自带信息

        /**
         * 购买数量
         */
        private Integer buyQuantity;
        /**
         * 原始单价，单位：分。
         */
        private Integer originPrice;
        /**
         * 购买单价，单位：分
         */
        private Integer buyPrice;
        /**
         * 最终价格，单位：分。
         */
        private Integer presentPrice;
        /**
         * 购买总金额，单位：分
         *
         * 用途类似 {@link #presentTotal}
         */
        private Integer buyTotal;
        /**
         * 优惠总金额，单位：分。
         */
        private Integer discountTotal;
        /**
         * 最终总金额，单位：分。
         *
         * 注意，presentPrice * quantity 不一定等于 presentTotal 。
         * 因为，存在无法整除的情况。
         * 举个例子，presentPrice = 8.33 ，quantity = 3 的情况，presentTotal 有可能是 24.99 ，也可能是 25 。
         * 所以，需要存储一个该字段。
         */
        private Integer presentTotal;

    }

    @Data
    @Accessors(chain = true)
    public static class Spu {

        /**
         * SPU 编号
         */
        private Integer id;

        // ========== 基本信息 =========
        /**
         * SPU 名字
         */
        private String name;
        /**
         * 分类编号
         */
        private Integer cid;
        /**
         * 商品主图地址
         *
         * 数组，以逗号分隔
         *
         * 建议尺寸：800*800像素，你可以拖拽图片调整顺序，最多上传15张
         */
        private List<String> picUrls;

    }

    /**
     * 费用（合计）
     */
    @Data
    @Accessors(chain = true)
    public static class Fee {

        /**
         * 购买总价
         */
        private Integer buyTotal;
        /**
         * 优惠总价
         *
         * 注意，满多少元包邮，不算在优惠中。
         */
        private Integer discountTotal;
        /**
         * 邮费
         */
        private Integer postageTotal;
        /**
         * 最终价格
         *
         * 计算公式 = 总价 - 优惠总价 + 邮费
         */
        private Integer presentTotal;

        public Fee() {
        }

        public Fee(Integer buyTotal, Integer discountTotal, Integer postageTotal, Integer presentTotal) {
            this.buyTotal = buyTotal;
            this.discountTotal = discountTotal;
            this.postageTotal = postageTotal;
            this.presentTotal = presentTotal;
        }
    }

    /**
     * 邮费信息
     */
    @Data
    @Accessors(chain = true)
    public static class Postage {

        /**
         * 需要满足多少钱，可以包邮。单位：分
         */
        private Integer threshold;

    }

}
