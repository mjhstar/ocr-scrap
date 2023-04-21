package kr.co.kpcard.ocrscraping.gifticon.service

import kr.co.kpcard.ocrscraping.common.extension.customClick
import kr.co.kpcard.ocrscraping.common.extension.scrapByXpath
import kr.co.kpcard.ocrscraping.gifticon.enums.GifticonScrapEnum
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service

@Service
class GifticonBrandService(
    private val webDriver: WebDriver
) {
    fun scrapBrand(categoryNameToUrl: Map<String, String>): Map<String, List<String>> {
        return categoryNameToUrl.map { (categoryName, url) ->
            webDriver.customClick(url)
            val brandItems = webDriver.scrapByXpath(GifticonScrapEnum.CATEGORY_PAGE_BRAND_X_PATH.value)
            categoryName to brandItems.map {
                it.getAttribute("href")
            }
        }.toMap().filter { it.value.isNotEmpty() }
    }
}