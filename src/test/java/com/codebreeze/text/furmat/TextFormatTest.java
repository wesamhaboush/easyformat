package com.codebreeze.text.furmat;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class TextFormatTest {

    @Test
    public void testWorksWithBooleans(){
        assertThat(TextFormat.format("{}", false), equalTo("false"));
        assertThat(TextFormat.apply("{}", 1), equalTo("1"));
        assertThat(TextFormat.apply("{}-{}", 1, false), equalTo("1-false"));
        assertThat(TextFormat.apply("{}-{}-{}", 1, false, 1.0), equalTo("1-false-1.0"));
        assertThat(TextFormat.apply("{}-{}-{}-{}", 1, false, 1.0, "string"), equalTo("1-false-1.0-string"));
        assertThat(TextFormat.apply("{}-{}-{}-{}-{}", 1, false, 1.0, "string", '?'), equalTo("1-false-1.0-string-?"));
    }

    @Test
    public void ensure_text_formatted_method_works_as_apply(){
        assertThat(TextFormat.format("{}", false), equalTo("false"));
        assertThat(TextFormat.textFormat("{}", 1), equalTo("1"));
        assertThat(TextFormat.textFormat("{}-{}", 1, false), equalTo("1-false"));
        assertThat(TextFormat.textFormat("{}-{}-{}", 1, false, 1.0), equalTo("1-false-1.0"));
        assertThat(TextFormat.textFormat("{}-{}-{}-{}", 1, false, 1.0, "string"), equalTo("1-false-1.0-string"));
        assertThat(TextFormat.textFormat("{}-{}-{}-{}-{}", 1, false, 1.0, "string", '?'), equalTo("1-false-1.0-string-?"));
    }

    @Test
    public void ensure_works_with_primitive_arrays(){
        assertThat(TextFormat.format("{}-{}-{}",
                new boolean[]{false, true},
                new int[]{1, 2},
                new double[]{1.0, 2.0}), equalTo("[false, true]-[1, 2]-[1.0, 2.0]"));
        assertThat(TextFormat.format("{}-{}-{}",
                new byte[]{1, 2},
                new char[]{'a', 'b'},
                new float[]{1.0f, 2.0f}), equalTo("[1, 2]-[a, b]-[1.0, 2.0]"));
        assertThat(TextFormat.format("{}-{}-{}",
                new short[]{1, 2},
                new long[]{1000, 2000},
                new Object[]{new Object(){ @Override public String toString(){ return "someObject"; }}, "simpleString"}),
                equalTo("[1, 2]-[1000, 2000]-[someObject, simpleString]"));
    }

    @Test
    public void testWorksWithInts(){
        assertThat(TextFormat.format("{}", 1), equalTo("1"));
    }

    @Test
    public void interesting_case_of_self_referencing_array(){
        final Object[] obs = new Object[3];
        obs[0] = "someObject";
        obs[1] = obs;
        obs[2] = "someOtherObject";
        assertThat(TextFormat.format("{}", obs), equalTo("[someObject, [...], someOtherObject]"));
    }

    @Test
    public void testWorksWithArrays(){
        assertThat(TextFormat.format("{}-{}", 1, 2), equalTo("1-2"));
    }

    @Test
    public void testWorksWithHeterogenousArrays(){
        assertThat(TextFormat.format("{}-{}", 1, false), equalTo("1-false"));
    }

    @Test
    public void testWorksWithHeterogenousArraysThree(){
        assertThat(TextFormat.format("{}-{}-{}", 1, false, 1.0), equalTo("1-false-1.0"));
    }

    @Test
    public void testWorksWithHeterogenousArraysThreeNoPattern(){
        assertThat(TextFormat.format(null, 1, false, 1.0), equalTo("/1/false/1.0/"));
        assertThat(TextFormat.format("", 1, false, 1.0), equalTo("/1/false/1.0/"));
        assertThat(TextFormat.format(" ", 1, false, 1.0), equalTo("/1/false/1.0/"));
        assertThat(TextFormat.format(" ", 1), equalTo("1"));
    }

    @Test
    public void testWorksWithHeterogenousArraysFourLessPattern(){
        assertThat(TextFormat.format("{}-{}", 1, false, 1.0, 2), equalTo("1-false -> WARNING: unused parameters: message pattern [{}-{}], parameters[1, false, 1.0, 2]"));
    }

    @Test
    public void testWorksWithHeterogenousArraysThreeLessPattern(){
        assertThat(TextFormat.format("{}-{}", 1, false, 1.0), equalTo("1-false -> WARNING: unused parameters: message pattern [{}-{}], parameters[1, false, 1.0]"));
    }

    @Test
    public void ensure_if_no_values_message_pattern_is_returned(){
        assertThat(TextFormat.format("{}", null), equalTo("null"));
    }

    @Test
    public void ensure_escaped_delimiters_are_not_replaced(){
        assertThat(TextFormat.format("\\{}-{}", 1), equalTo("{}-1"));
    }

    @Test
    public void ensure_double_escaped_delimiters_are_replaced(){
        assertThat(TextFormat.format("\\\\{}-{}", 1, false), equalTo("\\1-false"));
    }

    @Test
    public void ensure_simpe_strings_are_returned_as_is(){
        assertThat(TextFormat.format("hello world", 1), equalTo("hello world"));
    }
}