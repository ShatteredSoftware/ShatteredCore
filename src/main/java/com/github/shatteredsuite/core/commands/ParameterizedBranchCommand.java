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
    protected void execute(@NotNull CommandContext ctx) {
        if(ctx.debug) {
            ctx.sender.sendMessage(this.getLabel() + " (Parameterized Branch) -> "
                    + ctx.args[1] + " with argument " + ctx.args[0]);
        }
        children.get(ctx.args[1]).execute(flippedContext(ctx));
    }

    private @NotNull
    CommandContext flippedContext(@NotNull CommandContext ctx) {
        return new CommandContext(children.get(ctx.args[1]), ctx.sender,
                ctx.label + ctx.args[1], ArrayUtil.withoutIndex(ctx.args, 1).toArray(new String[]{}),
                ctx.messenger, ctx.cancelled, ctx.args[0]);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandContext ctx) {
        if(hasPerms(ctx.sender)) {
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
        else {
            return Collections.emptyList();
        }
    }

    protected List<String> provideCompletions(CommandContext ctx) {
        return Collections.emptyList();
    }
}
