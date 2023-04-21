package kr.co.kpcard.ocrscraping.common.config

import org.apache.commons.lang3.exception.ExceptionUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException

@Component
class ChromeDriverStart(
    private val envProperties: EnvProperties
) {
    @Bean
    fun createWebDriver(): WebDriver {
        val driverFile = File(envProperties.chromeDriverPath)
        val chromeDriverService = ChromeDriverService.Builder()
            .usingDriverExecutable(driverFile)
            .usingAnyFreePort()
            .build()
        val chromeOptions = ChromeOptions()

        if (envProperties.isHide) {
            chromeOptions.addArguments("--headless")
            chromeOptions.addArguments("--no-sandbox")
        }
        try {
            chromeDriverService.start()
        } catch (ioException: IOException) {
            logger.error(ExceptionUtils.getStackTrace(ioException))
        }
        return ChromeDriver(chromeDriverService, chromeOptions)
    }

    companion object : LoggingCompanion()
}