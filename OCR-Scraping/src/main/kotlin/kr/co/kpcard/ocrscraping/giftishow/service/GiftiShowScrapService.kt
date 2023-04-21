package kr.co.kpcard.ocrscraping.giftishow.service

import kr.co.kpcard.ocrscraping.common.excel.model.ProductInfoDto
import kr.co.kpcard.ocrscraping.common.excel.service.ExcelService
import kr.co.kpcard.ocrscraping.common.extension.getPageWaitLoading
import kr.co.kpcard.ocrscraping.giftishow.enums.GiftiShowScrapEnum
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class GiftiShowScrapService(
    private val webDriver: WebDriver,
    private val excelService: ExcelService,
    private val giftiShowCategoryService: GiftiShowCategoryService,
    private val gifshowProductService: GiftiShowProductService
) {
    fun scrap() {
        webDriver.getPageWaitLoading(GiftiShowScrapEnum.INIT_URL.value, 2000, TimeUnit.MILLISECONDS)
        val categoryNameToUrl = giftiShowCategoryService.scrapCategory()
        val giftiShowProductInfos = gifshowProductService.scrapProduct(categoryNameToUrl)
        val productInfo = ProductInfoDto.createByGiftiShow(giftiShowProductInfos)
        excelService.create(productInfo)
    }

}