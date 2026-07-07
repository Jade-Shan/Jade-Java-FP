package net.jadedungeon.javautil.json;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Consumer;

import org.json.JSONObject;

import net.jadedungeon.javautil.common.TimeUtils;

public class JsonValueField {

	public static Boolean toBoolean(JSONObject jo, String field, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		return toBoolean(jo, field, null, errHandler);
	}

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

	public static Integer toInt(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toInt(jo, field, null, errHandler);
	}

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

	public static Long toLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toLong(jo, field, null, errHandler);
	}

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

	public static Double toDouble(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
			throws Exception // nl
	{
		return toDouble(jo, field, null, errHandler);
	}

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

	public static String toStr(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toStr(jo, field, null, errHandler);
	}

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

	public static Date toDateInLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
		throws Exception // nl
	{
		return toDateInLong(jo, field, null, errHandler);
	}

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

	public static Date toDateInStr(JSONObject jo, String field, SimpleDateFormat sdf, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		return toDateInStr(jo, field, sdf, null, errHandler);
	}

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

	public static Timestamp toTimestampInLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
			throws Exception // nl
	{
		return toTimestampInLong(jo, field, null, errHandler);
	}

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

	public static LocalDate toLocalDateInLong(JSONObject jo, String field, Consumer<Exception> errHandler)// nl
			  throws Exception // nl
	{
		return toLocalDateInLong(jo, field, null, errHandler);
	}

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

	public static LocalDateTime toLocalDateTimeInLong(JSONObject jo, String field, Consumer<Exception> errHandler) // nl
			throws Exception // nl
	{
		return toLocalDateTimeInLong(jo, field, null, errHandler);
	}

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