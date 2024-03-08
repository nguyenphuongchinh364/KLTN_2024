package vn.shopttcn.validate;

import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.Order;

@Component
public class OrderValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {

	}

	public void validatePhoneNumber(Order order, Errors errors) {
		if (!order.getOrderPhone().equals(GlobalConstant.EMPTY)) {
			if (!Pattern.matches(GlobalConstant.REGEX_PHONE_NUMBER, order.getOrderPhone())) {
				errors.rejectValue("orderPhone", null,
						messageSource.getMessage("formatPhoneError", null, Locale.getDefault()));
			}
		}
	}

}
