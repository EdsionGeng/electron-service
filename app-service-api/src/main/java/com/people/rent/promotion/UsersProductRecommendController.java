package com.people.rent.promotion;


import com.rent.model.vo.UsersProductRecommendVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("product_recommend")
@Api("商品推荐模块")
public class UsersProductRecommendController {

    @Autowired
    private ProductRecommendService productRecommendService;
    @Autowired
    private ProductSpuService productSpuService;

    @GetMapping("/list")
    @ApiOperation("获得所有 Banner 列表")
    public CommonResult<Map<Integer, Collection<UsersProductRecommendVO>>> list() {
        // 查询商品推荐列表
        List<ProductRecommendBO> productRecommends = productRecommendService.getProductRecommendList(
                null, CommonStatusEnum.ENABLE.getValue());
        // 获得商品集合
        List<ProductSpuBO> spus = productSpuService.getProductSpuList(
                productRecommends.stream().map(ProductRecommendBO::getProductSpuId).collect(Collectors.toSet()));
        Map<Integer, ProductSpuBO> spuMap = spus.stream().collect(Collectors.toMap(ProductSpuBO::getId, account -> account));

        // 组合结果，返回
        Multimap<Integer, UsersProductRecommendVO> result = HashMultimap.create();
        productRecommends.sort(Comparator.comparing(ProductRecommendBO::getSort)); // 排序，按照 sort 升序
        productRecommends.forEach(productRecommendBO -> result.put(productRecommendBO.getType(),
                ProductRecommendConvert.INSTANCE.convert(spuMap.get(productRecommendBO.getProductSpuId())))); // 将 ProductSpuBO 添加到 results 中
        return CommonResult.success(result.asMap());
    }

}
