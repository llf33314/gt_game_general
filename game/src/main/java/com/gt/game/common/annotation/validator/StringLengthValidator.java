package com.gt.game.common.annotation.validator;


import com.gt.game.common.annotation.valid.StringLengthValid;
import com.gt.game.core.util.CommonUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2017/8/16 0016.
 */
public class StringLengthValidator implements ConstraintValidator<StringLengthValid, String> {

	private int len;

	@Override
	public void initialize(StringLengthValid stringLengthValid) {
		this.len = stringLengthValid.length();
	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		if(CommonUtil.isEmpty(str)){
			return true;
		}else{
			if(CommonUtil.getStringLength(str) > len){
				return false;
			}
		}
		return true;
	}
}
