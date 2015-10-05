# Introduction

Have you ever wanted to simply format a string and had to type:

	final String msg = String.format("%s, %s, %d", a, b, c);

or

	final String msg = MessageFormat.format("{0}, {1}, {2}", a, b, c);

I personally prefer the slf4j style of formatting, i.e. the simple '{}' placeholder. So I decided to create this little library.

# Examples:

		final String sport = "football";
		final String msg = TextFormat.apply("I love {}", sport);
		System.out.println(msg); 
		// will print I love football

		final String sport = "football";
		final int howManyYears = 5;
		final String msg = TextFormat.apply("I loved {} for {} years.", sport, howManyYears);
		System.out.println(msg); 
		// will print I loved football for 5 years.
		
		final String[] sport = {"football", "volleyball", "basketball"};
		final int[] howManyYears = new int[]{5, 2, 7};
		final String msg = TextFormat.apply("I loved {} for {} respectively", sport, howManyYears);
		System.out.println(msg); 
		// will print I loved [football, volleyball, basketball] for [5, 2, 7] years respectively
		
# Performance

The library is much faster than MessageFormat and String.format as per tests committed in the project. Here are the results:

	-----------------------------------------------------------------------------------------------------
	| Benchmark                                        | Mode  | Cnt | Score      | Error       | Units |
	-----------------------------------------------------------------------------------------------------
	| FormattingPerformanceTest.Furmat                 | thrpt | 15  | 820050.779 | ± 81038.993 | ops/s |
	| FormattingPerformanceTest.MessageFormatNoTypes   | thrpt | 15  | 96166.474  | ± 11223.198 | ops/s |
	| FormattingPerformanceTest.MessageFormatWithTypes | thrpt | 15  | 99233.450  | ±  7445.348 | ops/s |
	| FormattingPerformanceTest.StringFormatNoTypes    | thrpt | 15  | 276612.720 | ± 36104.539 | ops/s |
	| FormattingPerformanceTest.StringFormatWithTypes  | thrpt | 15  | 195565.987 | ± 28498.439 | ops/s |
	-----------------------------------------------------------------------------------------------------

The message printed in the tests were:

	MessageFormat.format no types: I met iHw1zFy3tqwWGUdMj3BN 4 times, last on 10/4/15 7:50 PM. I think s    he weighs 72.338 kilos.

	MessageFormat.format with types: I met iHw1zFy3tqwWGUdMj3BN 4 times, last on Oct 4, 2015. I think she weighs 72.338 kilos.

	String.format no types: I met iHw1zFy3tqwWGUdMj3BN 4 times, last on Sun Oct 04 19:50:39 PDT 2015. I think she weighs 72.33835    162647343 kilos.

	String.format with types: I met iHw1zFy3tqwWGUdMj3BN 4 times, last on 10/04/15. I think she weighs 72.338352 kilos.

	Furmat: I met iHw1zFy3tqwWGUdMj3BN 4 times, last on Sun Oct 04 19:50:39 PDT 2015. I think she weighs 72.33835162647343 kilos.

