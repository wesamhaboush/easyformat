package com.codebreeze.easyformat;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TextFormatTest {

    @Test
    public void testWorksWithBooleans(){
        assertThat(TextFormat.format("{}", false), equalTo("false"));
    }

    @Test
    public void testWorksWithInts(){
        assertThat(TextFormat.format("{}", 1), equalTo("1"));
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
    }

    @Test
    public void testWorksWithHeterogenousArraysFourLessPattern(){
        assertThat(TextFormat.format("{}-{}", 1, false, 1.0, 2), equalTo("1-false -> WARNING: unused parameters: message pattern [{}-{}], parameters[1, false, 1.0, 2]"));
    }

    @Test
    public void testWorksWithHeterogenousArraysThreeLessPattern(){
        assertThat(TextFormat.format("{}-{}", 1, false, 1.0), equalTo("1-false -> WARNING: unused parameters: message pattern [{}-{}], parameters[1, false, 1.0]"));
    }

}