package com.damon.simple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：左转命令
 *
 * @author Damon
 * @since 2019-01-02 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeftCommand implements Command {

    private Receiver receiver;

    @Override
    public void execute() {
        receiver.left();
    }
}
