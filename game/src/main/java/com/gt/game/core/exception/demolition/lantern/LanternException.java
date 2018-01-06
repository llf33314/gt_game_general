package com.gt.game.core.exception.demolition.lantern;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class LanternException extends SystemException {
    public LanternException(String message) {
        super(message);
    }

    public LanternException(int code, String message) {
        super(code, message);
    }

    public LanternException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
