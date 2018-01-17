package com.gt.game.core.exception.dragonboat;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class DragonboatException extends SystemException {
    public DragonboatException(String message) {
        super(message);
    }

    public DragonboatException(int code, String message) {
        super(code, message);
    }

    public DragonboatException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
