package kr.co.kpcard.ocrscraping.common.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ScrapUrlData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seqNo: Long = 0L,
    val url: String,
    val isProcess: Boolean = false
) {
    companion object {
        fun createBy(url: List<String>): List<ScrapUrlData> {
            return url.map {
                ScrapUrlData(
                    url = it
                )
            }
        }
    }
}