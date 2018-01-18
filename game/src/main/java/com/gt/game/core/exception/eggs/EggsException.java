package com.gt.game.core.exception.eggs;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class EggsException extends SystemException {
    public EggsException(String message) {
        super(message);
    }

    public EggsException(int code, String message) {
        super(code, message);
    }

    public EggsException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
