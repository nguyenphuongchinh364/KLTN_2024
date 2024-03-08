package vn.shopttcn.validate;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.util.FileUtil;

@Component
public class ProductPictureValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {

	}

	// validate image
	public void validatePicture(Errors errors, MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		if (fileName.equals(GlobalConstant.EMPTY)) {
			errors.rejectValue("productImage", null,
					messageSource.getMessage("noImageUploadError", null, Locale.getDefault()));
		} else {
			if (!FileUtil.checkFileExtension(fileName)) {
				errors.rejectValue("productImage", null,
						messageSource.getMessage("fileExtensionError", null, Locale.getDefault()));
			}
		}
	}

	// validate multiple image (product)
	// required: 0 (không bắt buộc), 1 (bắt buộc)
	public void validateMultiplePicture(Errors errors, List<MultipartFile> listMultipartFile, int required) {
		if (required == GlobalConstant.REQUIRED) {
			String fileName = listMultipartFile.get(0).getOriginalFilename();
			if (fileName.equals(GlobalConstant.EMPTY)) {
				errors.rejectValue("pictureName", null,
						messageSource.getMessage("noImageUploadError", null, Locale.getDefault()));
				return;
			}
		}
		if (listMultipartFile.size() > 0) {
			for (MultipartFile multipartFile : listMultipartFile) {
				String fileName = multipartFile.getOriginalFilename();
				if (!fileName.equals(GlobalConstant.EMPTY)) {
					if (!FileUtil.checkFileExtension(fileName)) {
						errors.rejectValue("pictureName", null,
								messageSource.getMessage("fileExtensionError", null, Locale.getDefault()));
						return;
					}
				}
			}
		}
		if (listMultipartFile.size() >= GlobalConstant.MAX_PICTURE_QUANTITY) {
			errors.rejectValue("pictureName", null,
					messageSource.getMessage("quantityPictureError", null, Locale.getDefault()));
		}
	}

}
