package kr.co.kpcard.ocrscraping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan("kr.co.kpcard.ocrscraping")
class OcrScrapingApplication

fun main(args: Array<String>) {
    runApplication<OcrScrapingApplication>(*args)
}
