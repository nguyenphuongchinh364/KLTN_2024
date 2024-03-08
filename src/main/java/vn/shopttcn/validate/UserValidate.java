package vn.shopttcn.validate;

import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.User;
import vn.shopttcn.service.UserService;
import vn.shopttcn.util.FileUtil;

@Component
public class UserValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {

	}

	// validate: username, email, phone
	public void validate(User user, User oldUser, Errors errors) {
		if (oldUser == null) {
			oldUser = new User(GlobalConstant.EMPTY, GlobalConstant.EMPTY, GlobalConstant.EMPTY);
		}
		if (!user.getUsername().equals(GlobalConstant.EMPTY)) {
			// trùng username
			if (userService.findUserDuplicate(user, oldUser, "username") != null) {
				errors.rejectValue("username", null,
						messageSource.getMessage("duplicateUsernameError", null, Locale.getDefault()));
			}
			// định dạng username
			if (!Pattern.matches(GlobalConstant.REGEX_USERNAME, user.getUsername())) {
				errors.rejectValue("username", null,
						messageSource.getMessage("formatUsernameError", null, Locale.getDefault()));
			}
		}
		if (!user.getUserEmail().equals(GlobalConstant.EMPTY)) {
			// trùng email
			if (userService.findUserDuplicate(user, oldUser, "email") != null) {
				errors.rejectValue("userEmail", null,
						messageSource.getMessage("duplicateEmailError", null, Locale.getDefault()));
			}
		}
		if (!user.getUserPhone().equals(GlobalConstant.EMPTY)) {
			// trùng số điện thoại
			if (userService.findUserDuplicate(user, oldUser, "phone") != null) {
				errors.rejectValue("userPhone", null,
						messageSource.getMessage("duplicatePhoneError", null, Locale.getDefault()));
			}
			// validate định dạng số điện thoại
			if (!Pattern.matches(GlobalConstant.REGEX_PHONE_NUMBER, user.getUserPhone())) {
				errors.rejectValue("userPhone", null,
						messageSource.getMessage("formatPhoneError", null, Locale.getDefault()));
			}
		}
		// không chọn role
		if (user.getRole().getRoleId() == 0) {
			errors.rejectValue("role", null,
					messageSource.getMessage("notSelectedRoleError", null, Locale.getDefault()));
		}
		// thêm user có role là admin
		if (user.getRole().getRoleId() == GlobalConstant.ROLE_ADMIN) {
			errors.rejectValue("role", null,
					messageSource.getMessage("noRightAddRoleAdminError", null, Locale.getDefault()));
		}
	}

	// user: user sau update, oldUser: user trước update, oldPassword: pass cũ
	// confirmPassword: xác nhận pass
	public void validatePassword(User user, Errors errors, User oldUser, String confirmPassword, String oldPassword,
			Model model) {
		if (!user.getPassword().equals(GlobalConstant.EMPTY)) {
			// validate định dạng password
			if (!Pattern.matches(GlobalConstant.REGEX_PASSWORD, user.getPassword())) {
				errors.rejectValue("password", null,
						messageSource.getMessage("formatPasswordError", null, Locale.getDefault()));
			}
			if (oldUser != null) {
				// trùng password cũ
				if (bCryptPasswordEncoder.matches(user.getPassword(), oldUser.getPassword())) {
					errors.rejectValue("password", null,
							messageSource.getMessage("duplicatePasswordError", null, Locale.getDefault()));
				}
			}
			if (confirmPassword.equals(GlobalConstant.EMPTY)) {
				// reject password để bắt lỗi, vì User không có thuộc tính confirmPassword
				errors.rejectValue("password", null, GlobalConstant.EMPTY);
				model.addAttribute("confirmPasswordError",
						messageSource.getMessage("emptyConfirmPasswordError", null, Locale.getDefault()));
			} else {
				// confirmPassword không giống password
				if (!user.getPassword().equals(confirmPassword)) {
					errors.rejectValue("password", null, GlobalConstant.EMPTY);
					model.addAttribute("confirmPasswordError",
							messageSource.getMessage("confirmPasswordIncorrectError", null, Locale.getDefault()));
				}
			}
		} else {
			errors.rejectValue("password", null,
					messageSource.getMessage("emptyPasswordError", null, Locale.getDefault()));
		}
		// validate password cũ
		if (oldPassword != null) {
			if (oldPassword.equals(GlobalConstant.EMPTY)) {
				errors.rejectValue("password", null, GlobalConstant.EMPTY);
				model.addAttribute("oldPasswordError",
						messageSource.getMessage("emptyPasswordError", null, Locale.getDefault()));
			} else {
				// password cũ không giống với password trong database
				if (!bCryptPasswordEncoder.matches(oldPassword, oldUser.getPassword())) {
					errors.rejectValue("password", null, GlobalConstant.EMPTY);
					model.addAttribute("oldPasswordError",
							messageSource.getMessage("oldPasswordIncorrectError", null, Locale.getDefault()));
				}
			}
		}
	}

	// validate: avatar
	public void validatePicture(MultipartFile multipartFile, Errors errors) {
		String fileName = multipartFile.getOriginalFilename();
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			if (!FileUtil.checkFileExtension(fileName)) {
				errors.rejectValue("avatar", null,
						messageSource.getMessage("fileExtensionError", null, Locale.getDefault()));
			}
		}
	}

}
