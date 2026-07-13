package net.jadedungeon.javautil.json;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Consumer;

import org.json.JSONObject;

import net.jadedungeon.javautil.common.TimeUtils;

/**
 * JsonValueField —— JSON 标量值字段读取工具。
 * <p>
 * 从 {@link JSONObject} 中安全地读取各种类型的标量字段值。
 * 每种类型都提供了两个重载版本：
 * </p>
 * <ul>
 *   <li><b>无默认值版本</b>：解析失败时返回 null（或抛出异常）</li>
 *   <li><b>带默认值版本</b>：解析失败或字段为空时返回指定的默认值</li>
 * </ul>
 *
 * <p>支持的类型：Boolean、Integer、Long、Double、String、
 * Date（Long 和 String 两种格式）、Timestamp、LocalDate、LocalDateTime。</p>
 *
 * <p>统一使用 {@link Consumer}{@code <Exception>} 错误处理器模式。</p>
 */
public class JsonValueField {

	/**
	 * 读取 Boolean 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 字段值，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Boolean toBoolean(JSONObject jo, String field, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		return toBoolean(jo, field, null, errHandler);
	}

	/**
	 * 读取 Boolean 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 字段值或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Boolean toBoolean(JSONObject jo, String field, Boolean defaultValue, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		Boolean result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = jo.getBoolean(field);
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取 Integer 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 字段值，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Integer toInt(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toInt(jo, field, null, errHandler);
	}

	/**
	 * 读取 Integer 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 字段值或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Integer toInt(JSONObject jo, String field, Integer defaultValue, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		Integer result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = jo.getInt(field);
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取 Long 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 字段值，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Long toLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toLong(jo, field, null, errHandler);
	}

	/**
	 * 读取 Long 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 字段值或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Long toLong(JSONObject jo, String field, Long defaultValue, Consumer<Exception> errHandler)
			throws Exception // nl
	{
		Long result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = jo.getLong(field);
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取 Double 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 字段值，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Double toDouble(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
			throws Exception // nl
	{
		return toDouble(jo, field, null, errHandler);
	}

	/**
	 * 读取 Double 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 字段值或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Double toDouble(JSONObject jo, String field, // nl
			Double defaultValue, Consumer<Exception> errHandler) throws Exception // nl
	{
		Double result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = jo.getDouble(field);
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取 String 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 字段值，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static String toStr(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toStr(jo, field, null, errHandler);
	}

	/**
	 * 读取 String 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 字段值或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static String toStr(JSONObject jo, String field, // nl
			String defaultValue, Consumer<Exception> errHandler) throws Exception //
	{
		String result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = jo.getString(field);
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取以 Long 格式存储的 Date 字段（无默认值版本）。
	 * <p>
	 * 字段值为 Unix 时间戳（毫秒）。
	 * </p>
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 转换后的 Date，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Date toDateInLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toDateInLong(jo, field, null, errHandler);
	}

	/**
	 * 读取以 Long 格式存储的 Date 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 转换后的 Date 或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Date toDateInLong(JSONObject jo, String field, Date defaultValue, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		Date result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = new Date(jo.getLong(field));
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取以字符串格式存储的 Date 字段（无默认值版本）。
	 * <p>
	 * 使用指定的 {@link SimpleDateFormat} 解析日期字符串。
	 * </p>
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param sdf        日期格式化器
	 * @param errHandler 异常处理器
	 * @return 转换后的 Date，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Date toDateInStr(JSONObject jo, String field, SimpleDateFormat sdf, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		return toDateInStr(jo, field, sdf, null, errHandler);
	}

	/**
	 * 读取以字符串格式存储的 Date 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param sdf          日期格式化器
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 转换后的 Date 或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Date toDateInStr(JSONObject jo, String field, SimpleDateFormat sdf, // nl
			Date defaultValue, Consumer<Exception> errHandler) throws Exception // nl
	{
		Date result = defaultValue;
		try {
			if (null != sdf && !jo.isNull(field)) {
				result = TimeUtils.toDate(sdf, jo.getString(field));
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取以 Long 格式存储的 Timestamp 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 转换后的 Timestamp，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Timestamp toTimestampInLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
			throws Exception // nl
	{
		return toTimestampInLong(jo, field, null, errHandler);
	}

	/**
	 * 读取以 Long 格式存储的 Timestamp 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 转换后的 Timestamp 或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static Timestamp toTimestampInLong(JSONObject jo, String field, // nl
			Timestamp defaultValue, Consumer<Exception> errHandler) throws Exception // nl
	{
		Timestamp result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = new Timestamp(jo.getLong(field));
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取以 Long 格式存储的 LocalDate 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 转换后的 LocalDate，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static LocalDate toLocalDateInLong(JSONObject jo, String field, Consumer<Exception> errHandler)// nl
			  throws Exception // nl
	{
		return toLocalDateInLong(jo, field, null, errHandler);
	}

	/**
	 * 读取以 Long 格式存储的 LocalDate 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 转换后的 LocalDate 或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static LocalDate toLocalDateInLong(JSONObject jo, String field, // nl
			LocalDate defaultValue, Consumer<Exception> errHandler) throws Exception // nl
	{
		LocalDate result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = TimeUtils.toLocalDate(jo.getLong(field));
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

	/**
	 * 读取以 Long 格式存储的 LocalDateTime 字段（无默认值版本）。
	 *
	 * @param jo         源 JSONObject
	 * @param field      字段名
	 * @param errHandler 异常处理器
	 * @return 转换后的 LocalDateTime，如果不存在或为 null 则返回 null
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static LocalDateTime toLocalDateTimeInLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
			throws Exception // nl
	{
		return toLocalDateTimeInLong(jo, field, null, errHandler);
	}

	/**
	 * 读取以 Long 格式存储的 LocalDateTime 字段（带默认值版本）。
	 *
	 * @param jo           源 JSONObject
	 * @param field        字段名
	 * @param defaultValue 字段不存在时的默认值
	 * @param errHandler   异常处理器
	 * @return 转换后的 LocalDateTime 或默认值
	 * @throws Exception 如果读取失败且 errHandler 为 null
	 */
	public static LocalDateTime toLocalDateTimeInLong(JSONObject jo, String field, // nl
			LocalDateTime defaultValue, Consumer<Exception> errHandler) throws Exception // nl
	{
		LocalDateTime result = defaultValue;
		try {
			if (!jo.isNull(field)) {
				result = TimeUtils.toLocalDateTime(jo.getLong(field));
			}
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return result;
	}

}
