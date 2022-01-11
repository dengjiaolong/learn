package com.huawei.intercepter;

import com.huawei.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

   @Override
   public void initialize(Phone constraint) {

   }

   @Override
   public boolean isValid(String phoneNo, ConstraintValidatorContext context) {
      // 1: 如果用户没输入直接返回不校验，因为空的判断交给@NotNull去做就行了
      if (phoneNo == null || phoneNo.length() == 0) {
         return true;
      }
      Pattern p = Pattern.compile("^(13[0-9]|14[5|7|9]|15[0|1|2|3|5|6|7|8|9]|17[0|1|6|7|8]|18[0-9])\\d{8}$");
      // 2：如果校验通过就返回true,否则返回false;
      Matcher matcher = p.matcher(phoneNo);
      return matcher.matches();
   }
}
