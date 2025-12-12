package com.mzcteam01.mzcproject01be.domains.lecture.enums;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import lombok.Getter;

@Getter
public enum SearchType {
    Lately(1,"최신순"),
//    Popularity(2, "인기순"),
    Older(2, "날짜순");

    private final int code;
    private final String categorys;

    SearchType(int code, String categorys){
        this.code = code;
        this.categorys = categorys;
    }

    public static SearchType fromCode(int code){
        for (SearchType searchType : SearchType.values() ) {
            if(searchType.getCode() == code) {
                return searchType;
            }
        }

        throw new CustomException("카테고리에 맞는 검색조건을 설정해주세요!");
    }
}
