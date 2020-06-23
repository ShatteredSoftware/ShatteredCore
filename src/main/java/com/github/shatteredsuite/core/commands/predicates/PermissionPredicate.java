package com.github.shatteredsuite.core.commands.predicates;

public class PermissionPredicate extends CommandContextPredicate {
    public PermissionPredicate(PredicateResponse response) {
        super(response);
    }

    public PermissionPredicate() {
        super(new CancelResponse("no-permission"));
    }

    @Override
    public boolean test(CommandContext context) {
        context.contextMessages.put("permission", context.command.getPermission());
        return context.command.hasPerms(context.sender);
    }
}
