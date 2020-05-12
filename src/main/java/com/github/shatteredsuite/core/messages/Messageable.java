package com.github.shatteredsuite.core.messages;

/**
 * The interface for a class that can return a Messenger.
 *
 * @see com.github.shatteredsuite.utilities.messages.Messenger
 */
public interface Messageable {
    public Messenger getMessenger();
}
