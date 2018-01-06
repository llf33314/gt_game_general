package com.gt.game.core.exception.seagold;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class SeagoldException extends SystemException {
    public SeagoldException(String message) {
        super(message);
    }

    public SeagoldException(int code, String message) {
        super(code, message);
    }

    public SeagoldException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
