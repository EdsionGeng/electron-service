package com.people.rent.search;

import com.people.rent.product.ProductMapper;
import com.rent.model.CollectionUtil;
import com.rent.model.SortingField;
import com.rent.model.bo.ProductConditionBO;
import com.rent.model.bo.ProductPageBO;
import com.rent.model.dto.ProductConditionDTO;
import com.rent.model.dto.ProductSearchPageDTO;
import com.rent.util.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private ProductMapper productMapper;


    public ProductPageBO getSearchPage(ProductSearchPageDTO searchPageDTO) {
        checkSortFieldInvalid(searchPageDTO.getSorts());
        // 执行查询
        Page<ESProductDO> searchPage = productMapper.search(searchPageDTO.getCid(), searchPageDTO.getKeyword(),
                searchPageDTO.getPageNo(), searchPageDTO.getPageSize(), searchPageDTO.getSorts());
        // 转换结果
        return new ProductPageBO()
                .setList(ProductSearchConvert.INSTANCE.convert(searchPage.getContent()))
                .setTotal((int) searchPage.getTotalElements());
    }

    private void checkSortFieldInvalid(List<SortingField> sorts) {
        if (CollectionUtil.isEmpty(sorts)) {
            return;
        }
        sorts.forEach(sortingField -> Assert.isTrue(ProductSearchPageDTO.SORT_FIELDS.contains(sortingField.getField()),
                String.format("排序字段(%s) 不在允许范围内", sortingField.getField())));
    }


    public ProductConditionBO getSearchCondition(ProductConditionDTO conditionDTO) {
        // 创建 ES 搜索条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 筛选
        if (StringUtil.hasText(conditionDTO.getKeyword())) { // 如果有 keyword ，就去匹配
            nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(conditionDTO.getKeyword(),
                    "name", "sellPoint", "categoryName"));
        } else {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        }
        // 聚合
        if (conditionDTO.getFields().contains(ProductConditionDTO.FIELD_CATEGORY)) { // 商品分类
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("cids").field("cid"));
        }
        // 执行查询
        ProductConditionBO condition = elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), response -> {
            ProductConditionBO result = new ProductConditionBO();
            // categoryIds 聚合
            Aggregation categoryIdsAggregation = response.getAggregations().get("cids");
            if (categoryIdsAggregation != null) {
                result.setCategories(new ArrayList<>());
                for (LongTerms.Bucket bucket  : (((LongTerms) categoryIdsAggregation).getBuckets())) {
                    result.getCategories().add(new ProductConditionBO.Category().setId(bucket.getKeyAsNumber().intValue()));
                }
            }
            // 返回结果
            return result;
        });
        // 聚合其它数据源
        if (!CollectionUtil.isEmpty(condition.getCategories())) {
            // 查询指定的 ProductCategoryBO 数组，并转换成 ProductCategoryBO Map
            Map<Integer, ProductCategoryBO> categoryMap = productCategoryService.getListByIds(
                    condition.getCategories().stream().map(ProductConditionBO.Category::getId).collect(Collectors.toList()))
                    .stream().collect(Collectors.toMap(ProductCategoryBO::getId, category -> category));
            // 设置分类名
            condition.getCategories().forEach(category -> category.setName(categoryMap.get(category.getId()).getName()));
        }
        // 返回结果
        return condition;
    }
}
