package kr.co.kpcard.ocrscraping.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("scrap")
class EnvProperties(
    val chromeDriverPath: String,
    val isHide: Boolean,
    val savePath: SavePath,
) {
    class SavePath(
        val excel: String,
        val image: String
    )
}