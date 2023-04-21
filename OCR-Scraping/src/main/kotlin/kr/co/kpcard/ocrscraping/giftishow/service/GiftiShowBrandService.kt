package kr.co.kpcard.ocrscraping.giftishow.service

import kr.co.kpcard.ocrscraping.common.extension.customClick
import kr.co.kpcard.ocrscraping.common.extension.scrapByXpath
import kr.co.kpcard.ocrscraping.giftishow.enums.GiftiShowScrapEnum
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service

@Service
class GiftiShowBrandService(
    private val webDriver: WebDriver
) {
    fun scrapBrand(url: String): List<String> {
        webDriver.customClick(url)
        return webDriver.scrapByXpath(GiftiShowScrapEnum.CATEGORY_PAGE_BRAND_X_PATH.value).filter {
            !it.getAttribute("style").contains("none")
        }.filter {
            it.text != "전체"
        }.map { it.getAttribute("onClick") }
    }
}