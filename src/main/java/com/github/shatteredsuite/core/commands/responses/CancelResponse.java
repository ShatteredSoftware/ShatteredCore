package com.github.shatteredsuite.core.commands.responses;

import com.github.shatteredsuite.core.commands.predicates.CommandContext;
import org.jetbrains.annotations.Nullable;

public class CancelResponse extends PassResponse {
    private final String messageId;

    public CancelResponse(@Nullable String messageId) {
        this.messageId = messageId;
    }

    @Override
    public CommandContext onFailure(CommandContext context) {
        if (messageId != null) {
            context.messenger.sendErrorMessage(context.sender, messageId, context.contextMessages);
        }
        return context.cancelled();
    }
}
