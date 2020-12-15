package com.lclgl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cksh
 * @create 2020-12-15 22:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userId;

    private String password;

}
