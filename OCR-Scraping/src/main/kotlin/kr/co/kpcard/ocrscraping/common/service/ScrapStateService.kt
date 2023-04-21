package kr.co.kpcard.ocrscraping.common.service

import kr.co.kpcard.ocrscraping.common.util.ProcessingStateObject
import org.springframework.stereotype.Service

@Service
class ScrapStateService {
    fun getState():Any?{
        return mapOf(
            "KAKAO" to listOf(ProcessingStateObject.kakaoAllProduct, ProcessingStateObject.kakaoCompleteProduct),
            "GIFTICON" to listOf(ProcessingStateObject.gifticonAllProduct, ProcessingStateObject.gifticonCompleteProduct),
            "GIFTISHOW" to listOf(ProcessingStateObject.giftiShowAllProduct, ProcessingStateObject.giftiShowCompleteProduct)
        )
    }
}