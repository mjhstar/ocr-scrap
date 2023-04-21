package kr.co.kpcard.ocrscraping.gifticon.service

import kr.co.kpcard.ocrscraping.common.excel.model.ProductInfoDto
import kr.co.kpcard.ocrscraping.common.excel.service.ExcelService
import kr.co.kpcard.ocrscraping.common.extension.getPageWaitLoading
import kr.co.kpcard.ocrscraping.gifticon.enums.GifticonScrapEnum
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class GifticonScrapService(
    private val webDriver: WebDriver,
    private val excelService: ExcelService,
    private val gifticonBrandService: GifticonBrandService,
    private val gifticonProductService: GifticonProductService,
    private val gifticonCategoryService: GifticonCategoryService
) {
    fun scrap(){
        webDriver.getPageWaitLoading(GifticonScrapEnum.INIT_URL.value, 5000, TimeUnit.MILLISECONDS)
        val categoryNameToUrl = gifticonCategoryService.scrapCategory()
        val categoryNameToBrandUrl = gifticonBrandService.scrapBrand(categoryNameToUrl)
        val gifticonProductInfos = gifticonProductService.scrapProduct(categoryNameToBrandUrl)
        val productInfos = ProductInfoDto.createByGifticon(gifticonProductInfos)
        excelService.create(productInfos)
    }
}