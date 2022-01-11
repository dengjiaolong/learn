package com.huawei.entity;

import com.huawei.annotation.Phone;
import com.huawei.validgroup.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class User {

    @NotNull(message = "id不能为空",groups = Update.class)
    private Integer id;

    @NotNull(message = "姓名不能为空")
    private String name;

    @Range(min = 0,max = 150,message = "超出正常人的寿命范围(0-150)")
    private Integer age;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Phone(message = "手机格式不能正确")
    private String phoneNo;
    /**
     * 前面已经出现过， User对象中的school属性也是一个对象，如果要想School对象里的name，address上的约束生效，则必须要在school上添加 @Valid注解，
     * 且上文已说过，@Valid支持嵌套校验、而@Validated不支持， 当然此处应该加上一个 @NotNull，避免school对象为null
     */
    @Valid
    @NotNull
    private School school;

    @Data
    private static class School{
        @NotBlank(message = "学校名不能为空")
        private String name;
        @NotBlank(message = "学校地址不能为空")
        private String address;
    }

}
