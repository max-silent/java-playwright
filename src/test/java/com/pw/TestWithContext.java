package com.pw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    public void jsExecutionEval() {
        page.navigate(home);
        Object obj = page.evaluate("() => window.localStorage.getItem('clapped')");
        Assertions.assertNull(obj);

        page.click("#clap-image");
        String obj2 = (String) page.evaluate("() => window.localStorage.getItem('clapped')");

//        Assertions.assertEquals("true", obj2);
        Assertions.assertTrue(Boolean.parseBoolean(obj2));
    }

    @Test
    public void jsExecutionEvalOnSelector() {
        page.navigate(home);
        page.evalOnSelector("#hero-banner", "selector => selector.remove()");
        Assertions.assertFalse(page.isVisible("#hero-banner"));
    }

    @Test
    public void jsExecutionEvalOnSelectorAll() {
        page.navigate(advantages);
        Object obj = page.evalOnSelectorAll(".feature", "selectors => selectors.length");
        Assertions.assertEquals(3, obj);
    }

}
