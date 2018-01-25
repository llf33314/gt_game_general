package com.gt.game.core.exception.turntable;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class TurntableException extends SystemException {
    public TurntableException(String message) {
        super(message);
    }

    public TurntableException(int code, String message) {
        super(code, message);
    }

    public TurntableException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
