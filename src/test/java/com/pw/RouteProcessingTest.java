package com.pw;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class RouteProcessingTest extends ScriptBase {

    @Test
    public void imageBlockingRoute(){
//        page.route("**/*.{png,jpg,jpeg,svg}", route -> route.abort());
        page.route("**/*.{png,jpg,jpeg,svg}", Route::abort);

        page.navigate("https://playwright.dev/");

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("blockedImage.png")));
    }

    @Test
    public void jsBlockingRoute(){
        page.route("**/*.{js}", Route::abort);

        page.navigate("https://playwright.dev/");
        Assertions.assertFalse(page.isEnabled(".toggleButton_gllP"));

        String theme = page.getAttribute("html", "data-theme");
        Assertions.assertEquals("light", theme);

        page.click(".toggleButton_gllP", new Page.ClickOptions().setForce(true));

        String theme2 = page.getAttribute("html", "data-theme");
        Assertions.assertEquals("light", theme2);
    }
}
