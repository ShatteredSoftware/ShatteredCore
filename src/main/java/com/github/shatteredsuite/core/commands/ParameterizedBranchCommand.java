package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate;
import com.github.shatteredsuite.core.commands.predicates.CommandContext;
import com.github.shatteredsuite.core.commands.predicates.IndexedChildPredicate;
import com.github.shatteredsuite.core.commands.responses.CancelResponse;
import com.github.shatteredsuite.core.messages.Messageable;
import com.github.shatteredsuite.core.util.ArrayUtil;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ParameterizedBranchCommand extends WrappedCommand {
    public ParameterizedBranchCommand(Messageable instance, WrappedCommand parent, String label, String permission, String helpPath) {
        super(instance, parent, label, permission, helpPath);
        contextPredicates.put("args", new ArgumentMinimumPredicate(new CancelResponse("not-enough-args"), 2));
        contextPredicates.put("children", new IndexedChildPredicate(new CancelResponse(helpPath), 1));
    }

    @Override
    protected void execute(@NotNull CommandContext context) {
        children.get(context.args[1]).execute(flippedContext(context));
    }

    private @NotNull
    CommandContext flippedContext(@NotNull CommandContext context) {
        return new CommandContext(children.get(context.args[1]), context.sender,
                context.label + context.args[1], ArrayUtil.withoutIndex(context.args, 1).toArray(new String[]{}),
                context.messenger, context.cancelled, context.args[0]);
    }

    @Override
    public List<String> onTabComplete(CommandContext ctx) {
        if (ctx.args.length <= 1) {
            List<String> res = new ArrayList<>();
            StringUtil.copyPartialMatches(ctx.args[0], provideCompletions(ctx), res);
            return res;
        }
        if (ctx.args.length == 2) {
            List<String> res = new ArrayList<>();
            StringUtil.copyPartialMatches(ctx.args[1], this.children.keySet(), res);
            return res;
        }
        else {
            WrappedCommand child = this.children.get(ctx.args[1]);
            if (child == null) {
                return Collections.emptyList();
            }
            return child.onTabComplete(flippedContext(ctx).nextLevel(child));
        }
    }

    protected List<String> provideCompletions(CommandContext ctx) {
        return Collections.emptyList();
    }
}
