package com.pw;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class RouteProcessingTest extends ScriptBase {

    @Test
    public void imageBlockingRoute() {
//        page.route("**/*.{png,jpg,jpeg,svg}", route -> route.abort());
        page.route("**/*.{png,jpg,jpeg,svg}", Route::abort);

        page.navigate("https://playwright.dev/");

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("blockedImage.png")));
    }

    @Test
    public void jsBlockingRoute() {
        page.route("**/*.{js}", Route::abort);

        page.navigate("https://playwright.dev/");
        Assertions.assertFalse(page.isEnabled(".toggleButton_gllP"));

        String theme = page.getAttribute("html", "data-theme");
        Assertions.assertEquals("light", theme);

        page.click(".toggleButton_gllP", new Page.ClickOptions().setForce(true));

        String theme2 = page.getAttribute("html", "data-theme");
        Assertions.assertEquals("light", theme2);
    }

    @Test
    public void jsBlockingRoute2() {
        page.route("**/*.{js}", route -> {
                    if (route.request().resourceType().equalsIgnoreCase("script")) {
                        route.abort();
                    } else {
                        route.resume();
                    }
                }
        );

        page.navigate("https://playwright.dev/");
        Assertions.assertFalse(page.isEnabled(".toggleButton_gllP"));

        String theme = page.getAttribute("html", "data-theme");
        Assertions.assertEquals("light", theme);

        page.click(".toggleButton_gllP", new Page.ClickOptions().setForce(true));

        String theme2 = page.getAttribute("html", "data-theme");
        Assertions.assertEquals("light", theme2);
    }

    @Test
    public void responseRouteFulFill() {
        page.route("**/*", route -> route.fulfill(
                new Route.FulfillOptions().setStatus(203).setBody("Intercepted Response!"))
        );

        Response resp = page.navigate("https://playwright.dev/");
        System.out.println(resp.text());
        Assertions.assertEquals(203, resp.status());
        Assertions.assertEquals("Intercepted Response!", resp.text());
    }

    @Test
    public void traceViewer() {
        BrowserContext context = null;
        try {
            context = browser.newContext();
            context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));

            Page page = context.newPage();
            page.navigate("https://playwright.dev/java/");
            page.click("text=Get Started");
            page.click("text=Guides");
            page.click("text=Trace Viewer");
            Assertions.assertTrue(page.isVisible("text=Recording a trace"));
        } finally {
            if (context != null) {
                context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
                // Note: in order to see results in Trace Viewer - drag and drop generated zip to the following page below:
                // https://trace.playwright.dev/
                context.close();
            }
        }
    }
}
