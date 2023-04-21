package kr.co.kpcard.ocrscraping.kakao.enums

enum class KakaoScrapEnum(
    val value: String,
    val desc: String
) {
    INIT_URL("https://gift.kakao.com/brand/category/91/subcategory/146", "카카오 스크래핑 시작 페이지"),
    CATEGORY_URL("https://gift.kakao.com/brand/category/91/subcategory/","각 카테고리 접속 url"),
    MAIN_PAGE_CATEGORY_CLASS_NAME("link_midcate","메인페이지 카테고리 스크래핑 클래스 이름 태그"),
    MAIN_PAGE_CATEGORY_DATA_NAME_TAG("data-tiara-copy","메인페이지 카테고리 아이템 이름 태그 "),
    MAIN_PAGE_CATEGORY_URL_TAG("data-tiara-id","메인페이지 카테고리 url 태그"),

    CATEGORY_PAGE_BRAND_X_PATH("/html/body/app-root/app-view-wrapper/div/div/main/article/app-pw-home/div/div/app-brands/div/div/div[2]/div[1]/ngx-flicking/div/div/div/div/gc-link/a","각 브랜드 xpath"),
    CATEGORY_PAGE_BRAND_PAGE_BUTTON_X_PATH("/html/body/app-root/app-view-wrapper/div/div/main/article/app-pw-home/div/div/app-brands/div/div/div[2]/button[2]/span",""),
    CATEGORY_PAGE_BRAND_CLASS_NAME("link_item","카테고리 페이지 브랜드 X PATH"),
    CATEGORY_PAGE_SIZE_X_PATH("/html/body/app-root/app-view-wrapper/div/div/main/article/app-pw-home/div/div/app-brands/div/div/div[2]/div[2]/span",""),

    PRODUCT_URL("https://gift.kakao.com/product/","상품 url prefix"),
    BRAND_PAGE_PRODUCT_NUMBER_URL_TAG("data-tiara-id","각 상품 번호"),
    BRAND_PAGE_PRODUCT_X_PATH("/html/body/app-root/app-view-wrapper/div/div/main/article/app-pw-detail/app-promotion/div/app-product-group/div/div/div/ul/li","스크롤 하기 위해 상품 개수 체크하기 위한 상품 xpath"),

    PRODUCT_PAGE_PRODUCT_TITLE("tit_subject","상품 페이지 내부 타이틀"),
    PRODUCT_PAGE_PRODUCT_BRAND("inner_shopname","상품 페이지 내부 브랜드"),
    PRODUCT_PAGE_PRODUCT_PRICE("txt_total","상품 페이지 내부 가격"),
    PRODUCT_PAGE_PRODUCT_CONTENT("desc_explain","상품 페이지 내부 컨텐츠"),
    PRODUCT_PAGE_PRODUCT_IMAGE_X_PATH("/html/body/app-root/app-view-wrapper/div/div/main/article/app-home/div/app-main/div/div/div[1]/div/ngx-flicking/div/div/img","상품 이미지 xpath"),
    PRODUCT_IMG("src","상품 페이지 내부 이미지 src"),


    ;
}