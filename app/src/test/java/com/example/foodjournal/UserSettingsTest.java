package com.example.foodjournal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserSettingsTest {
    @Test
    public void verify_UserSettings() {
        UserSettings user1 = new UserSettings("Michael", "mike@email.com", true, "weekly");

        assertEquals("Michael", user1.name);
        assertEquals("mike@email.com", user1.email);
        assertEquals(true, user1.sendReport);
        assertEquals("weekly", user1.frequency);
    }
}
