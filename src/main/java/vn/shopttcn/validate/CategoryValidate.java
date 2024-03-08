package vn.shopttcn.validate;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.shopttcn.model.Category;
import vn.shopttcn.service.CategoryService;

@Component
public class CategoryValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CategoryService categoryService;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {

	}

	// check tên danh mục trùng
	public void validateAddCat(Category category, Errors errors) {
		if (categoryService.findByName(category.getCatName()) != null) {
			errors.rejectValue("catName", null,
					messageSource.getMessage("duplicateCatError", null, Locale.getDefault()));
		}
	}

	// check tên danh mục trùng với các danh mục khác
	public void validateUpdateCat(Category category, Errors errors) {
		Category categoryByName = categoryService.findByName(category.getCatName());
		if (categoryByName != null) {
			if (categoryByName.getCatId() != category.getCatId()) {
				errors.rejectValue("catName", null,
						messageSource.getMessage("duplicateCatError", null, Locale.getDefault()));
			}
		}
	}

}
