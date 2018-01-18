package com.gt.game.core.exception.countmoney;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class CountmoneyException extends SystemException {
    public CountmoneyException(String message) {
        super(message);
    }

    public CountmoneyException(int code, String message) {
        super(code, message);
    }

    public CountmoneyException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
