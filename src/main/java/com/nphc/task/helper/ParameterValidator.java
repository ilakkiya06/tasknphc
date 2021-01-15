package com.nphc.task.helper;

import java.time.format.DateTimeFormatter;

public class ParameterValidator {

	public static boolean checkCSVDateIsValid(String inputDate) {

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");

		DateValidator validator = new DateValidatorUsingLocalDate(dateFormatter);

		DateTimeFormatter dateFormatterYYYYMMDD = DateTimeFormatter.ofPattern("dd-mmm-yy");

		DateValidator validatorYYYYMMDD = new DateValidatorUsingLocalDate(dateFormatterYYYYMMDD);
		if (validator.isValid(inputDate) || validatorYYYYMMDD.isValid(inputDate)) {
			return true;
		}
		return false;
	}

	public static boolean checkDateFormat(String inputDate) {

		DateTimeFormatter.ofPattern("yyyy-mm-dd");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;

		DateValidator validator = new DateValidatorUsingLocalDate(dateFormatter);

		DateTimeFormatter.ofPattern("dd-mmm-yy");
		DateTimeFormatter dateFormatterYYYYMMDD = DateTimeFormatter.ISO_DATE;

		DateValidator validatorYYYYMMDD = new DateValidatorUsingLocalDate(dateFormatterYYYYMMDD);
		if (validator.isValid(inputDate) || validatorYYYYMMDD.isValid(inputDate)) {
			return true;

		}
		return false;
	}

}
