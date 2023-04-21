package kr.co.kpcard.ocrscraping.kakao.service

import isTrueThen
import kr.co.kpcard.ocrscraping.common.config.LoggingCompanion
import kr.co.kpcard.ocrscraping.common.extension.*
import kr.co.kpcard.ocrscraping.common.repository.CustomScrapInfoRepository
import kr.co.kpcard.ocrscraping.common.scrap.ScrapService
import kr.co.kpcard.ocrscraping.common.util.ProcessingStateObject
import kr.co.kpcard.ocrscraping.kakao.enums.KakaoScrapEnum
import kr.co.kpcard.ocrscraping.kakao.model.KakaoProductInfo
import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service

@Service
class KakaoProductService(
    private val webDriver: WebDriver,
    private val scrapService: ScrapService,
    private val customScrapInfoRepository: CustomScrapInfoRepository
) {
    fun getKakaoProductInfo(brandMap: Map<String, List<String>>): List<KakaoProductInfo> {
        val categoryNameToProductUrl = getProductUrl(brandMap)
        return scrapProduct(categoryNameToProductUrl)
    }

    private fun getProductUrl(brandMap: Map<String, List<String>>): Map<String, List<String>> {
        return brandMap.map { (categoryName, urlList) ->
            categoryName to urlList.map { url ->
                webDriver.processing(url) {
                    scrollAndGetProducts()
                }
            }.flatten()
        }.toMap()
    }

    private fun scrollAndGetProducts(): List<String> {
        scrapService.scrollDown()
        val webElements = webDriver.scrapByXpath(KakaoScrapEnum.BRAND_PAGE_PRODUCT_X_PATH.value)
        return webElements.map {
            KakaoScrapEnum.PRODUCT_URL.value + it.getAttribute(KakaoScrapEnum.BRAND_PAGE_PRODUCT_NUMBER_URL_TAG.value)
        }
    }

    private fun scrapProduct(categoryNameToProductUrl: Map<String, List<String>>): List<KakaoProductInfo> {
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

    private fun scrapProductInfo(categoryName: String): KakaoProductInfo {
        val title = webDriver.handleException("title") {
            it.findElementByClassName(KakaoScrapEnum.PRODUCT_PAGE_PRODUCT_TITLE.value).text
        } ?: ""

        val brand = webDriver.handleException("brand") {
            it.findElementByClassName(KakaoScrapEnum.PRODUCT_PAGE_PRODUCT_BRAND.value).text.removeSuffix("바로가기").trim()
        } ?: ""

        val price = webDriver.handleException("price") {
            it.findElementByClassName(KakaoScrapEnum.PRODUCT_PAGE_PRODUCT_PRICE.value).text
        } ?: ""

        val couponType =
            webDriver.handleException("couponType") { title.contains("원권").isTrueThen { "금액권" } ?: "교환권" } ?: "교환권"

        val content = webDriver.handleException("content") {
            it.findElementByClassName(KakaoScrapEnum.PRODUCT_PAGE_PRODUCT_CONTENT.value).text
        } ?: ""

        val imgSrc = webDriver.handleException("imgSrc") {
            it.findElementByXpath(KakaoScrapEnum.PRODUCT_PAGE_PRODUCT_IMAGE_X_PATH.value)
                .getAttribute(KakaoScrapEnum.PRODUCT_IMG.value)
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

        return KakaoProductInfo(
            categoryName = categoryName,
            title = title,
            brand = brand,
            price = price,
            content = content,
            couponType = couponType,
            imageSrc = imgSrc
        )

    }

    private fun completeProduct(url: String) {
        ProcessingStateObject.kakaoCompleteProduct++
        customScrapInfoRepository.deleteUrlData(url)
    }

    private fun setAllProduct(allSize: Int) {
        ProcessingStateObject.kakaoAllProduct = allSize
    }

    companion object : LoggingCompanion()
}