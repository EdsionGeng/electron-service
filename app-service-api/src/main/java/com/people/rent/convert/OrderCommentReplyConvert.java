package com.people.rent.convert;

import com.rent.model.bo.OrderCommentMerchantReplyBO;
import com.rent.model.bo.OrderCommentReplyCreateBO;
import com.rent.model.bo.OrderCommentReplyPageBO;
import com.rent.model.dataobject.OrderCommentReplyDO;
import com.rent.model.dto.reply.OrderCommentReplyCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 * 评论回复 convert
 *
 * @author wtz
 * @time 2019-05-31 18:30
 */
@Mapper
public interface OrderCommentReplyConvert {

    OrderCommentReplyConvert INSTANCE = Mappers.getMapper(OrderCommentReplyConvert.class);

    @Mappings({})
    OrderCommentReplyDO convert(OrderCommentReplyCreateDTO orderCommentReplyCreateDTO);

    @Mappings({})
    OrderCommentReplyCreateBO convert(OrderCommentReplyDO orderCommentReplyDO);

    @Mappings({})
    List<OrderCommentMerchantReplyBO> convert(List<OrderCommentReplyDO> orderCommentReplyDOList);

    @Mappings({})
    List<OrderCommentReplyPageBO.OrderCommentReplayItem> convertOrderCommentReplyItem(List<OrderCommentReplyDO> orderCommentReplyDOList);



    @Mappings({})
    List<OrderCommentReplyPageBO.OrderCommentReplayItem> convertOrderCommentReplayItem(List<OrderCommentReplyDO> orderCommentReplyDOList);
}
