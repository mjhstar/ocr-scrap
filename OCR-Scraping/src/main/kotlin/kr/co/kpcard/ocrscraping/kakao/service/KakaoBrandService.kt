package kr.co.kpcard.ocrscraping.kakao.service

import kr.co.kpcard.ocrscraping.common.extension.findElementByXpath
import kr.co.kpcard.ocrscraping.common.extension.processing
import kr.co.kpcard.ocrscraping.common.extension.scrapByClassName
import kr.co.kpcard.ocrscraping.common.extension.scrapByXpath
import kr.co.kpcard.ocrscraping.kakao.enums.KakaoScrapEnum
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class KakaoBrandService(
    private val webDriver: WebDriver
) {
    fun scrapBrand(categoryMap: Map<String, String>): Map<String, List<String>> {
        return categoryMap.map { (categoryName, url) ->
            webDriver.processing(url) {
                val categorySize = webDriver.scrapByXpath(KakaoScrapEnum.CATEGORY_PAGE_SIZE_X_PATH.value)
                val brandItems = mutableListOf<WebElement>()
                categorySize.forEach {
                    brandItems.addAll(
                        webDriver.scrapByXpath(KakaoScrapEnum.CATEGORY_PAGE_BRAND_X_PATH.value).toMutableList()
                    )
                    try {
                        val button =
                            webDriver.findElementByXpath(KakaoScrapEnum.CATEGORY_PAGE_BRAND_PAGE_BUTTON_X_PATH.value)
                        button.click()
                    } catch (_: Exception) {
                    }
                    sleep(500)
                }
                categoryName to brandItems.toSet().map {
                    it.getAttribute("href")
                }
            }
        }.toMap()
    }
}