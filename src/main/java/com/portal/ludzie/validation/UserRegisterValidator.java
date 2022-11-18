package com.portal.ludzie.validation;

import com.portal.ludzie.contants.Constants;
import com.portal.ludzie.model.User;
import com.portal.ludzie.utils.Utils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserRegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return User.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User u = (User) obj;

        ValidationUtils.rejectIfEmpty(errors, "name", "error.userName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "error.userLastName.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "error.userPassword.empty");

        if (!u.getEmail().equals(null)) {
            boolean isMatch = Utils.checkEmailOrPassword(Constants.EMAIL_PATTERN, u.getEmail());
            if (!isMatch) {
                errors.rejectValue("email", "error.userEmailIsNotMatch");
            }
        }

        if (!u.getPassword().equals(null)) {
            boolean isMatch = Utils.checkEmailOrPassword(Constants.PASSWORD_PATTERN, u.getPassword());
            if (!isMatch) {
                errors.rejectValue("password", "error.userPasswordIsNotMatch");
            }
        }
    }

    public void validateEmailExist(User user, Errors errors) {
        if (user != null) {
            errors.rejectValue("email", "error.userEmailExist");
        }
    }
}
