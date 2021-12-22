package com.nagarro.nagp.ebroker.util;

import java.util.Calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateUtilTest {

	@Test
	@DisplayName("Is date valid")
	void shouldtest_weekDay() {
		Calendar c = Calendar.getInstance();
		DateUtil.isValidPeriod();
		c.set(2021, 12, 12, 13, 12, 12);
		/*
		 * try(MockedStatic<Calendar> date = Mockito.mockStatic(Calendar.class)) {
		 * date.when(Calendar :: getInstance).thenReturn(c);
		 * Assertions.assertTrue(DateUtil.isValidPeriod()); }
		 */
	}
}
