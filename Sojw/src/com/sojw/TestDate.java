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
public class TestDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		aa();
	}

	public static void aa() {
		Long[] result1 = bb();
		Long[] result2 = cc();
		Long[] result3 = dd();
		Long[] result4 = ee();

		System.out.println(" result 1 = " + (result1[1] - result1[0]));
		System.out.println(" result 2 = " + (result2[1] - result2[0]));
		System.out.println(" result 3 = " + (result3[1] - result3[0]));
		System.out.println(" result 4 = " + (result4[1] - result4[0]));
	}

	public static Long[] bb() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			try {
				System.out.println("bb!!!");
			} catch (Exception e) {
				System.out.println("Exception");
			}
		}
		long s2 = System.currentTimeMillis();

		return new Long[] {s1, s2};
	}

	public static Long[] cc() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			System.out.println("cc!!!");
		}
		long s2 = System.currentTimeMillis();
		return new Long[] {s1, s2};
	}

	public static Long[] dd() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			try {
				System.out.println("dd!!!");
				throw new Exception();
			} catch (Exception e) {
			}
		}
		long s2 = System.currentTimeMillis();

		return new Long[] {s1, s2};
	}

	public static Long[] ee() {
		long s1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			try {
				System.out.println("ee!!!");
				throw new Exception();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		long s2 = System.currentTimeMillis();

		return new Long[] {s1, s2};
	}
}
