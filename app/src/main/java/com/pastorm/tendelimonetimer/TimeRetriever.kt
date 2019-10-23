package com.pastorm.tendelimonetimer

import arrow.core.*
import org.jsoup.Jsoup
import java.io.IOException

interface TimeRetriever {
    fun retrieve(): Int?
}

class LiveCameraRetriever : TimeRetriever {
    override fun retrieve(): Int? {
        return 25
    }
}

class StaticWebsiteRetriever : TimeRetriever {
    private val endpoint = "http://escota.free.fr/tende/"
    private val idSelector = "#idIciSecondes"
    private val regExp = "nombreDeMinute=([0-9]+);".toRegex()

    override fun retrieve(): Int? {
        val htmlContent = getWebsiteContent().getOrElse { "" }
        val textSelected = retrieveByRegexp(htmlContent)
        return parseMinutes(textSelected)

    }

    private fun getWebsiteContent(): Either<IOException, String> {
        return try {
            val document = Jsoup.connect(endpoint).get()
            Right(document.outerHtml())
        } catch (e: IOException) {
            Left(e)
        }
    }

    fun retrieveBySelector(htmlContent: String, selector: String): String {
        return Jsoup.parse(htmlContent).run {
            select(selector).first()?.text().orEmpty()
        }
    }

    fun retrieveByRegexp(htmlContent: String): String {
        return regExp.find(htmlContent).toOption().map { i -> i.destructured.component1() }.getOrElse { "" }
    }

    fun parseMinutes(text: String): Int? {
        return text.toIntOrNull().takeIf { n -> n != null && n >= 0 }
    }
}