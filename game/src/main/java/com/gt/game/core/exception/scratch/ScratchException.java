package com.gt.game.core.exception.scratch;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class ScratchException extends SystemException {
    public ScratchException(String message) {
        super(message);
    }

    public ScratchException(int code, String message) {
        super(code, message);
    }

    public ScratchException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
