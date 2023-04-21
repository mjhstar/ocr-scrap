package kr.co.kpcard.ocrscraping.kakao.service

import kr.co.kpcard.ocrscraping.common.config.LoggingCompanion
import kr.co.kpcard.ocrscraping.common.excel.model.ProductInfoDto
import kr.co.kpcard.ocrscraping.common.excel.service.ExcelService
import kr.co.kpcard.ocrscraping.kakao.enums.KakaoScrapEnum
import kr.co.kpcard.ocrscraping.common.extension.*
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class KakaoScrapService(
    private val webDriver: WebDriver,
    private val kakaoCategoryService: KakaoCategoryService,
    private val kakaoBrandService: KakaoBrandService,
    private val kakaoProductService: KakaoProductService,
    private val excelService: ExcelService
) {
    fun scrap() {
        webDriver.getPageWaitLoading(KakaoScrapEnum.INIT_URL.value, 2000, TimeUnit.MILLISECONDS)
        val categoryNameToUrl = kakaoCategoryService.scrapCategory()
        val categoryNameToBrandUrl = kakaoBrandService.scrapBrand(categoryNameToUrl)
        val kakaoProductInfos = kakaoProductService.getKakaoProductInfo(categoryNameToBrandUrl)
        val productInfos = ProductInfoDto.createByKakao(kakaoProductInfos)
        excelService.create(productInfos)
    }
    companion object : LoggingCompanion(){

    }
}