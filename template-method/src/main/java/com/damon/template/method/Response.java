package com.damon.template.method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 功能：基本响应
 *
 * @author Damon
 * @since 2019-03-11 18:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String msg;

    private int code;

    private boolean success;

}