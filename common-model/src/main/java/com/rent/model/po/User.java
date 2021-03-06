package com.rent.model.po;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Integer id;

    private String alipayUserId;

    private String nickname;

    private Boolean isCertified;

    private String faceImage;

    private Integer zhimaScore;

    private Date registerTime;

}
