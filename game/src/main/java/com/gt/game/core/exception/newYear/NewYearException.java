package com.gt.game.core.exception.newYear;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class NewYearException extends SystemException {
    public NewYearException(String message) {
        super(message);
    }

    public NewYearException(int code, String message) {
        super(code, message);
    }

    public NewYearException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
