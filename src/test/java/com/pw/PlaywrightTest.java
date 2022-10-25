package com.pw;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlaywrightTest {

    @Test
    public void firstPlaywrightScript() {
        try (Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch();
            Page page = browser.newPage();
            page.navigate("https://playwright.dev/");
            System.out.println(page.title());
        }
    }

    @Test
    public void firstPlaywrightScriptRefactored() {
        try (Playwright playwright = Playwright.create()) {
            Page page = playwright.chromium().launch().newPage();
            page.navigate("https://playwright.dev/");
            System.out.println(page.title());
        }
    }

    @Test
    public void browserSupport() {
        try (Playwright pw = Playwright.create()) {
//            Java 9 and above:
//            List<BrowserType> browserType = List.of(pw.chromium(), pw.firefox())

//            Java 8 and above:
            ArrayList<BrowserType> browserTypes = new ArrayList<>();
            browserTypes.add(pw.chromium());
            browserTypes.add(pw.firefox());

//            BrowserType[] a = {pw.chromium(), pw.firefox()};

            for (BrowserType type : browserTypes) {
                Page page = type.launch().newPage();
                page.navigate("https://www.whatsmybrowser.org/");
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(type.name() + ".png")));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "msedge"})
    public void parametrizedBrowserChannels(String channel) {
        try (Playwright pw = Playwright.create()) {
            Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions().setChannel(channel).setHeadless(false).setSlowMo(1000));
            Page page = browser.newPage();
            page.navigate("https://www.whatsmybrowser.org/");
            Locator details = page.locator(".header");
            System.out.println(details.first().innerText());
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(channel + ".png")));
        }
    }

    @Test
    public void monitorHttpTraffic() {
        Page page = Playwright.create().chromium().launch().newPage();
//        page.onRequest(req -> System.out.println(">>> " + req.method() + ": " + req.url()));
//        page.onResponse(resp -> {
//            System.out.println("<<< " + resp.status() + ":");
//            System.out.println(resp.text());
//        });

// Approach 1
        List<Integer> statuses = new ArrayList<>();
        page.onResponse(resp -> statuses.add(resp.status()));
        page.navigate("https://playwright.dev/");
        System.out.println(statuses);
        boolean unexpectedStatus = statuses.stream().anyMatch(status -> status < 200 || status >= 300);
        Assertions.assertFalse(unexpectedStatus);

// Approach 2
        List<Boolean> results = new ArrayList<>();
        page.onResponse(resp -> results.add(resp.ok()));
        page.navigate("https://playwright.dev/");
        System.out.println(results);
        Assertions.assertTrue(results.stream().allMatch(result -> result));
    }
}
