package kr.co.kpcard.ocrscraping.common.config

import kr.co.kpcard.ocrscraping.common.domain.ScrapFailInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

/*
 * reference : https://www.baeldung.com/kotlin-logging
 */

fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> = javaClass.enclosingClass?.takeIf {
    it.kotlin.companionObject?.java == javaClass
} ?: javaClass

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    override fun getValue(thisRef: R, property: KProperty<*>): Logger = getLogger(
        getClassForLogging(
            thisRef.javaClass
        )
    )
}

open class LoggingCompanion {
    val logger by LoggerDelegate()
}

val dollarSignRegex = """\$.*$""".toRegex()

private fun <T : Any> getClassName(clazz: Class<T>): String = clazz.name.replace(dollarSignRegex, "")

fun topLevelLogging(lambda: () -> Unit): Lazy<Logger> = lazy { getLogger(getClassName(lambda.javaClass)) }

inline fun <R> Logger.catchException(failInfo: ScrapFailInfo, function: () -> R): R?{
    return try{
        function()
    }catch (e:Exception){
        failInfo.apply {
            this.reason += "\n${e.message}"
        }
        return null
    }
}