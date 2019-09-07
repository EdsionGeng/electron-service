package com.people.rent.promotion;

import com.people.rent.convert.ProductRecommendConvert;
import com.people.rent.product.ProductSpuService;
import com.rent.model.bo.ProductRecommendBO;
import com.rent.model.bo.ProductRecommendPageBO;
import com.rent.model.constant.CommonStatusEnum;
import com.rent.model.constant.DeletedStatusEnum;
import com.rent.model.constant.PromotionErrorCodeEnum;
import com.rent.model.dataobject.ProductRecommendDO;
import com.rent.model.dto.ProductRecommendAddDTO;
import com.rent.model.dto.ProductRecommendPageDTO;
import com.rent.model.dto.ProductRecommendUpdateDTO;
import com.rent.util.utils.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ProductRecommendService {

    @Autowired
    private ProductSpuService productSpuService;

    @Autowired
    private ProductRecommendMapper productRecommendMapper;


    public List<ProductRecommendBO> getProductRecommendList(Integer type, Integer status) {
        List<ProductRecommendDO> productRecommends = productRecommendMapper.selectListByTypeAndStatus(type, status);
        return ProductRecommendConvert.INSTANCE.convertToBO(productRecommends);
    }


    public ProductRecommendPageBO getProductRecommendPage(ProductRecommendPageDTO productRecommendPageDTO) {
        ProductRecommendPageBO productRecommendPageBO = new ProductRecommendPageBO();
        // 查询分页数据
        int offset = (productRecommendPageDTO.getPageNo() - 1) * productRecommendPageDTO.getPageSize();
        productRecommendPageBO.setList(ProductRecommendConvert.INSTANCE.convertToBO(productRecommendMapper.selectPageByType(productRecommendPageDTO.getType(),
                offset, productRecommendPageDTO.getPageSize())));
        // 查询分页总数
        productRecommendPageBO.setTotal(productRecommendMapper.selectCountByType(productRecommendPageDTO.getType()));
        return productRecommendPageBO;
    }


    public ProductRecommendBO addProductRecommend(Integer adminId, ProductRecommendAddDTO productRecommendAddDTO) {
        // 校验商品不存在
        if (productSpuService.getProductSpuDetail(productRecommendAddDTO.getProductSpuId()) == null) {
            throw ServiceExceptionUtil.exception(PromotionErrorCodeEnum.PRODUCT_RECOMMEND_PRODUCT_NOT_EXISTS.getCode());
        }
        // 校验商品是否已经推荐
        if (productRecommendMapper.selectByProductSpuIdAndType(productRecommendAddDTO.getProductSpuId(), productRecommendAddDTO.getType()) != null) {
            throw ServiceExceptionUtil.exception(PromotionErrorCodeEnum.PRODUCT_RECOMMEND_EXISTS.getCode());
        }
        // 保存到数据库
        ProductRecommendDO productRecommend = ProductRecommendConvert.INSTANCE.convert(productRecommendAddDTO).setStatus(CommonStatusEnum.ENABLE.getValue());
        productRecommend.setDeleted(DeletedStatusEnum.DELETED_NO.getValue()).setCreateTime(new Date());
        productRecommendMapper.insert(productRecommend);
        // 返回成功
        return ProductRecommendConvert.INSTANCE.convertToBO(productRecommend);
    }


    public Boolean updateProductRecommend(Integer adminId, ProductRecommendUpdateDTO productRecommendUpdateDTO) {
        // 校验更新的商品推荐存在
        if (productRecommendMapper.selectById(productRecommendUpdateDTO.getId()) == null) {
            throw ServiceExceptionUtil.exception(PromotionErrorCodeEnum.PRODUCT_RECOMMEND_NOT_EXISTS.getCode());
        }
        // 校验商品不存在
        if (productSpuService.getProductSpuDetail(productRecommendUpdateDTO.getProductSpuId()) == null) {
            throw ServiceExceptionUtil.exception(PromotionErrorCodeEnum.PRODUCT_RECOMMEND_PRODUCT_NOT_EXISTS.getCode());
        }
        // 校验商品是否已经推荐
        ProductRecommendDO existProductRecommend = productRecommendMapper.selectByProductSpuIdAndType(productRecommendUpdateDTO.getProductSpuId(), productRecommendUpdateDTO.getType());
        if (existProductRecommend != null && !existProductRecommend.getId().equals(productRecommendUpdateDTO.getId())) {
            throw ServiceExceptionUtil.exception(PromotionErrorCodeEnum.PRODUCT_RECOMMEND_EXISTS.getCode());
        }
        // 更新到数据库
        ProductRecommendDO updateProductRecommend = ProductRecommendConvert.INSTANCE.convert(productRecommendUpdateDTO);
        productRecommendMapper.update(updateProductRecommend);
        // 返回成功
        return true;
    }


    public Boolean updateProductRecommendStatus(Integer adminId, Integer productRecommendId, Integer status) {
        // 校验更新的商品推荐存在
        if (productRecommendMapper.selectById(productRecommendId) == null) {
            throw ServiceExceptionUtil.exception(PromotionErrorCodeEnum.PRODUCT_RECOMMEND_NOT_EXISTS.getCode());
        }
        // 更新到数据库
        ProductRecommendDO updateProductRecommend = new ProductRecommendDO().setId(productRecommendId).setStatus(status);
        productRecommendMapper.update(updateProductRecommend);
        // 返回成功
        return true;
    }


    public Boolean deleteProductRecommend(Integer adminId, Integer productRecommendId) {
        // 校验更新的商品推荐存在
        if (productRecommendMapper.selectById(productRecommendId) == null) {
            throw ServiceExceptionUtil.exception(PromotionErrorCodeEnum.PRODUCT_RECOMMEND_NOT_EXISTS.getCode());
        }
        // 更新到数据库
        ProductRecommendDO updateProductRecommend = new ProductRecommendDO().setId(productRecommendId);
        updateProductRecommend.setDeleted(DeletedStatusEnum.DELETED_YES.getValue());
        productRecommendMapper.update(updateProductRecommend);
        // 返回成功
        return true;
    }

}
