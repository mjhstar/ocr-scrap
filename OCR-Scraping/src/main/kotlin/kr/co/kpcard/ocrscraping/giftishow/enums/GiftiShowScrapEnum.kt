package kr.co.kpcard.ocrscraping.giftishow.enums

enum class GiftiShowScrapEnum(
    val value: String,
    val desc: String
) {
    INIT_URL("https://www.giftishow.com/brand/brandList.mhows",""),

    MAIN_PAGE_CATEGORY_X_PATH("/html/body/div[1]/div[3]/div[2]/div[1]/div[1]/div/div[1]/ul/li/a",""),

    CATEGORY_PAGE_BRAND_X_PATH("/html/body/div[1]/div[3]/div[2]/div[1]/div[2]/div/ul/li",""),
    BRAND_PAGE_PRODUCT_X_PATH("/html/body/div[1]/div[3]/div[2]/div[2]/div/div[2]/ul/li/a",""),

    PRODUCT_PAGE_BRAND_CLASS_NAME("brandNm",""),
    PRODUCT_PAGE_TITLE_CLASS_NAME("itemNm",""),
    PRODUCT_PAGE_PRICE_CLASS_NAME("itemPrice",""),
    PRODUCT_PAGE_CONTENT_ID_1("cont1",""),
    PRODUCT_PAGE_CONTENT_2_BUTTON_X_PATH("/html/body/div[4]/div/div/div[3]/div[1]/div/div[2]",""),
    PRODUCT_PAGE_CONTENT_ID_2("cont2",""),
    PRODUCT_IMG_CLASS_NAME("imgWrap","")


}