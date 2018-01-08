package com.gt.game.core.exception.raiseflag;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class RaiseflagException extends SystemException {
    public RaiseflagException(String message) {
        super(message);
    }

    public RaiseflagException(int code, String message) {
        super(code, message);
    }

    public RaiseflagException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
