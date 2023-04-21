package kr.co.kpcard.ocrscraping.common.enums

enum class CouponType(
    val code: String,
    val desc: String,
    val mappingList: List<String>
) {
    TYPE_1("0", "미분류", listOf("")),
    TYPE_2("1", "기프티콘", listOf("교환권")),
    TYPE_3("2", "금액권", listOf("금액권")),
    TYPE_4("3", "할인/기타", listOf("")),
    TYPE_5("4", "상품권", listOf(""))

    ;

    companion object {
        fun findByMappingCode(mappingCode: String): CouponType? {
            return values().firstOrNull { it.mappingList.contains(mappingCode) }
        }
    }
}