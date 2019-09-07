package com.people.rent.order;

import com.people.rent.convert.OrderCommentReplyConvert;
import com.rent.model.bo.OrderCommentMerchantReplyBO;
import com.rent.model.bo.OrderCommentReplyCreateBO;
import com.rent.model.bo.OrderCommentReplyPageBO;
import com.rent.model.constant.OrderCommentRelpyTypeEnum;
import com.rent.model.constant.OrderReplyUserTypeEnum;
import com.rent.model.dataobject.OrderCommentReplyDO;
import com.rent.model.dto.reply.OrderCommentReplyCreateDTO;
import com.rent.model.dto.reply.OrderCommentReplyPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


@Service
public class OrderCommentReplyService {

    @Autowired
    private OrderCommentReplyMapper orderCommentReplayMapper;

    /**
     * 分页获取评论回复
     * @param orderCommentReplyPageDTO
     * @return
     */
    public OrderCommentReplyPageBO getOrderCommentReplyPage(OrderCommentReplyPageDTO orderCommentReplyPageDTO) {
        OrderCommentReplyPageBO orderCommentReplyPageBO=new OrderCommentReplyPageBO();
        //评论回复总数
        Integer totalCount=orderCommentReplayMapper.selectCommentReplyTotalCountByCommentId(orderCommentReplyPageDTO.getCommentId(),
                orderCommentReplyPageDTO.getUserType());
        //分页获取评论回复
        List<OrderCommentReplyDO> orderCommentReplyDOList=orderCommentReplayMapper.selectCommentReplyPage(orderCommentReplyPageDTO);
        orderCommentReplyPageBO.setTotal(totalCount);
        orderCommentReplyPageBO.setOrderCommentReplayItems(OrderCommentReplyConvert.INSTANCE.convertOrderCommentReplayItem(orderCommentReplyDOList));
        return orderCommentReplyPageBO;
    }


    /**
     * 创建评论回复
     * @param orderCommentReplyCreateDTO
     * @return
     */

    public OrderCommentReplyCreateBO createOrderCommentReply(OrderCommentReplyCreateDTO orderCommentReplyCreateDTO) {
        OrderCommentReplyDO orderCommentReplyDO=OrderCommentReplyConvert.INSTANCE.convert(orderCommentReplyCreateDTO);
        orderCommentReplyDO.setCreateTime(new Date());

        Integer replyType=orderCommentReplyCreateDTO.getCommentId()==orderCommentReplyCreateDTO.getParentId()?
                OrderCommentRelpyTypeEnum.COMMENT_REPLY.getValue():OrderCommentRelpyTypeEnum.REPLY_REPLY.getValue();

        orderCommentReplyDO.setReplyType(replyType);

        orderCommentReplayMapper.insert(orderCommentReplyDO);

        return OrderCommentReplyConvert.INSTANCE.convert(orderCommentReplyDO);
    }

    /**
     * 获取商家评论回复
     * @param commentId
     * @return
     */
    public List<OrderCommentMerchantReplyBO> getOrderCommentMerchantReply(Integer commentId) {
        List<OrderCommentReplyDO> orderCommentReplyDOList=orderCommentReplayMapper.selectCommentMerchantReplyByCommentIdAndUserType(commentId,
                OrderReplyUserTypeEnum.MERCHANT.getValue());
        return OrderCommentReplyConvert.INSTANCE.convert(orderCommentReplyDOList);
    }
}
