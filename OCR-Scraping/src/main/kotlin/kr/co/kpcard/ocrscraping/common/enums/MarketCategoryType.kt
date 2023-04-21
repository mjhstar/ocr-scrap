package kr.co.kpcard.ocrscraping.common.enums

enum class MarketCategoryType(
    val code: String,
    val desc: String,
    val mappingList: List<String>
) {
    CATEGORY_1(
        "1", "즐겨찾는 판매자",
        listOf("")
    ),
    CATEGORY_2(
        "2", "상품권류",
        listOf("상품권", "게임/문화상품권")
    ),
    CATEGORY_3(
        "3", "공연/영화/뷰티",
        listOf("영화", "영화/음악/도서", "뷰티/헤어/바디", "도서", "뷰티","전시/체험")
    ),
    CATEGORY_4(
        "4", "버거/피자/외식",
        listOf(
            "구이/족발",
            "버거/피자",
            "분식/죽/도시락",
            "치킨",
            "패밀리/호텔뷔페",
            "퓨전/외국/펍",
            "한식/중식/일식",
            "버거/피자/치킨",
            "외식",
            "한식/중식/분식",
            "레스토랑/뷔페",
            "외국/퓨전/기타"
        )
    ),
    CATEGORY_5(
        "5", "커피/음료/아이스크림",
        listOf("카페", "아이스크림/빙수", "아이스크림", "커피/음료")
    ),
    CATEGORY_6(
        "6", "기타/할인쿠폰/실물",
        listOf("기타","할인")
    ),
    CATEGORY_7(
        "7", "베이커리/도넛",
        listOf("베이커리/도넛/떡", "베이커리/도넛")
    ),
    CATEGORY_8(
        "8", "편의점/마트",
        listOf("편의점", "마트/편의점", "백화점/마트")
    );

    companion object {
        fun findByCode(code: String): MarketCategoryType? {
            return values().firstOrNull { it.code == code }
        }

        fun findByMappingCode(mappingCode: String): MarketCategoryType? {
            return values().firstOrNull { it.mappingList.contains(mappingCode) }
        }
    }
}