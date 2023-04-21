package kr.co.kpcard.ocrscraping.kakao.service

import kr.co.kpcard.ocrscraping.common.config.LoggingCompanion
import kr.co.kpcard.ocrscraping.common.extension.scrapByClassName
import kr.co.kpcard.ocrscraping.kakao.enums.KakaoScrapEnum
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service

@Service
class KakaoCategoryService(
    private val webDriver: WebDriver
) {
    fun scrapCategory(): Map<String, String> {
        val webElements = webDriver.scrapByClassName(KakaoScrapEnum.MAIN_PAGE_CATEGORY_CLASS_NAME.value)
        return webElements.associate {
            val categoryName = it.getAttribute(KakaoScrapEnum.MAIN_PAGE_CATEGORY_DATA_NAME_TAG.value)
            val categoryNumber = it.getAttribute(KakaoScrapEnum.MAIN_PAGE_CATEGORY_URL_TAG.value)
            val categoryUrl: String = KakaoScrapEnum.CATEGORY_URL.value + categoryNumber
            categoryName to categoryUrl
        }
    }

    companion object : LoggingCompanion()
}