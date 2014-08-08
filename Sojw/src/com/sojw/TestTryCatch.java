/*
 * @(#)TestDate.java $version 2013. 8. 26.
 *
 * Copyright 2013 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sojw;

/**
 * @author NHN
 * @since 2013. 8. 26.
 */
public class TestTryCatch {

	public static int maxMethodCallCount = 15;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		aa();
	}

	public static void aa() {
		Long[] result1 = b(0);
		Long[] result2 = c(0);
		Long[] result3 = d(0);
		Long[] result4 = e(0);

		System.out.println(" result 1 = " + (result1[1] - result1[0]));
		System.out.println(" result 2 = " + (result2[1] - result2[0]));
		System.out.println(" result 3 = " + (result3[1] - result3[0]));
		System.out.println(" result 4 = " + (result4[1] - result4[0]));
	}

	public static Long[] b(int methodCallCount) {
		if (methodCallCount < maxMethodCallCount) {
			return b(methodCallCount + 1);
		} else {
			return b1();
		}
	}

	/**
	 * @return
	 */
	private static Long[] b1() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			try {
				System.out.println("b!!!");
			} catch (Exception e) {
				System.out.println("Exception");
			}
		}
		long s2 = System.currentTimeMillis();

		return new Long[] {s1, s2};
	}

	public static Long[] c(int methodCallCount) {
		if (methodCallCount < maxMethodCallCount) {
			return c(methodCallCount + 1);
		} else {
			return c1();
		}
	}

	/**
	 * @return
	 */
	private static Long[] c1() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			System.out.println("c!!!");
		}
		long s2 = System.currentTimeMillis();

		return new Long[] {s1, s2};
	}

	public static Long[] d(int methodCallCount) {
		if (methodCallCount < maxMethodCallCount) {
			return d(methodCallCount + 1);
		} else {
			return d1();
		}
	}

	/**
	 * @return
	 */
	private static Long[] d1() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			try {
				System.out.println("d!!!");
				throw new Exception();
			} catch (Exception e) {
			}
		}
		long s2 = System.currentTimeMillis();

		return new Long[] {s1, s2};
	}

	public static Long[] e(int methodCallCount) {
		if (methodCallCount < maxMethodCallCount) {
			return e(methodCallCount + 1);
		} else {
			return e1();
		}
	}

	/**
	 * @return
	 */
	private static Long[] e1() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			try {
				System.out.println("e!!!");
				throw new Exception();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		long s2 = System.currentTimeMillis();

		return new Long[] {s1, s2};
	}
}
