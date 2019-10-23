package com.pastorm.tendelimonetimer

import org.junit.Assert.*
import org.junit.Test


class RetrieverFactoryTest {
    @Test
    fun create_retriever_from_camera() {
        val factory = TimeRetrieverFactory()
        val retriever = factory.createRetriever(RetrieveMode.LIVE_CAMERA)
        assertTrue(retriever is LiveCameraRetriever)
    }

    @Test
    fun create_retriever_from_website() {
        val factory = TimeRetrieverFactory()
        val retriever = factory.createRetriever(RetrieveMode.STATIC_WEBSITE)
        assertTrue(retriever is StaticWebsiteRetriever)
    }
}


class StaticRetrieverTest {

    @Test
    fun get_result_with_selector_id() {
        val retriever = StaticWebsiteRetriever()
        val value = retriever.retrieveBySelector("<span id=\"myId\">myText</span>", "#myId")
        assertEquals("myText", value)
    }

    @Test
    fun get_result_with_selector_empty() {
        val retriever = StaticWebsiteRetriever()
        val value = retriever.retrieveBySelector("", "#anId")
        assertEquals("", value)
    }

    @Test
    fun get_result_by_regexp() {
        val retriever = StaticWebsiteRetriever()
        assertEquals("", retriever.retrieveByRegexp(""))
        assertEquals("", retriever.retrieveByRegexp("nombreDeMinute=-121511;"))
        assertEquals("100", retriever.retrieveByRegexp("nombreDeMinute=100;"))
    }

    @Test
    fun get_minutes_text() {
        val retriever = StaticWebsiteRetriever()
        assertNull(retriever.parseMinutes(""))
        assertNull(retriever.parseMinutes("-1"))
        assertNull(retriever.parseMinutes("noNumberText"))
        assertEquals(0, retriever.parseMinutes("0"))
        assertEquals(1, retriever.parseMinutes("1"))
    }
}