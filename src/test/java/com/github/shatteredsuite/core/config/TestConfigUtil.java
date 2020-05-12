package com.github.shatteredsuite.core.config;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.github.shatteredsuite.core.config.ConfigUtil.getIfValid;
import static com.github.shatteredsuite.core.config.ConfigUtil.getInEnum;
import static org.junit.Assert.*;

public class TestConfigUtil {
    HashMap<String, Object> map = new HashMap<>();

    @Before
    public void setUp() {
        map.clear();
        map.put("number", 5);
        map.put("letter", 'c');
        map.put("message", "This is a message.");
        map.put("class", new Random(0));
        map.put("enum", "YELLOW");
        map.put("bad-enum", "GREEN");
    }

    @Test
    public void simpleValidity() {
        int number = getIfValid(map, "number", Integer.class, 9);
        assertEquals(5, number);
        char letter = getIfValid(map, "letter", Character.class, 'b');
        assertEquals('c', letter);
        String message = getIfValid(map, "message", String.class, "This is a different message.");
        assertEquals("This is a message.", message);
    }

    @Test
    public void classValidity() {
        Random random = new Random(0);
        int expected = random.nextInt(200);
        Random res = getIfValid(map, "class", Random.class, new Random(5));
        assertEquals(expected, res.nextInt(200));
    }

    @Test
    public void simpleDefaults() {
        int number = getIfValid(map, "not-a-number", Integer.class, 7);
        assertEquals(7, number);
        number = getIfValid(map, "letter", Integer.class, 9);
        assertEquals(9, number);
    }

    @Test
    public void classDefaults() {
        Random random = new Random(5);
        int expected = random.nextInt(200);
        Random res = getIfValid(map, "not-a-class", Random.class, new Random(5));
        assertEquals(expected, res.nextInt(200));
    }

    @Test
    public void enumTest() {
        TestEnum expected = TestEnum.YELLOW;
        TestEnum res = getInEnum(map, "enum", TestEnum.class, TestEnum.RED);
        assertEquals(expected, res);

        expected = TestEnum.RED;
        res = getInEnum(map , "not-an-enum", TestEnum.class, TestEnum.RED);
        assertEquals(expected, res);

        expected = TestEnum.BLUE;
        res = getInEnum(map, "bad-enum", TestEnum.class, TestEnum.BLUE);
        assertEquals(expected, res);
    }

    @Test
    public void serializerTest() {
        Map<String, Object> serialized = ConfigUtil.reflectiveSerialize(
            new TestClass("Hello!", 17, TestEnum.RED), TestClass.class);

        assertTrue(serialized.containsKey("message"));
        assertEquals("Hello!", serialized.get("message"));
        assertTrue(serialized.containsKey("color-name"));
        assertEquals("RED", serialized.get("color-name"));
        assertFalse(serialized.containsKey("valid"));
    }

    private enum TestEnum {
        RED,
        YELLOW,
        BLUE
    }

    private class TestClass {
        public final String message;
        public final int id;
        public final TestEnum colorName;
        private final boolean valid;

        private TestClass(String message, int id, TestEnum color) {
            this.message = message;
            this.id = id;
            this.colorName = color;
            this.valid = true;
        }
    }
}
