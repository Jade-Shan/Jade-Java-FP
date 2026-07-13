package net.jadedungeon.javautil.json;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Function;

import org.json.JSONObject;

import net.jadedungeon.javautil.common.TimeUtils;

/**
 * ObjValueField —— JSON 标量值字段写入工具。
 * <p>
 * 将各种 Java 基本类型值安全地写入 {@link JSONObject} 的指定字段。
 * 每种类型都提供了两个重载版本：
 * </p>
 * <ul>
 *   <li><b>简化版</b>：不丢弃 null 字段，null 值写入 {@link JSONObject#NULL}</li>
 *   <li><b>完整版</b>：支持 defaultValue 和 dropNullField 参数</li>
 * </ul>
 *
 * <p>支持的类型：Boolean、Integer、Long、Double、String、
 * Date（Long 和 String 两种格式）、Timestamp、LocalDate、LocalDateTime，
 * 以及通过 {@link #putObjectField} 写入自定义嵌套对象。</p>
 *
 * <p>dropNullField 参数说明：</p>
 * <ul>
 *   <li>{@code true} —— 值为 null 时不在 JSON 中写入该字段</li>
 *   <li>{@code false} —— 值为 null 时写入 {@link JSONObject#NULL}</li>
 * </ul>
 */
public class ObjValueField {

	/**
	 * 写入 Boolean 字段（简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的值
	 */
	public static void putBooleanField(JSONObject jo, String field, Boolean value) {
		putBooleanField(jo, field, value, null, false);
	}

	/**
	 * 写入 Boolean 字段（完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的值
	 * @param defaultValue  值为 null 时使用的默认值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putBooleanField(JSONObject jo, String field, Boolean value, // nl
			Boolean defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			if (null != value) {
				jo.put(field, value);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 Integer 字段（简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的值
	 */
	public static void putIntField(JSONObject jo, String field, Integer value) {
		putIntField(jo, field, value, null, false);
	}

	/**
	 * 写入 Integer 字段（完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的值
	 * @param defaultValue  值为 null 时使用的默认值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putIntField(JSONObject jo, String field, Integer value, // nl
			Integer defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			if (null != value) {
				jo.put(field, value);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 Long 字段（简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的值
	 */
	public static void putLongField(JSONObject jo, String field, Long value) {
		putLongField(jo, field, value, null, false);
	}

	/**
	 * 写入 Long 字段（完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的值
	 * @param defaultValue  值为 null 时使用的默认值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putLongField(JSONObject jo, String field, Long value, // nl
			Long defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			if (null != value) {
				jo.put(field, value);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 Double 字段（简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的值
	 */
	public static void putDoubleField(JSONObject jo, String field, Double value) {
		putDoubleField(jo, field, value, null, false);
	}

	/**
	 * 写入 Double 字段（完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的值
	 * @param defaultValue  值为 null 时使用的默认值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putDoubleField(JSONObject jo, String field, Double value, // nl
			Double defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			if (null != value) {
				jo.put(field, value);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 String 字段（简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的值
	 */
	public static void putStrField(JSONObject jo, String field, String value) {
		putStrField(jo, field, value, null, false);
	}

	/**
	 * 写入 String 字段（完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的值
	 * @param defaultValue  值为 null 时使用的默认值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putStrField(JSONObject jo, String field, String value, // nl
			String defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			if (null != value) {
				jo.put(field, value);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 Date 字段（以 Long/Unix 时间戳格式，简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的 Date
	 */
	public static void putDateFieldInLong(JSONObject jo, String field, Date value) {
		putDateFieldInLong(jo, field, value, null, false);
	}

	/**
	 * 写入 Date 字段（以 Long/Unix 时间戳格式，完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的 Date
	 * @param defaultValue  值为 null 时写入的默认 Long 值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putDateFieldInLong(JSONObject jo, String field, Date value, // nl
			Long defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			if (null != value) {
				jo.put(field, value.getTime());
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 Date 字段（以格式化字符串格式，简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的 Date
	 * @param sdf   日期格式化器
	 */
	public static void putDateFieldInStr(JSONObject jo, String field, Date value, SimpleDateFormat sdf) {
		putDateFieldInStr(jo, field, value, sdf, null, false);
	}

