package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.commands.predicates.CancelResponse;
import com.github.shatteredsuite.core.commands.predicates.ChildPresentPredicate;
import com.github.shatteredsuite.core.commands.predicates.CommandContext;
import com.github.shatteredsuite.core.commands.predicates.PermissionPredicate;
import com.github.shatteredsuite.core.messages.Messageable;
import org.jetbrains.annotations.NotNull;

public abstract class BranchCommand extends WrappedCommand {
    public BranchCommand(Messageable instance, WrappedCommand parent, String label, String permission, String helpPath) {
        super(instance, parent, label, permission, helpPath);
        contextPredicates.put("permission", new PermissionPredicate());
        contextPredicates.put("children", new ChildPresentPredicate(new CancelResponse(helpPath)));
    }

    protected void execute(@NotNull CommandContext context) {
        WrappedCommand child = this.children.get(context.args[0]);
        child.run(context.nextLevel(child, this.getLabel()));
    }
}
