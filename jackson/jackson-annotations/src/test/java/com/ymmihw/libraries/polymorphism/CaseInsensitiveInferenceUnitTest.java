package com.ymmihw.libraries.polymorphism;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static com.ymmihw.libraries.polymorphism.JsonStringFormatterUtil.formatJson;
import static org.junit.jupiter.api.Assertions.*;

class CaseInsensitiveInferenceUnitTest {

    private final ObjectMapper objectMapper = JsonMapper.builder()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .build();

    @Test
    void givenACaseInsensitiveKnight_whenMapping_thenExpectKnight() throws Exception {
        String knightJson = formatJson("{'NaMe':'Ostrava, of Boletaria', 'WeaPON':'Rune Sword'}");

        Character character = objectMapper.readValue(knightJson, Character.class);

        assertTrue(character instanceof Knight);
        assertSame(character.getClass(), Knight.class);
        Knight knight = (Knight) character;
        assertEquals("Ostrava, of Boletaria", knight.getName());
        assertEquals("Rune Sword", knight.getWeapon());
    }

}
