package kr.co.kpcard.ocrscraping.common.repository

import kr.co.kpcard.ocrscraping.common.domain.ScrapUrlData
import org.springframework.data.jpa.repository.JpaRepository

interface ScrapUrlDataRepository: JpaRepository<ScrapUrlData, Long> {
    fun deleteByUrl(url: String): ScrapUrlData
}