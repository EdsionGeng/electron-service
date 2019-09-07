package com.people.rent.product;


import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import com.people.rent.convert.ProductAttrConvert;
import com.rent.model.bo.ProductAttrAndValuePairBO;
import com.rent.model.bo.product.*;
import com.rent.model.constant.DeletedStatusEnum;
import com.rent.model.constant.ProductAttrConstants;
import com.rent.model.constant.ProductErrorCodeEnum;
import com.rent.model.dataobject.product.ProductAttrDO;
import com.rent.model.dataobject.product.ProductAttrValueDO;
import com.rent.model.dto.product.ProductAttrPageDTO;
import com.rent.model.dto.product.ProductAttrValueAddDTO;
import com.rent.model.dto.product.ProductAttrValueUpdateDTO;
import com.rent.util.utils.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductAttrService {

    @Autowired
    private ProductAttrMapper productAttrMapper;
    @Autowired
    private ProductAttrValueMapper productAttrValueMapper;

    public List<ProductAttrAndValuePairBO> validProductAttrAndValue(Set<Integer> productAttrValueIds, boolean validStatus) {
        // 首先，校验规格值
        List<ProductAttrValueDO> attrValues = productAttrValueMapper.selectListByIds(productAttrValueIds);
        if (attrValues.size() != productAttrValueIds.size()) {
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_VALUE_NOT_EXIST.getCode());
        }
        if (validStatus) {
            for (ProductAttrValueDO attrValue : attrValues) { // 同时，校验下状态
                if (ProductAttrConstants.ATTR_STATUS_DISABLE.equals(attrValue.getStatus())) {
                    throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_VALUE_NOT_EXIST.getCode());
                }
            }
        }
        // 然后，校验规格
        Set<Integer> attrIds = attrValues.stream().map(ProductAttrValueDO::getAttrId).collect(Collectors.toSet());
        List<ProductAttrDO> attrs = productAttrMapper.selectListByIds(attrIds);
        if (attrs.size() != attrIds.size()) {
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_NOT_EXIST.getCode());
        }
        if (validStatus) {
            for (ProductAttrDO attr : attrs) { // 同时，校验下状态
                if (ProductAttrConstants.ATTR_VALUE_STATUS_DISABLE.equals(attr.getStatus())) {
                    throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_NOT_EXIST.getCode());
                }
            }
        }
        // 返回成功
        Map<Integer, ProductAttrDO> attrMap = attrs.stream().collect(Collectors.toMap(ProductAttrDO::getId, productAttrDO -> productAttrDO)); // ProductAttrDO 的映射，方便查找。
        return attrValues.stream().map(productAttrValueDO -> new ProductAttrAndValuePairBO()
                .setAttrId(productAttrValueDO.getAttrId()).setAttrName(attrMap.get(productAttrValueDO.getAttrId()).getName())
                .setAttrValueId(productAttrValueDO.getId()).setAttrValueName(productAttrValueDO.getName())).collect(Collectors.toList());
    }


//    public ProductAttrPageBO getProductAttrPage(ProductAttrPageDTO productAttrPageDTO) {
//        ProductAttrPageBO productAttrPageBO = new ProductAttrPageBO();
//        // 查询分页数据
//        int offset = (productAttrPageDTO.getPageNo()-1) * productAttrPageDTO.getPageSize();
//        productAttrPageBO.setAttrs(ProductAttrConvert.INSTANCE.convert(productAttrMapper.selectListByNameLike(productAttrPageDTO.getName(),
//                offset, productAttrPageDTO.getPageSize())));
//        // 查询分页总数
//        productAttrPageBO.setCount(productAttrMapper.selectCountByNameLike(productAttrPageDTO.getName()));
//        // 将规格值拼接上去
//        if (!productAttrPageBO.getAttrs().isEmpty()) {
//            Set<Integer> attrIds = productAttrPageBO.getAttrs().stream().map(ProductAttrDetailBO::getId).collect(Collectors.toSet());
//            List<ProductAttrValueDO> attrValues = productAttrValueMapper.selectListByAttrIds(attrIds);
//            ImmutableListMultimap<Integer, ProductAttrValueDO> attrValueMap = Multimaps.index(attrValues, ProductAttrValueDO::getAttrId); // KEY 是 attrId ，VALUE 是 ProductAttrValueDO 数组
//            for (ProductAttrDetailBO productAttrDetailBO : productAttrPageBO.getAttrs()) {
//                productAttrDetailBO.setValues(ProductAttrConvert.INSTANCE.convert2(((attrValueMap).get(productAttrDetailBO.getId()))));
//            }
//        }
//        // 返回结果
//        return productAttrPageBO;
//    }


//    public List<ProductAttrSimpleBO> getProductAttrList() {
//        // 查询所有开启的规格数组
//        List<ProductAttrSimpleBO> attrs = ProductAttrConvert.INSTANCE.convert3(productAttrMapper.selectListByStatus(ProductAttrConstants.ATTR_STATUS_ENABLE));
//        // 如果为空，则返回空
//        if (attrs.isEmpty()) {
//            return Collections.emptyList();
//        }
//        // 将规格值拼接上去
//        List<ProductAttrValueDO> attrValues = productAttrValueMapper.selectListByStatus(ProductAttrConstants.ATTR_VALUE_STATUS_ENABLE);
//        ImmutableListMultimap<Integer, ProductAttrValueDO> attrValueMap = Multimaps.index(attrValues, ProductAttrValueDO::getAttrId); // KEY 是 attrId ，VALUE 是 ProductAttrValueDO 数组
//        for (ProductAttrSimpleBO productAttrSimpleBO : attrs) {
//            productAttrSimpleBO.setValues(ProductAttrConvert.INSTANCE.convert4(((attrValueMap).get(productAttrSimpleBO.getId()))));
//        }
//        return attrs;
//    }


