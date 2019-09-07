package com.people.rent.order;

import com.rent.model.CommonResult;
import com.rent.model.bo.OrderCommentReplyCreateBO;
import com.rent.model.bo.OrderCommentReplyPageBO;
import com.rent.model.constant.MallConstants;
import com.rent.model.dto.reply.OrderCommentReplyCreateDTO;
import com.rent.model.dto.reply.OrderCommentReplyPageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 *
 * 评论回复模块 Api(user)
 *
 * @author wtz
 * @time 2019-05-31 18:00
 */
@RestController
@RequestMapping(MallConstants.ROOT_PATH_USER + "/order_comment_reply")
@Api("用户评论回复模块 ")
public class OrderCommentReplyController {

   @Autowired
    private OrderCommentReplyService orderCommentReplyService;

    @PostMapping("create_order_comment_reply")
    //@RequiresLogin
    @ApiOperation(value = "创建订单回复")
    public CommonResult<OrderCommentReplyCreateBO> createOrderCommentReply(@RequestBody @Validated OrderCommentReplyCreateDTO orderCommentReplyCreateDTO){
        return CommonResult.success(orderCommentReplyService.createOrderCommentReply(orderCommentReplyCreateDTO));
    }

    @GetMapping("order_comment_reply_page")
    //@RequiresLogin
    @ApiOperation(value = "分页获取评论回复")
    public CommonResult<OrderCommentReplyPageBO> getOrderCommentReplyPage(@Validated OrderCommentReplyPageDTO orderCommentReplyCreateDTO){
        return CommonResult.success(orderCommentReplyService.getOrderCommentReplyPage(orderCommentReplyCreateDTO));
    }

}
