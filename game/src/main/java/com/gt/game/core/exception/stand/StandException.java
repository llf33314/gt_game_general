package com.gt.game.core.exception.stand;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class StandException extends SystemException {
    public StandException(String message) {
        super(message);
    }

    public StandException(int code, String message) {
        super(code, message);
    }

    public StandException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
