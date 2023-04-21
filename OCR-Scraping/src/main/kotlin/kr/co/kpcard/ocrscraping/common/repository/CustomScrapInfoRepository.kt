package kr.co.kpcard.ocrscraping.common.repository

import isTrueThen
import kr.co.kpcard.ocrscraping.common.domain.ScrapFailInfo
import kr.co.kpcard.ocrscraping.common.domain.ScrapUrlData
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import kotlin.math.truncate

@Repository
class CustomScrapInfoRepository(
    private val scrapFailInfoRepository: ScrapFailInfoRepository,
    private val scrapUrlDataRepository: ScrapUrlDataRepository
) {
    fun checkAndSaveFailInfo(
        title: String,
        brand: String,
        price: String,
        couponType: String,
        content: String,
        imgSrc: String,
        url: String
    ) {
        val reason = mutableListOf<String>()
        title.isEmpty().isTrueThen { reason.add("title") }
        brand.isEmpty().isTrueThen { reason.add("brand") }
        price.isEmpty().isTrueThen { reason.add("price") }
        couponType.isEmpty().isTrueThen { reason.add("couponType") }
        content.isEmpty().isTrueThen { reason.add("content") }
        imgSrc.isEmpty().isTrueThen { reason.add("img") }
        reason.isNotEmpty().isTrueThen {
            val scrapFailInfo = ScrapFailInfo.createBy(reason, url)
            scrapFailInfoRepository.save(scrapFailInfo)
        }
    }

    fun createUrlData(url: List<String>) {
        try {
            val data = ScrapUrlData.createBy(url)
            scrapUrlDataRepository.saveAll(data)
        } catch (_: Exception) {
        }
    }

    @Transactional
    fun deleteUrlData(url: String) {
        try {
            val a = scrapUrlDataRepository.deleteByUrl(url)
        } catch (_: Exception) {
        }
    }
}