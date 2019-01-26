package com.damon.memento;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-26 15:12
 */
@Data
@AllArgsConstructor
public class Memento {

    //状态维护
    private String state;

}