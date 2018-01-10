package com.gt.game.core.exception.luck;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class LuckException extends SystemException {
    public LuckException(String message) {
        super(message);
    }

    public LuckException(int code, String message) {
        super(code, message);
    }

    public LuckException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
