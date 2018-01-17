package com.gt.game.core.exception.ninelattice;


import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.common.exception.SystemException;

/**
 * Created by zwq on 2017/12/24 0020.
 *
 */
public class NinelatticeException extends SystemException {
    public NinelatticeException(String message) {
        super(message);
    }

    public NinelatticeException(int code, String message) {
        super(code, message);
    }

    public NinelatticeException(ResponseEnums responseEnums) {
        super(responseEnums);
    }
}
