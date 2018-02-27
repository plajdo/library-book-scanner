package io.github.shardbytes.lbs.document.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public final class TimeUtils{
	
	public static final LocalDate dateToLocal(Date date){
		/*
		 * TODO: Java 9
		 * return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
		 */
		Instant instant = date.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		return zdt.toLocalDate();
		
	}
	
	public static final Date localToOld(LocalDate date){
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
	}
	
}
