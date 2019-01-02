package com.damon.undo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：右转命令
 *
 * @author Damon
 * @since 2019-01-02 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RightCommand implements Command {

    private Receiver receiver;

    @Override
    public void execute() {
        receiver.right();
    }

    @Override
    public void undo() {
        receiver.left();
    }
}
