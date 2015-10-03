# easyformat

makes formatting easy a la slf4j

# Examples:

		final String sport = "football";
		final String msg = TextFormat.apply("I love {}", sport);
		System.out.println(msg); // will print I love football

		final String sport = "football";
		final int howManyYears = 5;
		final String msg = TextFormat.apply("I loved {} for {} years.", sport, howManyYears);
		System.out.println(msg); // will print I loved football for 5 years.
		
		final String[] sport = {"football", "volleyball", "basketball"};
		final int[] howManyYears = new int[]{5, 2, 7};
		final String msg = TextFormat.apply("I loved {} for {} respectively", sport, howManyYears);
		System.out.println(msg); // will print I loved [football, volleyball, basketball] for [5, 2, 7] years respectively
