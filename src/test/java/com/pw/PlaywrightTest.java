package com.pw;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

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
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(type.name()+".png")));
            }
        }
    }
}
