package com.people.rent.choose;

import com.people.rent.convert.TimeChooseConvert;
import com.people.rent.convert.UserAddressConvert;
import com.rent.model.CommonResult;
import com.rent.model.TimeChooseBO;
import com.rent.model.constant.DeletedStatusEnum;
import com.rent.model.dataobject.TimeChooseDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeChooseService {

    @Autowired
    private TimeChooseMapper timeChooseMapper;

    public CommonResult<List<TimeChooseBO>> timeChooseList() {
        List<TimeChooseDO> timeChooseDOList = timeChooseMapper.selectTimeChooseList(DeletedStatusEnum.DELETED_NO.getValue());
        List<TimeChooseBO> userAddressBOList = TimeChooseConvert.INSTANCE.convertUserAddressBOList(timeChooseDOList);

        return CommonResult.success(userAddressBOList);
    }
}
