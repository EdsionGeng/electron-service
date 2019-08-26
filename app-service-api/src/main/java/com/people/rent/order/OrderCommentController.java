//package com.people.rent.order;
//

//import com.rent.model.CommonResult;
//import com.rent.model.constant.MallConstants;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.dubbo.config.annotation.Reference;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import static cn.iocoder.common.framework.vo.CommonResult.success;
//
///**
// *
// * 订单评论 Api(user)
// *
// * @author wtz
// * @time 2019-05-27 20:46
// */
//@RestController
//@RequestMapping(MallConstants.ROOT_PATH_USER + "/order_comment")
//@Api("用户评论模块")
//public class OrderCommentController {
//
//
//    private OrderCommentService orderCommentService;
//
//    @Reference(validation = "true", version = "${dubbo.provider.OrderCommentReplyService.version}")
//    private OrderCommentReplyService orderCommentReplyService;
//
//
//    @PostMapping("create_order_comment")
//    //@RequiresLogin
//    @ApiOperation(value = "创建订单评论")
//    public CommonResult<OrderCommentCreateBO> createOrderComment(@RequestBody @Validated OrderCommentCreateDTO orderCommentCreateDTO) {
//        Integer userId = UserSecurityContextHolder.getContext().getUserId();
//        orderCommentCreateDTO.setUserId(userId);
//        return CommonResult.success(orderCommentService.createOrderComment(orderCommentCreateDTO));
//    }
//
//    @GetMapping("order_comment_page")
//    @ApiOperation(value = "获取评论分页")
//    public CommonResult<OrderCommentPageBO> getOrderCommentPage(@Validated OrderCommentPageDTO orderCommentPageDTO){
//        return CommonResult.success(orderCommentService.getOrderCommentPage(orderCommentPageDTO));
//    }
//
//    @GetMapping("order_comment_info_merchant_reply")
//    @ApiOperation(value = "获取评论和商家回复")
//    public CommonResult<OrderCommentInfoAndMerchantReplyBO> geOrderCommentInfoAndMerchantReply(@RequestParam("commentId") Integer commentId){
//        OrderCommentInfoAndMerchantReplyBO orderCommentInfoAndMerchantReplyBO=new OrderCommentInfoAndMerchantReplyBO();
//        orderCommentInfoAndMerchantReplyBO.setOrderCommentInfoBO(orderCommentService.getOrderCommentInfo(commentId));
//        orderCommentInfoAndMerchantReplyBO.setOrderCommentMerchantReplyBOS(orderCommentReplyService.getOrderCommentMerchantReply(commentId));
//        return CommonResult.success(orderCommentInfoAndMerchantReplyBO);
//    }
//
//    @GetMapping
//    //@RequiresLogin
//    @ApiOperation(value = "获取订单评论状态分页")
//    public CommonResult<OrderCommentStateInfoPageBO> getOrderCommentStateInfoPage(@Validated OrderCommentStateInfoPageDTO orderCommentStateInfoPageDTO){
//        //Integer userId = UserSecurityContextHolder.getContext().getUserId();
//        //orderCommentStateInfoPageDTO.setUserId(userId);
//        return CommonResult.success(orderCommentService.getOrderCommentStateInfoPage(orderCommentStateInfoPageDTO));
//    }
//
//
//}
