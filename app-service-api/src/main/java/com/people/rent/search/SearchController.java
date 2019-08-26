package com.people.rent.search;

import com.rent.model.CommonResult;
import com.rent.model.SortingField;
import com.rent.model.bo.ProductConditionBO;
import com.rent.model.bo.ProductPageBO;
import com.rent.model.dto.ProductConditionDTO;
import com.rent.model.dto.ProductSearchPageDTO;
import com.rent.util.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController

public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/page") // TODO 芋艿，后面把 BO 改成 VO
    public CommonResult<ProductPageBO> page(@RequestParam(value = "cid", required = false) Integer cid,
                                            @RequestParam(value = "keyword", required = false) String keyword,
                                            @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                            @RequestParam(value = "sortField", required = false) String sortField,
                                            @RequestParam(value = "sortOrder", required = false) String sortOrder) {
        // 创建 ProductSearchPageDTO 对象
        ProductSearchPageDTO productSearchPageDTO = new ProductSearchPageDTO().setCid(cid).setKeyword(keyword)
                .setPageNo(pageNo).setPageSize(pageSize);
        if (StringUtil.hasText(sortField) && StringUtil.hasText(sortOrder)) {
            productSearchPageDTO.setSorts(Collections.singletonList(new SortingField(sortField, sortOrder)));
        }
        // 执行搜索
        return CommonResult.success(searchService.getSearchPage(productSearchPageDTO));
    }

    @GetMapping("/condition") // TODO 芋艿，后面把 BO 改成 VO
    public CommonResult<ProductConditionBO> condition(@RequestParam(value = "keyword", required = false) String keyword) {
        // 创建 ProductConditionDTO 对象
        ProductConditionDTO productConditionDTO = new ProductConditionDTO().setKeyword(keyword)
                .setFields(Collections.singleton(ProductConditionDTO.FIELD_CATEGORY));
        // 执行搜索
        return CommonResult.success(searchService.getSearchCondition(productConditionDTO));
    }

}
