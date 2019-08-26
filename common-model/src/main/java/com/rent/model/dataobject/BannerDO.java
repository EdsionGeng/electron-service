package com.rent.model.dataobject;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Banner 广告页
 */
@Data
@Accessors(chain = true)
public class BannerDO extends DeletableDO {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 跳转链接
     */
    private String url;
    /**
     * 图片链接
     */
    private String picUrl;
    /**
     * 排序
     */
    private Integer sort;
//    /**
//     * 状态
//     *
//     * {@link cn.iocoder.common.framework.constant.CommonStatusEnum}
//     */
    private Integer status;
    /**
     * 备注
     */
    private String memo;

    // TODO 芋艿 点击次数。&& 其他数据相关

}
