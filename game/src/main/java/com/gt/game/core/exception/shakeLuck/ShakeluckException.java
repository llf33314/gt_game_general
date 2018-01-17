package com.gt.game.core.exception.shakeLuck;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class ShakeluckException extends SystemException {
    public ShakeluckException(String message) {
        super(message);
    }

    public ShakeluckException(int code, String message) {
        super(code, message);
    }

    public ShakeluckException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
