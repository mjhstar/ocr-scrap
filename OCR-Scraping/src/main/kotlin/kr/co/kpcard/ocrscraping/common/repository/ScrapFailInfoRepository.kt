package kr.co.kpcard.ocrscraping.common.repository

import kr.co.kpcard.ocrscraping.common.domain.ScrapFailInfo
import org.springframework.data.jpa.repository.JpaRepository

interface ScrapFailInfoRepository: JpaRepository<ScrapFailInfo, Long> {
}