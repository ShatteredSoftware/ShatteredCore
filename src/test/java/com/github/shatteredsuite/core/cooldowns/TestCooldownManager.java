package com.github.shatteredsuite.core.cooldowns;

import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class TestCooldownManager {

    private CooldownManager cooldownManager = new CooldownManager(100);
    private UUID uuid = UUID.randomUUID();

    @Test
    public void testUnknown() {
        cooldownManager.reset(uuid); // Unknown test order makes this required.
        Assert.assertTrue("Unknown UUIDs should be able to use the feature.", cooldownManager.canUse(uuid));
        Assert.assertEquals("Unknown UUIDs should have no time until use.", 0, cooldownManager.timeUntilUse(uuid));
    }

    @Test
    public void testKnown() {
        cooldownManager.use(uuid);
        Assert.assertFalse("Known UUIDs that have just used the feature should not be able to use it.",
            cooldownManager.canUse(uuid));
    }
}
