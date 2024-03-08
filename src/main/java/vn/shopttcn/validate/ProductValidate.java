package vn.shopttcn.validate;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.shopttcn.model.Product;
import vn.shopttcn.service.ProductService;

@Component
public class ProductValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {

	}

	public void validateCatId(int catId, Errors errors) {
		if (catId == 0) {
			errors.rejectValue("cat", null, messageSource.getMessage("noSelectedCat", null, Locale.getDefault()));
		}
	}

	// validate trùng productName khi insert
	public void validateAddProduct(Product product, Errors errors) {
		if (productService.findByName(product.getProductName()) != null) {
			errors.rejectValue("productName", null,
					messageSource.getMessage("duplicateProductError", null, Locale.getDefault()));
		}
	}

	// validate trùng productName khi update
	public void validateUpdateProduct(Product product, Errors errors) {
		Product productByName = productService.findByName(product.getProductName());
		if (productByName != null) {
			if (productByName.getProductId() != product.getProductId()) {
				errors.rejectValue("productName", null,
						messageSource.getMessage("duplicateProductError", null, Locale.getDefault()));
			}
		}
	}

}
