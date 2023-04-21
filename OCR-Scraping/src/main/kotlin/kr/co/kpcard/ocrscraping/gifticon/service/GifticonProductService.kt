package kr.co.kpcard.ocrscraping.gifticon.service

import isTrueThen
import kr.co.kpcard.ocrscraping.common.config.LoggingCompanion
import kr.co.kpcard.ocrscraping.common.extension.*
import kr.co.kpcard.ocrscraping.common.repository.CustomScrapInfoRepository
import kr.co.kpcard.ocrscraping.common.util.ProcessingStateObject
import kr.co.kpcard.ocrscraping.gifticon.enums.GifticonScrapEnum
import kr.co.kpcard.ocrscraping.gifticon.model.GifticonProductInfo
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service

@Service
class GifticonProductService(
    private val webDriver: WebDriver,
    private val customScrapInfoRepository: CustomScrapInfoRepository
) {
    fun scrapProduct(categoryNameToBrandUrl: Map<String, List<String>>): List<GifticonProductInfo> {
        val categoryNameToProductUrl = categoryNameToBrandUrl.map { (categoryName, urlList) ->
            categoryName to urlList.map { url ->
                webDriver.processing(url) {
                    getProductUrl()
                }
            }.flatten()
        }.toMap().filter { it.value.isNotEmpty() }
        return getProductInfo(categoryNameToProductUrl)
    }

    private fun getProductUrl(): List<String> {
        val availablePageElements = webDriver.scrapByXpath(GifticonScrapEnum.BRAND_PAGE_X_PATH.value).filter {
            it.getAttribute("class").isEmpty()
        }.map { it.getAttribute("href") }
        return availablePageElements.isEmpty().isTrueThen {
            webDriver.scrapByXpath(GifticonScrapEnum.PRODUCT_X_PATH.value).map { product ->
                product.getAttribute("href")
            }
        } ?: availablePageElements.map {
            webDriver.customClick(it)
            webDriver.scrapByXpath(GifticonScrapEnum.PRODUCT_X_PATH.value).map { product ->
                product.getAttribute("href")
            }
        }.flatten()
    }

    private fun getProductInfo(categoryNameToProductUrl: Map<String, List<String>>): List<GifticonProductInfo> {
        setAllProduct(categoryNameToProductUrl.values.flatten().size)
        customScrapInfoRepository.createUrlData(categoryNameToProductUrl.values.flatten())
        return categoryNameToProductUrl.map { (categoryName, urlList) ->
            urlList.map { url ->
                webDriver.processing(url) {
                    scrapProductInfo(categoryName).also {
                        completeProduct(url)
                    }
                }
            }
        }.flatten()
    }

    private fun scrapProductInfo(categoryName: String): GifticonProductInfo {

        val title = webDriver.handleException("title") {
            it.findElementByClassName(GifticonScrapEnum.PRODUCT_PAGE_TITLE_CLASS_NAME.value).text
        } ?: ""

        val brand = webDriver.handleException("brand") {
            it.findElementByClassName(GifticonScrapEnum.PRODUCT_PAGE_BRAND_CLASS_NAME.value).text.removeSuffix("매장교환")
                .trim()
        } ?: ""

        val price = webDriver.handleException("price") {
            it.findElementByClassName(GifticonScrapEnum.PRODUCT_PRICE_CLASS_NAME.value).text
        } ?: ""

        val couponType =
            webDriver.handleException("couponType") { title.contains("원권").isTrueThen { "금액권" } ?: "교환권" } ?: "교환권"

        val content = webDriver.handleException("content") {
            val content1 = it.findElementById(GifticonScrapEnum.PRODUCT_CONTENT_1_CLASS_NAME.value).text
            val content2 = it.findElementById(GifticonScrapEnum.PRODUCT_CONTENT_2_CLASS_NAME.value).text
            content1 + content2
        } ?: ""

        val imgSrc = webDriver.handleException("imgSrc") {
            it.findElementByXpath(GifticonScrapEnum.PRODUCT_IMG_X_PATH.value).getAttribute("src")
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
        return GifticonProductInfo(
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
        ProcessingStateObject.gifticonCompleteProduct++
        customScrapInfoRepository.deleteUrlData(url)
    }
    private fun setAllProduct(allSize: Int) {
        ProcessingStateObject.gifticonAllProduct = allSize
    }

    companion object : LoggingCompanion()

}