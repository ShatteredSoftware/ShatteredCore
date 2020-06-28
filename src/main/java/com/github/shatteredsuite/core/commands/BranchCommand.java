package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.commands.responses.CancelResponse;
import com.github.shatteredsuite.core.commands.predicates.ChildPredicate;
import com.github.shatteredsuite.core.commands.predicates.CommandContext;
import com.github.shatteredsuite.core.commands.predicates.SenderPermissionPredicate;
import com.github.shatteredsuite.core.messages.Messageable;
import org.jetbrains.annotations.NotNull;

public abstract class BranchCommand extends WrappedCommand {
    public BranchCommand(Messageable instance, WrappedCommand parent, String label, String permission, String helpPath) {
        super(instance, parent, label, permission, helpPath);
        contextPredicates.put("permission", new SenderPermissionPredicate());
        contextPredicates.put("children", new ChildPredicate(new CancelResponse(helpPath)));
    }

    protected void execute(@NotNull CommandContext context) {
        WrappedCommand child = this.children.get(context.args[0]);
        child.run(context.nextLevel(child, this.getLabel()));
    }
}
