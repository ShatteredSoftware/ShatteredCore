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
    int argTarget;

    public ParameterizedBranchCommand(Messageable instance, WrappedCommand parent, String label, String permission, String helpPath) {
        this(instance, parent, label, permission, helpPath, 1);
    }

    public ParameterizedBranchCommand(Messageable instance, WrappedCommand parent, String label, String permission, String helpPath, int argTarget) {
        super(instance, parent, label, permission, helpPath);
        contextPredicates.put("args", new ArgumentMinimumPredicate(new CancelResponse("not-enough-args"), argTarget + 1));
        contextPredicates.put("children", new IndexedChildPredicate(new CancelResponse(helpPath), argTarget));
        this.argTarget = argTarget;
    }

    @Override
    protected void execute(@NotNull CommandContext ctx) {
        if(ctx.debug) {
            ctx.sender.sendMessage(this.getLabel() + " (Parameterized Branch) -> "
                    + ctx.args[argTarget] + " with argument " + ctx.args[0]);
        }
        children.get(ctx.args[argTarget]).run(flippedContext(ctx));
    }

    private @NotNull
    CommandContext flippedContext(@NotNull CommandContext ctx) {
        return new CommandContext(children.get(ctx.args[argTarget]), ctx.sender,
                ctx.label + " " + ctx.args[argTarget], ArrayUtil.withoutIndex(ctx.args, argTarget).toArray(new String[]{}),
                ctx.messenger, ctx.cancelled, ctx.args[0]);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandContext ctx) {
        if (ctx.args.length <= argTarget) {
            List<String> res = new ArrayList<>();
            StringUtil.copyPartialMatches(ctx.args[0], provideCompletions(ctx), res);
            return res;
        }
        if (ctx.args.length == argTarget + 1) {
            List<String> res = new ArrayList<>();
            StringUtil.copyPartialMatches(ctx.args[1], this.children.keySet(), res);
            return res;
        }
        else {
            WrappedCommand child = this.children.get(ctx.args[1]);
            if (child == null) {
                return Collections.emptyList();
            }
            return child.tabComplete(flippedContext(ctx).nextLevel(child));
        }
    }

    protected List<String> provideCompletions(CommandContext ctx) {
        return Collections.emptyList();
    }
}
