package kr.co.kpcard.ocrscraping.common.config

import org.openqa.selenium.WebDriver
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component

@Component
class ShutdownEvent(
    private val driver: WebDriver
) : ApplicationListener<ContextClosedEvent> {
    override fun onApplicationEvent(event: ContextClosedEvent) {
        driver.close()
    }
}