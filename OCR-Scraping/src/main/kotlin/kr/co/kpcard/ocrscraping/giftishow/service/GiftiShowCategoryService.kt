package kr.co.kpcard.ocrscraping.giftishow.service

import kr.co.kpcard.ocrscraping.common.extension.getPageWaitLoading
import kr.co.kpcard.ocrscraping.common.extension.scrapByXpath
import kr.co.kpcard.ocrscraping.giftishow.enums.GiftiShowScrapEnum
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class GiftiShowCategoryService(
    private val webDriver: WebDriver
) {
    fun scrapCategory(): Map<String, String>{
        webDriver.getPageWaitLoading(GiftiShowScrapEnum.INIT_URL.value, 2000, TimeUnit.MILLISECONDS)
        return webDriver.scrapByXpath(GiftiShowScrapEnum.MAIN_PAGE_CATEGORY_X_PATH.value).map {
            it.text to it.getAttribute("onClick")
        }.toMap().filterNot { it.key== "전체" }
    }
}