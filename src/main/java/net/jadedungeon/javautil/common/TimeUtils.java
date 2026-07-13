package net.jadedungeon.javautil.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Consumer;

/**
 * 时间工具类 —— 提供 Date、LocalDate、LocalDateTime 之间的互转。
 * <p>
 * 同时支持基于 {@link SimpleDateFormat} 的字符串格式转换和
 * 基于 Unix 时间戳（long）的转换。默认时区为 {@code Asia/Shanghai}。
 * </p>
 *
 * <p>转换矩阵：</p>
 * <ul>
 *   <li>{@code Date ↔ LocalDate} —— {@link #toDate(LocalDate)} / {@link #toLocalDate(Date)}</li>
 *   <li>{@code Date ↔ LocalDateTime} —— {@link #toDate(LocalDateTime)} / {@link #toLocalDateTime(Date)}</li>
 *   <li>{@code String → Date/LocalDate/LocalDateTime} —— 通过 SimpleDateFormat 解析</li>
 *   <li>{@code LocalDate/LocalDateTime/Date/time → String} —— 通过 SimpleDateFormat 格式化</li>
 * </ul>
 *
 * <p>错误处理使用 {@link Consumer}{@code <Exception>} 模式，
 * errHandler 为 null 时直接抛出异常。</p>
 */
public class TimeUtils {

	/** 日期时间格式：yyyy-MM-dd HH:mm:ss */
	public static final String FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式：yyyy-MM-dd */
	public static final String FMT_DATE = "yyyy-MM-dd";

	/** 零时间（epoch）的日期时间字符串表示 */
	public static final String STR_DATETIME_ZERO = (new SimpleDateFormat(FMT_DATETIME)).format(new Date(0L));
	/** 零时间（epoch）的日期字符串表示 */
	public static final String STR_DATE_ZERO = (new SimpleDateFormat(FMT_DATETIME)).format(new Date(0L));

	/** 默认时区：Asia/Shanghai */
	public static final ZoneId baseZone = ZoneId.of("Asia/Shanghai");

