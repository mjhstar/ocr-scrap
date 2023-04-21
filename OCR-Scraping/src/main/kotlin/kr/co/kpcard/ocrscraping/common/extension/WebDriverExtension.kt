package kr.co.kpcard.ocrscraping.common.extension

import kr.co.kpcard.ocrscraping.common.config.ChromeDriverStart.Companion.logger
import kr.co.kpcard.ocrscraping.common.util.ScrapUtil
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

private const val HEIGHT = "return document.body.scrollHeight;"
private const val SCROLL_BOTTOM = "window.scrollTo(0, document.body.scrollHeight);"


inline fun <R> WebDriver.processing(url: String, block: () -> R): R {
    this.waitLoading(2000, TimeUnit.MILLISECONDS)
    ScrapUtil.openNewTab(this, url)
    val tab1 = this.windowHandles.toList()[0]
    val tab2 = this.windowHandles.toList()[1]
    this.switchTo().window(tab2).navigate()

    val blockResult = block()

    this.close()
    this.switchTo().window(tab1).navigate()
    return blockResult
}

inline fun <R> WebDriver.handleException(contextName: String, block: (WebDriver) -> R): R? {
    return try {
        block(this)
    } catch (e: Exception) {
        try {
            logger.error(
                """
                $contextName ::: ${this.currentUrl}
            """.trimIndent()
            )
        } catch (e: Exception) {
            logger.error(e.message)
        }
        null
    }
}

fun WebDriver.getCurrentUrlOrEmpty(): String {
    return try {
        this.currentUrl
    } catch (e: Exception) {
        ""
    }
}

fun WebDriver.getPageWaitLoading(url: String, time: Long, timeUnit: TimeUnit) {
    this.waitLoading(time, timeUnit)
    this.get(url)
}

fun WebDriver.waitLoading(time: Long, timeUnit: TimeUnit) {
    this.manage().timeouts().implicitlyWait(time, timeUnit)
}

fun WebDriver.scrapByXpath(name: String): List<WebElement> =
    this.findElements(By.xpath(name))

fun WebDriver.scrapByClassName(name: String): List<WebElement> =
    this.findElements(By.className(name))

fun WebDriver.scrapByTagName(name: String): List<WebElement> =
    this.findElements(By.tagName(name))

fun WebElement.getElementByTagName(name: String): WebElement =
    this.findElement(By.tagName(name))

fun WebDriver.findElementByXpath(name: String): WebElement =
    this.findElement(By.xpath(name))

fun WebDriver.findElementByClassName(name: String): WebElement =
    this.findElement(By.className(name))

fun WebDriver.findElementById(name: String): WebElement =
    this.findElement(By.id(name))

fun WebDriver.getHeight(): Long {
    return (this as JavascriptExecutor).executeScript(HEIGHT) as Long
}

fun WebDriver.scrollDown() {
    (this as JavascriptExecutor).executeScript(SCROLL_BOTTOM)
    sleep(1000)
}

fun WebDriver.customClick(command: String) {
    (this as JavascriptExecutor).executeScript(command)
    sleep(500)
}

fun WebElement.customClick(time: Long) {
    this.click()
    sleep(time)
}

