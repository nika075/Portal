package com.portal.ludzie.validation;

import com.portal.ludzie.contants.Constants;
import com.portal.ludzie.model.User;
import com.portal.ludzie.utils.Utils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ChangePasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return User.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.userPassword.empty");
    }

    public void checkPassword(String newPass, Errors errors) {
        if (!newPass.equals(null)) {
            boolean isMatch = Utils.checkEmailOrPassword(Constants.PASSWORD_PATTERN, newPass);
            if (!isMatch) {
                errors.rejectValue("newPassword", "error.userPasswordIsNotMatch");
            }
        }
    }
}
