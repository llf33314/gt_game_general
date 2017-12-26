package com.gt.game.common.base;

import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.exception.ResponseEntityException;
import com.gt.game.core.bean.url.MobileUrlReq;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * BaseController
 *
 * @author psr
 * @create 2017/7/10
 */
public abstract class BaseController {
    /**
     * 日志
     */
    protected static final Logger logger = Logger.getLogger( BaseController.class );

    /**
     * 参数校验是否合法
     *
     * @param result BindingResult
     */
    protected void InvalidParameter( BindingResult result ) {
        System.out.println(result+"==============");
        if ( result.hasErrors() ) {
            List< ObjectError > errorList = result.getAllErrors();
            for ( ObjectError error : errorList ) {
                logger.warn( error.getDefaultMessage() );
                throw new ResponseEntityException( error.getDefaultMessage() );
            }
        }
    }

    protected abstract ResponseDTO getMobileUrl(MobileUrlReq mobileUrlReq, HttpServletRequest request);

}
