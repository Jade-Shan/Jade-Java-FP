package net.jadedungeon.javautil.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Consumer;

public class TimeUtils {

	public static final String FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FMT_DATE = "yyyy-MM-dd";

	public static final String STR_DATETIME_ZERO = (new SimpleDateFormat(FMT_DATETIME)).format(new Date(0L));
	public static final String STR_DATE_ZERO = (new SimpleDateFormat(FMT_DATETIME)).format(new Date(0L));

	public static final ZoneId baseZone = ZoneId.of("Asia/Shanghai");

	public static Date toDate(SimpleDateFormat sdf, String dateStr) {
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date(0);
		}
		return date;
	}

	public static Date toDate(SimpleDateFormat sdf, String dateStr, Consumer<Exception> errHandler) {
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date(0);
			errHandler.accept(e);
		}
		return date;
	}

	public static Date toDate(LocalDate localDate) {
		return java.sql.Date.valueOf(localDate);
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(TimeUtils.baseZone).toInstant());
	}

	public static long toLong(LocalDate localDate) throws Exception {
		return toLong(localDate, 1L, e -> {
		});
	}

	public static Long toLong(LocalDate localDate, // nl
			Long defaultValue, Consumer<Exception> errHandler) throws Exception // nl
	{
		Long result = defaultValue;
		try {
			result = toDate(localDate).getTime();
		} catch (Exception e) {
			if (null != defaultValue) {
				result = defaultValue;
			}
			if (null == errHandler) {
				throw e;
			} else {
				errHandler.accept(e);
			}
		}
		return result;
	}

	public static long toLong(LocalDateTime localDateTime) throws Exception {
		return toLong(localDateTime, 1L, e -> {
		});
	}

	public static long toLong(LocalDateTime localDateTime, // nl
			Long defaultValue, Consumer<Exception> errHandler) throws Exception {
		Long result = defaultValue;
		try {
			result = toDate(localDateTime).getTime();
		} catch (Exception e) {
			if (null != defaultValue) {
				result = defaultValue;
			}
			if (null == errHandler) {
				throw e;
			} else {
				errHandler.accept(e);
			}
		}
		return result;
	}

	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(TimeUtils.baseZone).toLocalDate();
	}

	public static LocalDate toLocalDate(long time) {
		return toLocalDate(new Date(time));
	}

	public static LocalDate toLocalDate(SimpleDateFormat sdf, String timeStr) throws ParseException {
		return sdf.parse(timeStr).toInstant().atZone(TimeUtils.baseZone).toLocalDate();
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(TimeUtils.baseZone).toLocalDateTime();
	}

	public static LocalDateTime toLocalDateTime(long time) {
		return toLocalDateTime(new Date(time));
	}

	public static LocalDateTime toLocalDateTime(SimpleDateFormat sdf, String timeStr) throws ParseException {
		return sdf.parse(timeStr).toInstant().atZone(TimeUtils.baseZone).toLocalDateTime();
	}

	public static String toString(SimpleDateFormat sdf, LocalDate localDate) {
		Date date = java.sql.Date.valueOf(localDate);
		return sdf.format(date);
	}

	public static String toString(SimpleDateFormat sdf, LocalDateTime localDateTime) {
		Date date = Date.from(localDateTime.atZone(TimeUtils.baseZone).toInstant());
		return sdf.format(date);
	}

	public static String toString(SimpleDateFormat sdf, Date date) {
		if (null == date) {
			date = new Date(0);
		}
		return sdf.format(date);
	}

	public static String toString(SimpleDateFormat sdf, long time) {
		Date date = new Date();
		date.setTime(time);
		return sdf.format(date);
	}

}
