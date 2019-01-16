
package com.damon.command.macro;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoker {

    private List<Command> commandList = Lists.newArrayList();

    public void addCommand(Command command) {
        commandList.add(command);
    }

    public void removeCommand(Command command) {
        commandList.remove(command);
    }

    public void execute() {
        if(CollectionUtils.isEmpty(commandList)) {
            return;
        }
        commandList.stream().forEach(command -> command.execute());
    }

}