package com.rent.model.bo;

import lombok.Data;

import java.util.Date;

@Data
public class UserBO {

    private String alipayUserId;

    private String nickname;

    private Boolean isCertified;

    private String faceImage;

    private Integer zhimaScore;
}
