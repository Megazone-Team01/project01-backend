package com.mzcteam01.mzcproject01be.common.enums;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;

public enum ChannelType {
    ALL(0, "전체"),
    ONLINE(1, "온라인"),
    OFFLINE(2, "오프라인");

    private final int code;
    private final String description;

    ChannelType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ChannelType fromCode(int code) {
        for (ChannelType channelType : ChannelType.values()) {
            if (channelType.getCode() == code) {
                return channelType;
            }
        }
        throw new CustomException("존재하지 않는 채널 코드입니다 : " + code);
    }

}
