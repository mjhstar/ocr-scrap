package kr.co.kpcard.ocrscraping.controller

import kr.co.kpcard.ocrscraping.common.service.ScrapStateService
import kr.co.kpcard.ocrscraping.gifticon.service.GifticonScrapService
import kr.co.kpcard.ocrscraping.giftishow.service.GiftiShowScrapService
import kr.co.kpcard.ocrscraping.kakao.service.KakaoScrapService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ScrapController(
    private val kakaoScrapService: KakaoScrapService,
    private val gifticonScrapService: GifticonScrapService,
    private val giftiShowScrapService: GiftiShowScrapService,
    private val scrapStateService: ScrapStateService,
) {
    @PostMapping("/kakao")
    fun scrapKakao() {
        return kakaoScrapService.scrap()
    }

    @PostMapping("/state")
    fun getState(): Any? {
        return scrapStateService.getState()
    }

    @PostMapping("/gifticon")
    fun scrapGifticon() {
        return gifticonScrapService.scrap()
    }

    @PostMapping("/giftishow")
    fun scrapGiftiShow() {
        return giftiShowScrapService.scrap()
    }

    @PostMapping("/scrap-all")
    fun scrap(){
        kakaoScrapService.scrap()
        gifticonScrapService.scrap()
        giftiShowScrapService.scrap()
    }

}