package com.gt.game.core.exception.qixi;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class QixiException extends SystemException {
    public QixiException(String message) {
        super(message);
    }

    public QixiException(int code, String message) {
        super(code, message);
    }

    public QixiException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
