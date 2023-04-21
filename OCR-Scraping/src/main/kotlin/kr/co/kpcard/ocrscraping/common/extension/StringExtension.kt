package kr.co.kpcard.ocrscraping.common.extension

import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.time.DateFormatUtils
import java.util.*

fun String.transferOnlyText(): String {
    return this.replace("[^가-힣A-Za-z0-9]".toRegex(), "")
}

fun String.getUniqueFileName(): String {
    return "$this${DateFormatUtils.format(Date(), "yyyyMMddHHmmss")}${RandomStringUtils.randomNumeric(5)}.ext"
}

fun String.transferOnlyNumber(): Int {
    return this.replace("[^0-9]".toRegex(), "").toInt()
}

