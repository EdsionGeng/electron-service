package com.people.rent.product;

import com.people.rent.convert.ProductCategoryConvert;
import com.rent.model.bo.ProductCategoryBO;
import com.rent.model.constant.ProductCategoryConstants;
import com.rent.model.dataobject.ProductCategoryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;


    public List<ProductCategoryBO> getListByPid(Integer pid) {
        List<ProductCategoryDO> categoryList = productCategoryMapper.selectListByPidAndStatusOrderBySort(pid, ProductCategoryConstants.STATUS_ENABLE);
        return ProductCategoryConvert.INSTANCE.convertToBO(categoryList);
    }


    public ProductCategoryDO getProductCategory(Integer productCategoryId) {
        return productCategoryMapper.selectById(productCategoryId);
    }


    public List<ProductCategoryBO> getListByIds(Collection<Integer> ids) {
        List<ProductCategoryDO> categoryList = productCategoryMapper.selectByIds(ids);
        return ProductCategoryConvert.INSTANCE.convertToBO(categoryList);
    }
}
