package com.gt.game.core.exception.common;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class MemberException extends SystemException {
    public MemberException(String message) {
        super(message);
    }

    public MemberException(int code, String message) {
        super(code, message);
    }

    public MemberException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
