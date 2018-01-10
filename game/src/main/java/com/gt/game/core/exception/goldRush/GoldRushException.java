package com.gt.game.core.exception.goldRush;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class GoldRushException extends SystemException {
    public GoldRushException(String message) {
        super(message);
    }

    public GoldRushException(int code, String message) {
        super(code, message);
    }

    public GoldRushException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
