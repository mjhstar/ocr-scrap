package kr.co.kpcard.ocrscraping.gifticon.service

import kr.co.kpcard.ocrscraping.common.extension.scrapByXpath
import kr.co.kpcard.ocrscraping.gifticon.enums.GifticonScrapEnum
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service

@Service
class GifticonCategoryService(
    private val webDriver: WebDriver
) {
    fun scrapCategory(): Map<String, String> {
        return webDriver.scrapByXpath(GifticonScrapEnum.MAIN_PAGE_CATEGORY_X_PATH.value).map {
            it.text to it.getAttribute("href")
        }.toMap().filterNot { it.key == "전체" || it.key == "할인" }
    }
}