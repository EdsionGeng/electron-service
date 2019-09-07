//package com.people.rent.search;
//
//import com.people.rent.cart.CartService;
//import com.people.rent.convert.ProductSearchConvert;
//import com.people.rent.product.ProductCategoryService;
//import com.people.rent.product.ProductSpuMapper;
//import com.people.rent.product.ProductSpuService;
//import com.rent.model.CollectionUtil;
//import com.rent.model.SortingField;
//import com.rent.model.bo.*;
//import com.rent.model.dataobject.ESProductDO;
//import com.rent.model.dto.ProductConditionDTO;
//import com.rent.model.dto.ProductSearchPageDTO;
//import com.rent.util.utils.StringUtil;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.aggregations.Aggregation;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class SearchService {
//
//    private static final Integer REBUILD_FETCH_PER_SIZE = 100;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//   // 因为需要使用到聚合操作，只好引入 ElasticsearchTemplate 。
//
//    @Autowired
//    ProductSpuService productSpuService;
//    @Autowired
//    private ProductCategoryService productCategoryService;
//    @Autowired
//    private CartService cartService;
//
//
////    public Integer rebuild() {
////        // TODO 芋艿，因为目前商品比较少，所以写的很粗暴。等未来重构
////        Integer lastId = null;
////        int rebuildCounts = 0;
////        while (true) {
////            List<ProductSpuDetailBO> spus = productSpuService.getProductSpuDetailListForSync(lastId, REBUILD_FETCH_PER_SIZE);
////            rebuildCounts += spus.size();
////            // 存储到 ES 中
////            List<ESProductDO> products = spus.stream().map(this::convert).collect(Collectors.toList());
////            productRepository.saveAll(products);
////            // 设置新的 lastId ，或者结束
////            if (spus.size() < REBUILD_FETCH_PER_SIZE) {
////                break;
////            } else {
////                lastId = spus.get(spus.size() - 1).getId();
////            }
////        }
////        // 返回成功
////        return rebuildCounts;
////    }
//
//
////    public Boolean save(Integer id) {
////        // 获得商品性情
////        ProductSpuDetailBO result = productSpuService.getProductSpuDetail(id);
////        // 存储到 ES 中
////        ESProductDO product = convert(result);
////        productRepository.save(product);
////        // 返回成功
////        return true;
////    }
//
//    @SuppressWarnings("OptionalGetWithoutIsPresent")
//    private ESProductDO convert(ProductSpuDetailBO spu) {
//        // 获得最小价格的 SKU ，用于下面的价格计算
//        ProductSpuDetailBO.Sku sku = spu.getSkus().stream().min(Comparator.comparing(ProductSpuDetailBO.Sku::getPrice)).get();
//        // 价格计算
//        CalcSkuPriceBO calSkuPriceResult = cartService.calcSkuPrice(sku.getId());
//        // 拼装结果
//        return ProductSearchConvert.INSTANCE.convert(spu, calSkuPriceResult);
//    }
//
//
//    public ProductPageBO getSearchPage(ProductSearchPageDTO searchPageDTO) {
//        checkSortFieldInvalid(searchPageDTO.getSorts());
//        // 执行查询
//        Page<ESProductDO> searchPage = productRepository.search(searchPageDTO.getCid(), searchPageDTO.getKeyword(),
//                searchPageDTO.getPageNo(), searchPageDTO.getPageSize(), searchPageDTO.getSorts());
//        // 转换结果
//        return new ProductPageBO()
//                .setList(ProductSearchConvert.INSTANCE.convert(searchPage.getContent()))
//                .setTotal((int) searchPage.getTotalElements());
//    }
//
//    private void checkSortFieldInvalid(List<SortingField> sorts) {
//        if (CollectionUtil.isEmpty(sorts)) {
//            return;
//        }
//        sorts.forEach(sortingField -> Assert.isTrue(ProductSearchPageDTO.SORT_FIELDS.contains(sortingField.getField()),
//                String.format("排序字段(%s) 不在允许范围内", sortingField.getField())));
//    }
//
//    public ProductConditionBO getSearchCondition(ProductConditionDTO conditionDTO) {
//        // 创建 ES 搜索条件
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        // 筛选
//        if (StringUtil.hasText(conditionDTO.getKeyword())) { // 如果有 keyword ，就去匹配
//            nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(conditionDTO.getKeyword(),
//                    "name", "sellPoint", "categoryName"));
//        } else {
//            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
//        }
//        // 聚合
//        if (conditionDTO.getFields().contains(ProductConditionDTO.FIELD_CATEGORY)) { // 商品分类
//            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("cids").field("cid"));
//        }
//        // 执行查询
//        ProductConditionBO condition =null;
////                elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), response -> {
////            ProductConditionBO result = new ProductConditionBO();
////            // categoryIds 聚合
////            Aggregation categoryIdsAggregation = response.getAggregations().get("cids");
////            if (categoryIdsAggregation != null) {
////                result.setCategories(new ArrayList<>());
////                for (LongTerms.Bucket bucket : (((LongTerms) categoryIdsAggregation).getBuckets())) {
////                    result.getCategories().add(new ProductConditionBO.Category().setId(bucket.getKeyAsNumber().intValue()));
////                }
////            }
////            // 返回结果
////            return result;
////        });
//        // 聚合其它数据源
//        if (!CollectionUtil.isEmpty(condition.getCategories())) {
//            // 查询指定的 ProductCategoryBO 数组，并转换成 ProductCategoryBO Map
//            Map<Integer, ProductCategoryBO
//                    > categoryMap = productCategoryService.getListByIds(
//                    condition.getCategories().stream().map(ProductConditionBO.Category::getId).collect(Collectors.toList()))
//                    .stream().collect(Collectors.toMap(ProductCategoryBO::getId, category -> category));
//            // 设置分类名
//            condition.getCategories().forEach(category -> category.setName(categoryMap.get(category.getId()).getName()));
//        }
//        // 返回结果
//        return condition;
//    }
//
//}
