package com.pw;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ScriptBase {
    protected String home = "file:///" + System.getProperty("user.dir") + "\\src\\web\\home.html";

    protected static Playwright playwright;
    protected static Browser browser;

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static public void setup(){
        playwright = Playwright.create();
//        browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
    }

    @AfterAll
    static public void cleanUp(){
//        browser.close();
        playwright.close();
    }

    @BeforeEach
    public void openBrowser(){
        context = browser.newContext();
        page = context.newPage();
        page.setViewportSize(1920, 1080);
    }

    @AfterEach
    public void closeBrowser(){
//        page.close();
        context.close();
    }

}
