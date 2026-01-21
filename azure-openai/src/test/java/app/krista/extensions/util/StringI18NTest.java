/*
 * Azure Open Ai Extension for Krista
 * Copyright (C) 2025 Krista Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>. 
 */

package app.krista.extensions.util;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for StringI18N utility class.
 * Tests static utility methods for string manipulation and validation.
 */
class StringI18NTest {

    /**
     * Test isEmpty with null string.
     */
    @Test
    void testIsEmpty_WithNull() {
        // Act & Assert
        assertTrue(StringI18N.isEmpty(null));
    }

    /**
     * Test isEmpty with empty string.
     */
    @Test
    void testIsEmpty_WithEmptyString() {
        // Act & Assert
        assertTrue(StringI18N.isEmpty(""));
    }

    /**
     * Test isEmpty with whitespace only.
     */
    @Test
    void testIsEmpty_WithWhitespace() {
        // Act & Assert
        assertTrue(StringI18N.isEmpty("   "));
        assertTrue(StringI18N.isEmpty("\t"));
        assertTrue(StringI18N.isEmpty("\n"));
        assertTrue(StringI18N.isEmpty(" \t\n "));
    }

    /**
     * Test isEmpty with non-empty string.
     */
    @Test
    void testIsEmpty_WithNonEmptyString() {
        // Act & Assert
        assertFalse(StringI18N.isEmpty("hello"));
        assertFalse(StringI18N.isEmpty(" hello "));
        assertFalse(StringI18N.isEmpty("a"));
    }

    /**
     * Test isNotEmpty with null string.
     */
    @Test
    void testIsNotEmpty_WithNull() {
        // Act & Assert
        assertFalse(StringI18N.isNotEmpty(null));
    }

    /**
     * Test isNotEmpty with empty string.
     */
    @Test
    void testIsNotEmpty_WithEmptyString() {
        // Act & Assert
        assertFalse(StringI18N.isNotEmpty(""));
        assertFalse(StringI18N.isNotEmpty("   "));
    }

    /**
     * Test isNotEmpty with non-empty string.
     */
    @Test
    void testIsNotEmpty_WithNonEmptyString() {
        // Act & Assert
        assertTrue(StringI18N.isNotEmpty("hello"));
        assertTrue(StringI18N.isNotEmpty(" hello "));
    }

    /**
     * Test safeEquals with equal strings.
     */
    @Test
    void testSafeEquals_WithEqualStrings() {
        // Act & Assert
        assertTrue(StringI18N.safeEquals("hello", "hello"));
        assertTrue(StringI18N.safeEquals("", ""));
    }

    /**
     * Test safeEquals with different strings.
     */
    @Test
    void testSafeEquals_WithDifferentStrings() {
        // Act & Assert
        assertFalse(StringI18N.safeEquals("hello", "world"));
        assertFalse(StringI18N.safeEquals("Hello", "hello"));
    }

    /**
     * Test safeEquals with null strings.
     */
    @Test
    void testSafeEquals_WithNull() {
        // Act & Assert
        assertFalse(StringI18N.safeEquals(null, "hello"));
        assertFalse(StringI18N.safeEquals("hello", null));
        assertFalse(StringI18N.safeEquals(null, null));
    }

    /**
     * Test safeString with null.
     */
    @Test
    void testSafeString_WithNull() {
        // Act
        String result = StringI18N.safeString(null);

        // Assert
        assertEquals("", result);
    }

    /**
     * Test safeString with non-null string.
     */
    @Test
    void testSafeString_WithNonNull() {
        // Act
        String result = StringI18N.safeString("hello");

        // Assert
        assertEquals("hello", result);
    }

    /**
     * Test safeString with custom default.
     */
    @Test
    void testSafeString_WithCustomDefault() {
        // Act
        String result = StringI18N.safeString(null, "default");

        // Assert
        assertEquals("default", result);
    }

    /**
     * Test safeString with non-null and custom default.
     */
    @Test
    void testSafeString_WithNonNullAndCustomDefault() {
        // Act
        String result = StringI18N.safeString("hello", "default");

        // Assert
        assertEquals("hello", result);
    }

    /**
     * Test startsWithIgnoreCase with matching prefix.
     */
    @Test
    void testStartsWithIgnoreCase_WithMatchingPrefix() {
        // Act & Assert
        assertTrue(StringI18N.startsWithIgnoreCase("Hello World", "hello"));
        assertTrue(StringI18N.startsWithIgnoreCase("HELLO", "hel"));
        assertTrue(StringI18N.startsWithIgnoreCase("test", "TEST"));
    }

    /**
     * Test startsWithIgnoreCase with non-matching prefix.
     */
    @Test
    void testStartsWithIgnoreCase_WithNonMatchingPrefix() {
        // Act & Assert
        assertFalse(StringI18N.startsWithIgnoreCase("Hello World", "world"));
        assertFalse(StringI18N.startsWithIgnoreCase("test", "abc"));
    }

