package com.people.rent.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerBO> getBannerListByStatus(Integer status) {
        List<BannerDO> banners = bannerMapper.selectListByStatus(status);
        return BannerConvert.INSTANCE.convertToBO(banners);
    }
}
