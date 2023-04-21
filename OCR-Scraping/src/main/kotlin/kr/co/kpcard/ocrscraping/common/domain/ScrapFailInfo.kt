package kr.co.kpcard.ocrscraping.common.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ScrapFailInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seqNo: Long = 0L,
    var reason: String = "",
    val url: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun createBy(reason: List<String>, url: String): ScrapFailInfo {
            return ScrapFailInfo(
                reason = reason.toString(),
                url = url
            )
        }
    }
}