package com.rent.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Banner 更新 DTO
 */
@Data
@Accessors(chain = true)
public class BannerUpdateDTO implements Serializable {

    @NotNull(message = "编号不能为空")
    private Integer id;
    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 32, message = "标题长度为 2-32 位")
    private String title;
    @NotEmpty(message = "跳转链接不能为空")

    @Size(max = 255, message = "跳转链接最大长度为 255 位")
    private String url;
    @NotEmpty(message = "图片链接不能为空")

    @Size(max = 255, message = "图片链接最大长度为 255 位")
    private String picUrl;
    @NotNull(message = "排序不能为空")
    private Integer sort;
    @Size(max = 255, message = "备注最大长度为 255 位")
    private String memo;

}
