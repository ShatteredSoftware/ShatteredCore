package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.commands.predicates.*;
import com.github.shatteredsuite.core.commands.responses.CancelResponse;
import com.github.shatteredsuite.core.messages.Messageable;
import com.github.shatteredsuite.core.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;

public abstract class ParameterizedBranchCommand extends WrappedCommand {
    public ParameterizedBranchCommand(Messageable instance, WrappedCommand parent, String label, String permission, String helpPath) {
        super(instance, parent, label, permission, helpPath);
        contextPredicates.put("args", new ArgumentMinimumPredicate(new CancelResponse("not-enough-args"), 2));
        contextPredicates.put("children", new IndexedChildPredicate(new CancelResponse(helpPath), 1));
    }

    @Override
    protected void execute(@NotNull CommandContext context) {
        children.get(context.args[1]).execute(new CommandContext(children.get(context.args[1]), context.sender,
                context.label + context.args[1], ArrayUtil.withoutIndex(context.args, 1),
                context.messenger, context.cancelled, context.args[0]));
    }
}
