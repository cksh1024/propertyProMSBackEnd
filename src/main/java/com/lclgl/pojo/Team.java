package com.lclgl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cksh
 * @create 2020-12-15 22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    private int teamId;

    private String teamType;

    private String teamName;

}
