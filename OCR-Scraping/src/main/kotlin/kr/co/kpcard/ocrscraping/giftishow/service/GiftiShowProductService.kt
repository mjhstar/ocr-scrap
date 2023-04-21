package kr.co.kpcard.ocrscraping.giftishow.service

import isTrueThen
import kr.co.kpcard.ocrscraping.common.config.LoggingCompanion
import kr.co.kpcard.ocrscraping.common.extension.*
import kr.co.kpcard.ocrscraping.common.repository.CustomScrapInfoRepository
import kr.co.kpcard.ocrscraping.common.scrap.ScrapService
import kr.co.kpcard.ocrscraping.common.util.ProcessingStateObject
import kr.co.kpcard.ocrscraping.giftishow.enums.GiftiShowScrapEnum
import kr.co.kpcard.ocrscraping.giftishow.model.GiftiShowProductInfo
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class GiftiShowProductService(
    private val webDriver: WebDriver,
    private val giftiShowBrandService: GiftiShowBrandService,
    private val scrapService: ScrapService,
    private val customScrapInfoRepository: CustomScrapInfoRepository
) {
    fun scrapProduct(categoryNameToUrl: Map<String, String>): List<GiftiShowProductInfo> {
        val categoryNameToProductUrl = categoryNameToUrl.map { (categoryName, categoryUrl) ->
            val brandItems = giftiShowBrandService.scrapBrand(categoryUrl)
            categoryName to brandItems.map { url ->
                webDriver.customClick(url)
                scrapService.scrollDown()
                webDriver.scrapByXpath(GiftiShowScrapEnum.BRAND_PAGE_PRODUCT_X_PATH.value).map {
                    it.getAttribute("href")
                }
            }.flatten()
        }.toMap().filter { it.value.isNotEmpty() }.also {
            setAllProduct(it.values.flatten().size)
        }

        return categoryNameToProductUrl.map { (categoryName, urlList) ->
            customScrapInfoRepository.createUrlData(categoryNameToProductUrl.values.flatten())
            urlList.map { url ->
                webDriver.customClick(url)
                scrapProductInfo(categoryName).also {
                    completeProduct(url)
                }
            }
        }.flatten()
    }

    private fun scrapProductInfo(categoryName: String): GiftiShowProductInfo {
        val title = webDriver.handleException("title") {
            it.findElementByClassName(GiftiShowScrapEnum.PRODUCT_PAGE_TITLE_CLASS_NAME.value).text
        } ?: ""

        val brand = webDriver.handleException("brand") {
            it.findElementByClassName(GiftiShowScrapEnum.PRODUCT_PAGE_BRAND_CLASS_NAME.value).text
        } ?: ""

        val price = webDriver.handleException("price") {
            it.findElementByClassName(GiftiShowScrapEnum.PRODUCT_PAGE_PRICE_CLASS_NAME.value).text
        } ?: ""

        val couponType =
            webDriver.handleException("couponType") { title.contains("원권").isTrueThen { "금액권" } ?: "교환권" } ?: "교환권"

        val content = webDriver.handleException("content") {
            val content1 = it.findElementById(GiftiShowScrapEnum.PRODUCT_PAGE_CONTENT_ID_1.value).text
            val content2Button = it.findElementByXpath(GiftiShowScrapEnum.PRODUCT_PAGE_CONTENT_2_BUTTON_X_PATH.value)
            content2Button.click()
            sleep(300)
            val content2 = it.findElementById(GiftiShowScrapEnum.PRODUCT_PAGE_CONTENT_ID_2.value).text
            content1 + content2
        } ?: ""

        val imgSrc = webDriver.handleException("imgSrc") {
            it.findElementByClassName(GiftiShowScrapEnum.PRODUCT_IMG_CLASS_NAME.value)
                .getElementByTagName("img").getAttribute("src")
        } ?: ""

        customScrapInfoRepository.checkAndSaveFailInfo(
            title = title,
            brand = brand,
            price = price,
            couponType = couponType,
            content = content,
            imgSrc = imgSrc,
            url = webDriver.getCurrentUrlOrEmpty()
        )

        return GiftiShowProductInfo(
            categoryName = categoryName,
            title = title,
            brand = brand,
            price = price,
            couponType = couponType,
            content = content,
            imgSrc = imgSrc
        )
    }

    private fun completeProduct(url: String) {
        ProcessingStateObject.giftiShowCompleteProduct++
        customScrapInfoRepository.deleteUrlData(url)
    }

    private fun setAllProduct(allSize: Int) {
        ProcessingStateObject.giftiShowAllProduct = allSize
    }

    companion object : LoggingCompanion()
}