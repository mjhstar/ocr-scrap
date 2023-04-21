package kr.co.kpcard.ocrscraping.common.enums

enum class IssuerType(
    val code: String,
    val desc: String
) {
    KAKAO("KAKAO", "카카오"),
    GIFTICON("GIFTICON", "기프티콘"),
    GIFTISHOW("GIFTISHOW", "기프티쇼")

    ;
    companion object{
        fun findByCode(code: String): IssuerType?{
            return values().firstOrNull { it.code == code }
        }
    }
}