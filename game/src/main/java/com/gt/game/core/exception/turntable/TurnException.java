package com.gt.game.core.exception.turntable;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class TurnException extends SystemException {
    public TurnException(String message) {
        super(message);
    }

    public TurnException(int code, String message) {
        super(code, message);
    }

    public TurnException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
