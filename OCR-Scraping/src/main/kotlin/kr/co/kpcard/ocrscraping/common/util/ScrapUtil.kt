package kr.co.kpcard.ocrscraping.common.util

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit

class ScrapUtil(
) {
    companion object {
        var seqNo = 0L
        fun openNewTab(webDriver: WebDriver, url: String) {
            webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS)
            (webDriver as JavascriptExecutor).executeScript("window.open('url')".replace("url", url))

        }
    }
}