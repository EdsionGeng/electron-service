package com.people.rent.product;

import com.people.rent.convert.ProductSpuConvert;
import com.rent.model.bo.*;
import com.rent.model.constant.ProductErrorCodeEnum;
import com.rent.model.constant.ProductSpuConstants;
import com.rent.model.dataobject.ProductCategoryDO;
import com.rent.model.dataobject.ProductSkuDO;
import com.rent.model.dataobject.ProductSpuDO;
import com.rent.model.dto.ProductSpuPageDTO;
import com.rent.util.utils.ServiceExceptionUtil;
import com.rent.util.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductSpuService {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductAttrService productAttrService;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductSpuMapper productSpuMapper;




    public ProductSkuBO getProductSku(Integer id) {
        ProductSkuDO sku = productSkuMapper.selectById(id);
        return ProductSpuConvert.INSTANCE.convert4(sku);
    }

    public List<ProductSpuBO> getProductSpuList(Collection<Integer> ids) {
        List<ProductSpuDO> spus = productSpuMapper.selectByIds(ids);
        return ProductSpuConvert.INSTANCE.convert(spus);
    }

    public List<ProductSkuDetailBO> getProductSkuDetailList(Collection<Integer> ids) {
        // 查询 SKU 数组
        List<ProductSkuDO> skus = productSkuMapper.selectByIds(ids);
        if (skus.isEmpty()) {
            return Collections.emptyList();
        }
        // 查询 SPU 数组
        List<ProductSpuDO> spus = productSpuMapper.selectByIds(skus.stream().map(ProductSkuDO::getSpuId).collect(Collectors.toSet()));
        if (spus.isEmpty()) {
            return Collections.emptyList();
        }
        // 获得规格
        Set<Integer> productAttrValueIds = new HashSet<>();
        skus.forEach(sku -> productAttrValueIds.addAll(StringUtil.splitToInt(sku.getAttrs(), ",")));
        List<ProductAttrAndValuePairBO> attrAndValuePairList = productAttrService.validProductAttrAndValue(productAttrValueIds,
                false); // 读取规格时，不考虑规格是否被禁用
        // 返回成功
        return ProductSpuConvert.INSTANCE.convert3(skus, spus, attrAndValuePairList);
    }


    public ProductSpuDetailBO getProductSpuDetail(Integer id) {
        // 校验商品 spu 存在
        ProductSpuDO spu = productSpuMapper.selectById(id);
        if (spu == null) {
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_SPU_NOT_EXISTS.getCode());
        }
        // 获得商品分类分类
        ProductCategoryDO category = productCategoryService.getProductCategory(spu.getCid());
        Assert.notNull(category, String.format("分类编号(%d) 对应", spu.getCid()));
        // 获得商品 sku 数组
        List<ProductSkuDO> skus = productSkuMapper.selectListBySpuIdAndStatus(id, ProductSpuConstants.SKU_STATUS_ENABLE);
        // 获得规格
        Set<Integer> productAttrValueIds = new HashSet<>();
        skus.forEach(sku -> productAttrValueIds.addAll(StringUtil.splitToInt(sku.getAttrs(), ",")));
        List<ProductAttrAndValuePairBO> attrAndValuePairList = productAttrService.validProductAttrAndValue(productAttrValueIds,
                false); // 读取规格时，不考虑规格是否被禁用
        // 返回成功
        return ProductSpuConvert.INSTANCE.convert2(spu, skus, attrAndValuePairList, category);
    }


    public ProductSpuPageBO getProductSpuPage(ProductSpuPageDTO productSpuPageDTO) {
        ProductSpuPageBO productSpuPage = new ProductSpuPageBO();
        // 查询分页数据
        int offset = (productSpuPageDTO.getPageNo() - 1) * productSpuPageDTO.getPageSize();
        productSpuPage.setList(ProductSpuConvert.INSTANCE.convert(productSpuMapper.selectListByNameLikeOrderBySortAsc(
                productSpuPageDTO.getName(), productSpuPageDTO.getCid(), productSpuPageDTO.getVisible(),productSpuPageDTO.getShopId(),
                offset, productSpuPageDTO.getPageSize())));
        // 查询分页总数
        productSpuPage.setTotal(productSpuMapper.selectCountByNameLike(productSpuPageDTO.getName(), productSpuPageDTO.getCid(),
                productSpuPageDTO.getVisible(),productSpuPageDTO.getShopId()));
        // 返回结果
        return productSpuPage;
    }
}