	/**
	 * 使用指定的 SimpleDateFormat 解析日期字符串。
	 * <p>
	 * 解析失败时返回 epoch 时间（new Date(0)），不抛出异常。
	 * </p>
	 *
	 * @param sdf     日期格式化器
	 * @param dateStr 日期字符串
	 * @return 解析得到的 Date，失败时返回 new Date(0)
	 */
	public static Date toDate(SimpleDateFormat sdf, String dateStr) {
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date(0);
		}
		return date;
	}

	/**
	 * 使用指定的 SimpleDateFormat 解析日期字符串（带错误处理）。
	 *
	 * @param sdf        日期格式化器
	 * @param dateStr    日期字符串
	 * @param errHandler 异常处理器
	 * @return 解析得到的 Date，失败时返回 new Date(0)
	 */
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

	/**
	 * 将 LocalDate 转为 Date（使用 java.sql.Date.valueOf）。
	 *
	 * @param localDate 源 LocalDate
	 * @return 转换后的 Date
	 */
	public static Date toDate(LocalDate localDate) {
		return java.sql.Date.valueOf(localDate);
	}

	/**
	 * 将 LocalDateTime 转为 Date。
	 * <p>
	 * 使用默认时区（Asia/Shanghai）进行转换。
	 * </p>
	 *
	 * @param localDateTime 源 LocalDateTime
	 * @return 转换后的 Date
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(TimeUtils.baseZone).toInstant());
	}

	/**
	 * 将 LocalDate 转为 Unix 时间戳（毫秒）。
	 *
	 * @param localDate 源 LocalDate
	 * @return Unix 时间戳
	 * @throws Exception 如果转换失败
	 */
	public static long toLong(LocalDate localDate) throws Exception {
		return toLong(localDate, 1L, e -> {
		});
	}

	/**
	 * 将 LocalDate 转为 Unix 时间戳（毫秒，带错误处理和默认值）。
	 *
	 * @param localDate    源 LocalDate
	 * @param defaultValue 转换失败时的默认值
	 * @param errHandler   异常处理器（可为 null，为 null 时抛出异常）
	 * @return Unix 时间戳或默认值
	 * @throws Exception 如果转换失败且 errHandler 为 null
	 */
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

	/**
	 * 将 LocalDateTime 转为 Unix 时间戳（毫秒）。
	 *
	 * @param localDateTime 源 LocalDateTime
	 * @return Unix 时间戳
	 * @throws Exception 如果转换失败
	 */
	public static long toLong(LocalDateTime localDateTime) throws Exception {
		return toLong(localDateTime, 1L, e -> {
		});
	}

	/**
	 * 将 LocalDateTime 转为 Unix 时间戳（毫秒，带错误处理和默认值）。
	 *
	 * @param localDateTime 源 LocalDateTime
	 * @param defaultValue  转换失败时的默认值
	 * @param errHandler    异常处理器（可为 null，为 null 时抛出异常）
	 * @return Unix 时间戳或默认值
	 * @throws Exception 如果转换失败且 errHandler 为 null
	 */
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

	/**
	 * 将 Date 转为 LocalDate。
	 *
	 * @param date 源 Date
	 * @return 转换后的 LocalDate（使用默认时区 Asia/Shanghai）
	 */
	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(TimeUtils.baseZone).toLocalDate();
	}

	/**
	 * 将 Unix 时间戳（毫秒）转为 LocalDate。
	 *
	 * @param time Unix 时间戳（毫秒）
	 * @return 转换后的 LocalDate
	 */
	public static LocalDate toLocalDate(long time) {
		return toLocalDate(new Date(time));
	}

	/**
	 * 使用指定格式解析日期字符串为 LocalDate。
	 *
	 * @param sdf     日期格式化器
	 * @param timeStr 日期字符串
	 * @return 转换后的 LocalDate
	 * @throws ParseException 如果解析失败
	 */
	public static LocalDate toLocalDate(SimpleDateFormat sdf, String timeStr) throws ParseException {
		return sdf.parse(timeStr).toInstant().atZone(TimeUtils.baseZone).toLocalDate();
	}

	/**
	 * 将 Date 转为 LocalDateTime。
	 *
	 * @param date 源 Date
	 * @return 转换后的 LocalDateTime（使用默认时区 Asia/Shanghai）
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(TimeUtils.baseZone).toLocalDateTime();
	}

	/**
	 * 将 Unix 时间戳（毫秒）转为 LocalDateTime。
	 *
	 * @param time Unix 时间戳（毫秒）
	 * @return 转换后的 LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(long time) {
		return toLocalDateTime(new Date(time));
	}

	/**
	 * 使用指定格式解析日期字符串为 LocalDateTime。
	 *
	 * @param sdf     日期格式化器
	 * @param timeStr 日期时间字符串
	 * @return 转换后的 LocalDateTime
	 * @throws ParseException 如果解析失败
	 */
	public static LocalDateTime toLocalDateTime(SimpleDateFormat sdf, String timeStr) throws ParseException {
		return sdf.parse(timeStr).toInstant().atZone(TimeUtils.baseZone).toLocalDateTime();
	}

	/**
	 * 使用指定格式将 LocalDate 格式化为字符串。
	 *
	 * @param sdf       日期格式化器
	 * @param localDate 源 LocalDate
	 * @return 格式化后的字符串
	 */
	public static String toString(SimpleDateFormat sdf, LocalDate localDate) {
		Date date = java.sql.Date.valueOf(localDate);
		return sdf.format(date);
	}

	/**
	 * 使用指定格式将 LocalDateTime 格式化为字符串。
	 *
	 * @param sdf           日期格式化器
	 * @param localDateTime 源 LocalDateTime
	 * @return 格式化后的字符串
	 */
	public static String toString(SimpleDateFormat sdf, LocalDateTime localDateTime) {
		Date date = Date.from(localDateTime.atZone(TimeUtils.baseZone).toInstant());
		return sdf.format(date);
	}

	/**
	 * 使用指定格式将 Date 格式化为字符串。
	 *
	 * @param sdf  日期格式化器
	 * @param date 源 Date（为 null 时使用 epoch 时间 new Date(0)）
	 * @return 格式化后的字符串
	 */
	public static String toString(SimpleDateFormat sdf, Date date) {
		if (null == date) {
			date = new Date(0);
		}
		return sdf.format(date);
	}

	/**
	 * 使用指定格式将 Unix 时间戳（毫秒）格式化为字符串。
	 *
	 * @param sdf  日期格式化器
	 * @param time Unix 时间戳（毫秒）
	 * @return 格式化后的字符串
	 */
	public static String toString(SimpleDateFormat sdf, long time) {
		Date date = new Date();
		date.setTime(time);
		return sdf.format(date);
	}

}
