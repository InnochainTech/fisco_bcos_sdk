package inno.fisco.bcos.be.util.validate;


import inno.fisco.bcos.be.entity.usesign.ReqVo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author peifeng
 * @date 2021/9/16 18:22
 */
public class ValidateUtils {
	/**
	 * 校验属性
	 * @param requestVo
	 * @return
	 */
	public static ErrorInfos validate(ReqVo reqVo) {
		// 1、使用【默认配置】得到一个校验工厂  这个配置可以来自于provider、SPI提供
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		// 2、得到一个校验器
		Validator validator = validatorFactory.getValidator();
		// 3、校验Java Bean（解析注解） 返回校验结果
		Set<ConstraintViolation<ReqVo>> result = validator.validate(reqVo);
		// 输出校验结果
		ErrorInfos errorInfos = new ErrorInfos();
		List<ErrorInfo> errors = new ArrayList<>();
		result.forEach(v->{
			ErrorInfo error = new ErrorInfo();
			error.setPropertyName(v.getPropertyPath().toString());
			error.setMessage(v.getMessage());
			error.setValue(v.getInvalidValue().toString());
			errors.add(error);
		});
		errorInfos.setOrderId(reqVo.getOrderId());
		errorInfos.setErrors(errors);
		return errorInfos;
	}

}
