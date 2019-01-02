package com.damon.simple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：请求方
 *
 * @author Damon
 * @since 2019-01-02 11:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoker {

    private Command command;

    public void execute() {
        command.execute();
    }

}