package kr.co.kpcard.ocrscraping.gifticon.enums

enum class GifticonScrapEnum(
    val value: String,
    val desc: String,
) {
    INIT_URL("https://www.gifticon.com/shopping/shopping_brandshop.do", "초기 접속 페이지"),

    MAIN_PAGE_CATEGORY_X_PATH("/html/body/div[1]/div[2]/div[2]/div/div[3]/div/div[1]/div[2]/ul/li[2]/em[2]/span/a", "메인 페이지 카테고리 목록 X PATH"),

    CATEGORY_PAGE_BRAND_X_PATH("/html/body/div[1]/div[2]/div[2]/div/div[3]/div/div[4]/ul/li/a","카테고리 페이지 브랜드 X PATH"),

    BRAND_X_PATH("/html/body/div[1]/div[2]/div[2]/div/div[3]/div/div[3]/ul/li/a",""),
    BRAND_PAGE_X_PATH("/html/body/div[1]/div[2]/div[2]/div/div[7]/div[2]/a",""),

    PRODUCT_X_PATH("/html/body/div[1]/div[2]/div[2]/div/div[7]/div[1]/ul/li/a",""),

    PRODUCT_PAGE_TITLE_CLASS_NAME("pdt_name",""),
    PRODUCT_PAGE_BRAND_CLASS_NAME("shopna",""),
    PRODUCT_PRICE_CLASS_NAME("cost",""),
    PRODUCT_CONTENT_1_CLASS_NAME("con1",""),
    PRODUCT_CONTENT_2_CLASS_NAME("con2",""),
    PRODUCT_IMG_X_PATH("/html/body/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/span/img","")



    ;
}