//    public ProductAttrBO addProductAttr(Integer adminId, ProductAttrAddDTO productAttrAddDTO) {
//        // 校验规格名不重复
//        if (productAttrMapper.selectByName(productAttrAddDTO.getName()) != null) {
//            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_EXISTS.getCode());
//        }
//        // 插入到数据库
//        ProductAttrDO productAttrDO = ProductAttrConvert.INSTANCE.convert(productAttrAddDTO)
//                .setStatus(ProductAttrConstants.ATTR_STATUS_ENABLE);
//        productAttrDO.setCreateTime(new Date());
//        productAttrDO.setDeleted(DeletedStatusEnum.DELETED_NO.getValue());
//        productAttrMapper.insert(productAttrDO);
//        // 返回成功
//        return ProductAttrConvert.INSTANCE.convert(productAttrDO);
//    }


//    public Boolean updateProductAttr(Integer adminId, ProductAttrUpdateDTO productAttrUpdateDTO) {
//        // 校验存在
//        if (productAttrMapper.selectById(productAttrUpdateDTO.getId()) == null) {
//            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_NOT_EXIST.getCode());
//        }
//        // 校验规格名不重复
//        ProductAttrDO existsAttrDO = productAttrMapper.selectByName(productAttrUpdateDTO.getName());
//        if (existsAttrDO != null && !existsAttrDO.getId().equals(productAttrUpdateDTO.getId())) {
//            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_EXISTS.getCode());
//        }
//        // 更新到数据库
//        ProductAttrDO updateProductAttr = ProductAttrConvert.INSTANCE.convert(productAttrUpdateDTO);
//        productAttrMapper.update(updateProductAttr);
//        // 返回成功
//        return true;
//    }


    public Boolean updateProductAttrStatus(Integer adminId, Integer productAttrId, Integer status) {
        // 校验存在
        ProductAttrDO productAttrDO = productAttrMapper.selectById(productAttrId);
        if (productAttrDO == null) {
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_NOT_EXIST.getCode());
        }
        // 校验状态
        if (productAttrDO.getStatus().equals(status)) {
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_STATUS_EQUALS.getCode());
        }
        // 更新到数据库
        ProductAttrDO updateProductAttr = new ProductAttrDO().setId(productAttrId).setStatus(status);
        productAttrMapper.update(updateProductAttr);
        // 返回成功
        return true;
    }


//    public ProductAttrValueBO addProductAttrValue(Integer adminId, ProductAttrValueAddDTO productAttrValueAddDTO) {
//        // 校验规格名不重复
//        if (productAttrValueMapper.selectByAttrIdAndName(productAttrValueAddDTO.getAttrId(), productAttrValueAddDTO.getName()) != null) {
//            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_VALUE_EXISTS.getCode());
//        }
//        // 插入到数据库
//        ProductAttrValueDO productAttrValueDO = ProductAttrConvert.INSTANCE.convert(productAttrValueAddDTO)
//                .setStatus(ProductAttrConstants.ATTR_VALUE_STATUS_ENABLE);
//        productAttrValueDO.setCreateTime(new Date());
//        productAttrValueDO.setDeleted(DeletedStatusEnum.DELETED_NO.getValue());
//        productAttrValueMapper.insert(productAttrValueDO);
//        // 返回成功
//        return ProductAttrConvert.INSTANCE.convert2(productAttrValueDO);
//    }


//    public Boolean updateProductAttrValue(Integer adminId, ProductAttrValueUpdateDTO productAttrValueUpdateDTO) {
//        // 校验存在
//        ProductAttrValueDO productAttrValueDO = productAttrValueMapper.selectById(productAttrValueUpdateDTO.getId());
//        if (productAttrValueDO == null) {
//            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_VALUE_NOT_EXIST.getCode());
//        }
//        // 校验规格名不重复
//        ProductAttrValueDO existsAttrDO = productAttrValueMapper.selectByAttrIdAndName(productAttrValueDO.getAttrId(), productAttrValueUpdateDTO.getName());
//        if (existsAttrDO != null && !existsAttrDO.getId().equals(productAttrValueUpdateDTO.getId())) {
//            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_VALUE_EXISTS.getCode());
//        }
//        // 更新到数据库
//        ProductAttrValueDO updateProductValue = ProductAttrConvert.INSTANCE.convert(productAttrValueUpdateDTO);
//        productAttrValueMapper.update(updateProductValue);
//        // 返回成功
//        return true;
//    }


    public Boolean updateProductAttrValueStatus(Integer adminId, Integer productAttrValueId, Integer status) {
        // 校验存在
        ProductAttrValueDO productAttrValueDO = productAttrValueMapper.selectById(productAttrValueId);
        if (productAttrValueDO == null) {
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_VALUE_NOT_EXIST.getCode());
        }
        // 校验状态
        if (productAttrValueDO.getStatus().equals(status)) {
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.PRODUCT_ATTR_VALUE_STATUS_EQUALS.getCode());
        }
        // 更新到数据库
        ProductAttrValueDO updateProductAttrValue = new ProductAttrValueDO().setId(productAttrValueId).setStatus(status);
        productAttrValueMapper.update(updateProductAttrValue);
        // 返回成功
        return true;
    }

}
