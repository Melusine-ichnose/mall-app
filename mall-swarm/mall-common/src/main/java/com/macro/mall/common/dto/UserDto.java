package com.macro.mall.common.dto;

import lombok.*;

import java.util.List;

/**
 * @author Melusine-ichnose
 * @description 权限框架中使用的用户信息封装类
 * @date 2024/1/30
 * @github https://github.com/Melusine-ichnose
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String clientId;
    private List<String> permissionList;
}
