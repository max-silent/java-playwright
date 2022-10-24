package com.pw;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class TestWithContext extends ScriptBase {

    @Test
    public void playWrightTestWithContext() {
            page.navigate(home);
            Assertions.assertEquals(page.title(), "Home Page");
    }

    @Test
    public void secondPlayWrightTestWithContext() {
            page.navigate(home);
            String content = page.content();
            Assertions.assertTrue(content.contains("Cat In The Bag"));
    }
}
