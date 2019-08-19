package com.boot.org.test;

import java.util.Random;
import java.util.stream.Stream;

/**
 * stream的三个操作步骤
 *
 */
public class TestMethodAPI1 {

	public static void main(String[] args) {
//		Stream<Integer> str = Stream.iterate(0, (x) -> x+2);
//		str.limit(10).forEach(System.out::println);
		Stream.generate(() -> Math.random())
			.limit(5)
			.forEach(System.out::println);
	}
}