	/**
	 * 写入 Date 字段（以格式化字符串格式，完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的 Date
	 * @param sdf           日期格式化器
	 * @param defaultValue  值为 null 时写入的默认字符串
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putDateFieldInStr(JSONObject jo, String field, Date value, SimpleDateFormat sdf,
			String defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field && null != sdf) {
			if (null != value) {
				jo.put(field, sdf.format(value));
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 Timestamp 字段（以 Long 格式，简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的 Timestamp
	 */
	public static void putTimestampFieldInLong(JSONObject jo, String field, Timestamp value) {
		putTimestampFieldInLong(jo, field, value, null, false);
	}

	/**
	 * 写入 Timestamp 字段（以 Long 格式，完整参数版本）。
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的 Timestamp
	 * @param defaultValue  值为 null 时写入的默认 Long 值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putTimestampFieldInLong(JSONObject jo, String field, Timestamp value, // nl
			Long defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			if (null != value) {
				jo.put(field, value.getTime());
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 LocalDate 字段（以 Long 格式，简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的 LocalDate
	 */
	public static void putLocalDateFieldInLong(JSONObject jo, String field, LocalDate value) {
		putLocalDateFieldInLong(jo, field, value, null, false);
	}

	/**
	 * 写入 LocalDate 字段（以 Long 格式，完整参数版本）。
	 * <p>
	 * 内部通过 {@link TimeUtils#toLong(LocalDate)} 进行转换。
	 * </p>
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的 LocalDate
	 * @param defaultValue  值为 null 或转换失败时写入的默认 Long 值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putLocalDateFieldInLong(JSONObject jo, String field, LocalDate value, //nl
			Long defaultValue, boolean dropNullField) //
	{
		if (null != jo && null != field) {
			Long n = null;
			if (null != value) {
				try {
					n = TimeUtils.toLong(value);
				} catch (Exception e) {
					n = null;
				}
			}
			if (null != n) {
				jo.put(field, n);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 写入 LocalDateTime 字段（以 Long 格式，简化版）。
	 *
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的 LocalDateTime
	 */
	public static void putLocalDateTimeFieldInLong(JSONObject jo, String field, LocalDateTime value) {
		putLocalDateTimeFieldInLong(jo, field, value, null, false);
	}

	/**
	 * 写入 LocalDateTime 字段（以 Long 格式，完整参数版本）。
	 * <p>
	 * 内部通过 {@link TimeUtils#toLong(LocalDateTime)} 进行转换。
	 * </p>
	 *
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的 LocalDateTime
	 * @param defaultValue  值为 null 或转换失败时写入的默认 Long 值
	 * @param dropNullField 为 true 时，值为 null 且无默认值则不写入字段
	 */
	public static void putLocalDateTimeFieldInLong(JSONObject jo, String field, LocalDateTime value, // nl
			Long defaultValue, boolean dropNullField) // nl
	{
		if (null != jo && null != field) {
			Long n = null;
			if (null != value) {
				try {
					n = TimeUtils.toLong(value);
				} catch (Exception e) {
					n = null;
				}
			}
			if (null != n) {
				jo.put(field, n);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

	/**
	 * 将自定义对象转换为 JSONObject 并写入（简化版）。
	 *
	 * @param <T>   对象类型
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param value 要写入的对象
	 * @param func  将对象转换为 JSONObject 的函数
	 */
	public static <T> void putObjectField(JSONObject jo, String field, T value, // nl
			Function<T, JSONObject> func) //
	{
		putObjectField(jo, field, value, func, null, false);
	}

	/**
	 * 将自定义对象转换为 JSONObject 并写入（完整参数版本）。
	 *
	 * @param <T>           对象类型
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param value         要写入的对象
	 * @param func          将对象转换为 JSONObject 的函数
	 * @param defaultValue  转换失败时使用的默认 JSONObject
	 * @param dropNullField 为 true 时，转换失败且无默认值则不写入字段
	 */
	public static <T> void putObjectField(JSONObject jo, String field, T value, // nl
			Function<T, JSONObject> func, JSONObject defaultValue, boolean dropNullField) //
	{
		if (null != jo && null != field) {
			JSONObject obj = null;
			if (null != value && null != func) {
				try {
					obj = func.apply(value);
				} catch (Exception e) {
					obj = null;
				}
			}
			if (null != obj) {
				jo.put(field, obj);
			} else if (null != defaultValue) {
				jo.put(field, defaultValue);
			} else if (dropNullField) {
				// skip null field
			} else {
				jo.put(field, JSONObject.NULL);
			}
		}
	}

}
