package kr.co.kpcard.ocrscraping.common.scrap

import kr.co.kpcard.ocrscraping.common.extension.getHeight
import kr.co.kpcard.ocrscraping.common.extension.scrollDown
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service


@Service
class ScrapService(
    private val webDriver: WebDriver
) {
    fun scrollDown() {
        var lastHeight = webDriver.getHeight()
        var newHeight = 0L
        while (true) {
            webDriver.scrollDown()
            newHeight = webDriver.getHeight()
            if (newHeight == lastHeight)
                break
            lastHeight = newHeight
        }
    }
}