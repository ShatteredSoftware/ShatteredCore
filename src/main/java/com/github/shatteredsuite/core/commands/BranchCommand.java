package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.commands.predicates.ChildPredicate;
import com.github.shatteredsuite.core.commands.predicates.CommandContext;
import com.github.shatteredsuite.core.commands.predicates.SenderPermissionPredicate;
import com.github.shatteredsuite.core.commands.responses.CancelResponse;
import com.github.shatteredsuite.core.messages.Messageable;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BranchCommand extends WrappedCommand {
    public BranchCommand(Messageable instance, WrappedCommand parent, String label, String permission, String helpPath) {
        super(instance, parent, label, permission, helpPath);
        contextPredicates.put("permission", new SenderPermissionPredicate());
        contextPredicates.put("children", new ChildPredicate(new CancelResponse(helpPath)));
    }

    protected void execute(@NotNull CommandContext context) {
        WrappedCommand child = this.children.get(context.args[0]);
        child.run(context.nextLevel(child));
    }

    @Override
    public List<String> onTabComplete(CommandContext ctx) {
        // create new array
        final List<String> completions = new ArrayList<>();
        if (ctx.args.length <= 1) {
            StringUtil.copyPartialMatches(ctx.args[0], children.keySet(), completions);
            Collections.sort(completions);
            return completions;
        }
        return children.containsKey(ctx.args[0])
                ? children
                .get(ctx.args[0])
                .onTabComplete(ctx.nextLevel(children.get(ctx.args[0])))
                : null;
    }
}