    /**
     * Test startsWithIgnoreCase with null string.
     */
    @Test
    void testStartsWithIgnoreCase_WithNullString() {
        // Act & Assert
        assertFalse(StringI18N.startsWithIgnoreCase(null, "hello"));
        assertFalse(StringI18N.startsWithIgnoreCase("", "hello"));
    }

    /**
     * Test startsWithIgnoreCase with null prefix.
     */
    @Test
    void testStartsWithIgnoreCase_WithNullPrefix() {
        // Act & Assert
        assertTrue(StringI18N.startsWithIgnoreCase("hello", null));
        assertTrue(StringI18N.startsWithIgnoreCase("hello", ""));
    }

    /**
     * Test startsWithIgnoreCase with prefix longer than string.
     */
    @Test
    void testStartsWithIgnoreCase_WithLongerPrefix() {
        // Act & Assert
        assertFalse(StringI18N.startsWithIgnoreCase("hi", "hello"));
    }

    /**
     * Test makeArrayFromCommas with simple string.
     */
    @Test
    void testMakeArrayFromCommas_WithSimpleString() {
        // Act
        String[] result = StringI18N.makeArrayFromCommas("a,b,c");

        // Assert
        assertEquals(3, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
    }

    /**
     * Test makeArrayFromCommas with null.
     */
    @Test
    void testMakeArrayFromCommas_WithNull() {
        // Act
        String[] result = StringI18N.makeArrayFromCommas(null);

        // Assert
        assertEquals(0, result.length);
    }

    /**
     * Test makeArrayFromCommas with single value.
     */
    @Test
    void testMakeArrayFromCommas_WithSingleValue() {
        // Act
        String[] result = StringI18N.makeArrayFromCommas("single");

        // Assert
        assertEquals(1, result.length);
        assertEquals("single", result[0]);
    }

    /**
     * Test makeCommasFromArray with simple array.
     */
    @Test
    void testMakeCommasFromArray_WithSimpleArray() {
        // Arrange
        String[] array = {"a", "b", "c"};

        // Act
        String result = StringI18N.makeCommasFromArray(array);

        // Assert
        assertEquals("a,b,c", result);
    }

    /**
     * Test makeCommasFromArray with null array.
     */
    @Test
    void testMakeCommasFromArray_WithNull() {
        // Act
        String result = StringI18N.makeCommasFromArray(null);

        // Assert
        assertEquals("", result);
    }

    /**
     * Test makeCommasFromArray with empty array.
     */
    @Test
    void testMakeCommasFromArray_WithEmptyArray() {
        // Act
        String result = StringI18N.makeCommasFromArray(new String[0]);

        // Assert
        assertEquals("", result);
    }

    /**
     * Test makeCommasFromArray with spaces.
     */
    @Test
    void testMakeCommasFromArray_WithSpaces() {
        // Arrange
        String[] array = {"a", "b", "c"};

        // Act
        String result = StringI18N.makeCommasFromArray(array, true, null);

        // Assert
        assertEquals("a, b, c", result);
    }

    /**
     * Test makeCommasFromArray with andOr.
     */
    @Test
    void testMakeCommasFromArray_WithAndOr() {
        // Arrange
        String[] array = {"a", "b", "c"};

        // Act
        String result = StringI18N.makeCommasFromArray(array, true, "and");

        // Assert
        assertEquals("a, b, and c", result);
    }

    /**
     * Test doesStringHaveSubString with matching substring.
     */
    @Test
    void testDoesStringHaveSubString_WithMatch() {
        // Arrange
        String[] subs = {"test", "hello", "world"};

        // Act & Assert
        assertTrue(StringI18N.doesStringHaveSubString("This is a test", subs));
        assertTrue(StringI18N.doesStringHaveSubString("hello there", subs));
    }

    /**
     * Test doesStringHaveSubString with no match.
     */
    @Test
    void testDoesStringHaveSubString_WithNoMatch() {
        // Arrange
        String[] subs = {"foo", "bar", "baz"};

        // Act & Assert
        assertFalse(StringI18N.doesStringHaveSubString("This is a test", subs));
    }

    /**
     * Test doesStringHaveSubString with null string.
     */
    @Test
    void testDoesStringHaveSubString_WithNullString() {
        // Arrange
        String[] subs = {"test"};

        // Act & Assert
        assertFalse(StringI18N.doesStringHaveSubString(null, subs));
    }

    /**
     * Test doesStringHaveSubString with null array.
     */
    @Test
    void testDoesStringHaveSubString_WithNullArray() {
        // Act & Assert
        assertFalse(StringI18N.doesStringHaveSubString("test", null));
    }
}

