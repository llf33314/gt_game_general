package com.gt.game.core.exception.loveArrow;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class LoveArrowException extends SystemException {
    public LoveArrowException(String message) {
        super(message);
    }

    public LoveArrowException(int code, String message) {
        super(code, message);
    }

    public LoveArrowException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
