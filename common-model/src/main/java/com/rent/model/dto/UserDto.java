package com.rent.model.dto;

import lombok.Data;


@Data

public class UserDto {
    private String alipayUserId;

    private String nickname;

    private Boolean isCertified;

    private String faceImage;


}
