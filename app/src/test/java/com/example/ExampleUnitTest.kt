package com.example

import com.example.data.GrammarAndSkillsCatalog
import com.example.data.VocabularyCatalog
import com.example.model.CefrLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun vocabularyCatalog_containsAtLeast100A1WordsAndAllTopics() {
        val a1Words = VocabularyCatalog.allWords.filter { it.cefrLevel == "A1" }
        assertTrue("Expected at least 100 A1 words, got ${a1Words.size}", a1Words.size >= 100)
        assertTrue(VocabularyCatalog.allWords.all { it.english.isNotBlank() && it.uzbek.isNotBlank() })
    }

    @Test
    fun grammarCurriculum_coversAllCefrLevelsFromA1ToC2() {
        val levelsInCurriculum = GrammarAndSkillsCatalog.grammarLessons.map { it.cefrLevel }.toSet()
        CefrLevel.entries.forEach { level ->
            assertTrue("Missing grammar lesson for ${level.code}", levelsInCurriculum.contains(level.code))
        }
        assertTrue(GrammarAndSkillsCatalog.grammarLessons.all { it.questions.isNotEmpty() })
    }

    @Test
    fun placementEstimator_returnsExpectedCefrLevels() {
        assertEquals("A1", GrammarAndSkillsCatalog.estimateCefrFromScore(10))
        assertEquals("A2", GrammarAndSkillsCatalog.estimateCefrFromScore(35))
        assertEquals("B1", GrammarAndSkillsCatalog.estimateCefrFromScore(55))
        assertEquals("B2", GrammarAndSkillsCatalog.estimateCefrFromScore(75))
        assertEquals("C1", GrammarAndSkillsCatalog.estimateCefrFromScore(85))
        assertEquals("C2", GrammarAndSkillsCatalog.estimateCefrFromScore(95))
    }
}
