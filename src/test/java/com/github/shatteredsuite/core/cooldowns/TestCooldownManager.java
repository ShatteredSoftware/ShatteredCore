package com.github.shatteredsuite.core.cooldowns;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TestCooldownManager {

    private CooldownManager cooldownManager = new CooldownManager(100);
    private UUID uuid = UUID.randomUUID();

    @Test
    public void testUnknown() {
        cooldownManager.reset(uuid); // Unknown test order makes this required.
        Assertions.assertTrue(cooldownManager.canUse(uuid), "Unknown UUIDs should be able to use the feature.");
        Assertions.assertEquals(0, cooldownManager.timeUntilUse(uuid), "Unknown UUIDs should have no time until use.");
    }

    @Test
    public void testKnown() {
        cooldownManager.use(uuid);
        Assertions.assertFalse(cooldownManager.canUse(uuid),
                "Known UUIDs that have just used the feature should not be able to use it.");
    }
}
