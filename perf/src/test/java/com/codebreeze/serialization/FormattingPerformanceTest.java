package com.codebreeze.serialization;

import com.codebreeze.text.furmat.TextFormat;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;

@Measurement(iterations = 5, time = 1)
@Warmup(iterations = 5, time = 1)
@Fork(3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class FormattingPerformanceTest {
    private static final Consumer<Object> CONSUMER =
            consumer();

    private static Consumer<Object> consumer() {
        return toBoolean(System.getProperty("performance", "false"))
                ? (s -> {})
                : (s -> System.out.println(s));
    }

    private static final String MSG_TEMPLATE_FOR_MESSAGE_FORMAT_NO_TYPES = "I met {0} {1} times, last on {2}. I think she weighs {3} kilos.";
    private static final String MSG_TEMPLATE_FOR_MESSAGE_FORMAT_WITH_TYPES = "I met {0} {1,number,integer} times, last on {2,date}. I think she weighs {3,number} kilos.";
    private static final String MSG_TEMPLATE_FOR_STRING_FORMAT_NO_TYPES = "I met %s %s times, last on %s. I think she weighs %s kilos.";
    private static final String MSG_TEMPLATE_FOR_STRING_FORMAT_WITH_TYPES = "I met %s %d times, last on %tD. I think she weighs %f kilos.";
    private static final String MSG_TEMPLATE_FOR_FURMAT = "I met {} {} times, last on {}. I think she weighs {} kilos.";
    private static final String NAME = randomAlphanumeric(20);
    private static final int TIMES = nextInt(3, 15);
    private static final double WEIGHT = nextDouble(50.0, 80.0);
    private static final Date DATE = new Date(System.currentTimeMillis());

    @Setup
    public void setup(){
        //this is to achieve some kind of warm up
        System.out.println("MessageFormat.format no types: " + MessageFormat.format(MSG_TEMPLATE_FOR_MESSAGE_FORMAT_NO_TYPES, NAME, TIMES, DATE, WEIGHT));
        System.out.println("MessageFormat.format with types: " + MessageFormat.format(MSG_TEMPLATE_FOR_MESSAGE_FORMAT_WITH_TYPES, NAME, TIMES, DATE, WEIGHT));
        System.out.println("String.format no types: " + String.format(MSG_TEMPLATE_FOR_STRING_FORMAT_NO_TYPES, NAME, TIMES, DATE, WEIGHT));
        System.out.println("String.format with types: " + String.format(MSG_TEMPLATE_FOR_STRING_FORMAT_WITH_TYPES, NAME, TIMES, DATE, WEIGHT));
        System.out.println("furmat: " + TextFormat.format(MSG_TEMPLATE_FOR_FURMAT, NAME, TIMES, DATE, WEIGHT));
    }

    @Benchmark
    public void MessageFormatNoTypes() {
        CONSUMER.accept(MessageFormat.format(MSG_TEMPLATE_FOR_MESSAGE_FORMAT_NO_TYPES, NAME, TIMES, DATE, WEIGHT));
    }

    @Benchmark
    public void MessageFormatWithTypes(){
        CONSUMER.accept(MessageFormat.format(MSG_TEMPLATE_FOR_MESSAGE_FORMAT_WITH_TYPES, NAME, TIMES, DATE, WEIGHT));
    }

    @Benchmark
    public void StringFormatNoTypes() {
        CONSUMER.accept(String.format(MSG_TEMPLATE_FOR_STRING_FORMAT_NO_TYPES, NAME, TIMES, DATE, WEIGHT));
    }

    @Benchmark
    public void StringFormatWithTypes() throws IOException {
        CONSUMER.accept(String.format(MSG_TEMPLATE_FOR_STRING_FORMAT_WITH_TYPES, NAME, TIMES, DATE, WEIGHT));
    }

    @Benchmark
    public void Furmat() throws IOException {
        CONSUMER.accept(TextFormat.format(MSG_TEMPLATE_FOR_FURMAT, NAME, TIMES, DATE, WEIGHT));
    }
}