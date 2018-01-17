package com.gt.game.core.exception.tree;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class TreeException extends SystemException {
    public TreeException(String message) {
        super(message);
    }

    public TreeException(int code, String message) {
        super(code, message);
    }

    public TreeException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
