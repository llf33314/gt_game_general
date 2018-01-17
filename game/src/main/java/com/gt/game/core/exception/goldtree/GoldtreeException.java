package com.gt.game.core.exception.goldtree;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class GoldtreeException extends SystemException {
    public GoldtreeException(String message) {
        super(message);
    }

    public GoldtreeException(int code, String message) {
        super(code, message);
    }

    public GoldtreeException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
