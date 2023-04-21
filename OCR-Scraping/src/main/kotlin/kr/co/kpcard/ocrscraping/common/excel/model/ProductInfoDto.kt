package kr.co.kpcard.ocrscraping.common.excel.model

import kr.co.kpcard.ocrscraping.common.enums.CouponType
import kr.co.kpcard.ocrscraping.common.enums.IssuerType
import kr.co.kpcard.ocrscraping.common.enums.MarketCategoryType
import kr.co.kpcard.ocrscraping.common.extension.transferOnlyText
import kr.co.kpcard.ocrscraping.common.util.ScrapUtil
import kr.co.kpcard.ocrscraping.gifticon.model.GifticonProductInfo
import kr.co.kpcard.ocrscraping.giftishow.model.GiftiShowProductInfo
import kr.co.kpcard.ocrscraping.kakao.model.KakaoProductInfo

data class ProductInfoDto(
    val seqNo: Long = 0,
    val issuer: String = "",
    val title: String = "",
    val brand: String = "",
    val searchTitle: String = "",
    val searchBrand: String = "",
    val categoryCode: String = "",
    val categoryDesc: String = "",
    val couponTypeCode: String = "",
    val couponTypeDesc: String = "",
    val price: String = "",
    val content: String = "",
    val imageUrl: String = ""
) {
    companion object {
        fun createByKakao(kakaoProductInfos: List<KakaoProductInfo>): List<ProductInfoDto> {
            return kakaoProductInfos.map {
                val couponType = CouponType.findByMappingCode(it.couponType)
                val categoryType = MarketCategoryType.findByMappingCode(it.categoryName)
                ProductInfoDto(
                    seqNo = ScrapUtil.seqNo++,
                    issuer = IssuerType.KAKAO.code,
                    title = it.title,
                    brand = it.brand,
                    searchTitle = it.title.transferOnlyText(),
                    searchBrand = it.brand.transferOnlyText(),
                    categoryCode = categoryType?.code ?: "",
                    categoryDesc = categoryType?.desc ?: "",
                    couponTypeCode = couponType?.code ?: "",
                    couponTypeDesc = couponType?.desc ?: "",
                    price = it.price,
                    content = it.content,
                    imageUrl = it.imageSrc
                )
            }
        }

        fun createByGifticon(gifticonProductInfos: List<GifticonProductInfo>): List<ProductInfoDto>{
            return gifticonProductInfos.map {
                val couponType = CouponType.findByMappingCode(it.couponType)
                val categoryType = MarketCategoryType.findByMappingCode(it.categoryName)
                ProductInfoDto(
                    seqNo = ScrapUtil.seqNo++,
                    issuer = IssuerType.GIFTICON.code,
                    title = it.title,
                    brand = it.brand,
                    searchTitle = it.title.transferOnlyText(),
                    searchBrand = it.brand.transferOnlyText(),
                    categoryCode = categoryType?.code ?: "",
                    categoryDesc = categoryType?.desc ?: "",
                    couponTypeCode = couponType?.code ?: "",
                    couponTypeDesc = couponType?.desc ?: "",
                    price = it.price,
                    content = it.content,
                    imageUrl = it.imgSrc
                )
            }
        }

        fun createByGiftiShow(giftiShowProductInfos: List<GiftiShowProductInfo>): List<ProductInfoDto>{
            return giftiShowProductInfos.map {
                val couponType = CouponType.findByMappingCode(it.couponType)
                val categoryType = MarketCategoryType.findByMappingCode(it.categoryName)
                ProductInfoDto(
                    seqNo = ScrapUtil.seqNo++,
                    issuer = IssuerType.GIFTISHOW.code,
                    title = it.title,
                    brand = it.brand,
                    searchTitle = it.title.transferOnlyText(),
                    searchBrand = it.brand.transferOnlyText(),
                    categoryCode = categoryType?.code ?: "",
                    categoryDesc = categoryType?.desc ?: "",
                    couponTypeCode = couponType?.code ?: "",
                    couponTypeDesc = couponType?.desc ?: "",
                    price = it.price,
                    content = it.content,
                    imageUrl = it.imgSrc
                )
            }
        }
    }
}