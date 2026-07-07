package net.jadedungeon.javautil.json;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Function;

import org.json.JSONObject;

import net.jadedungeon.javautil.common.TimeUtils;

public class ObjValueField {

	public static void putBooleanField(JSONObject jo, String field, Boolean value) {
		putBooleanField(jo, field, value, null, false);
	}

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

	public static void putIntField(JSONObject jo, String field, Integer value) {
		putIntField(jo, field, value, null, false);
	}

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

	public static void putLongField(JSONObject jo, String field, Long value) {
		putLongField(jo, field, value, null, false);
	}

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

	public static void putDoubleField(JSONObject jo, String field, Double value) {
		putDoubleField(jo, field, value, null, false);
	}

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

	public static void putStrField(JSONObject jo, String field, String value) {
		putStrField(jo, field, value, null, false);
	}

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

	public static void putDateFieldInLong(JSONObject jo, String field, Date value) {
		putDateFieldInLong(jo, field, value, null, false);
	}

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

	public static void putDateFieldInStr(JSONObject jo, String field, Date value, SimpleDateFormat sdf) {
		putDateFieldInStr(jo, field, value, sdf, null, false);
	}

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

	public static void putTimestampFieldInLong(JSONObject jo, String field, Timestamp value) {
		putTimestampFieldInLong(jo, field, value, null, false);
	}

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

	public static void putLocalDateFieldInLong(JSONObject jo, String field, LocalDate value) {
		putLocalDateFieldInLong(jo, field, value, null, false);
	}

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

	public static void putLocalDateTimeFieldInLong(JSONObject jo, String field, LocalDateTime value) {
		putLocalDateTimeFieldInLong(jo, field, value, null, false);
	}

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

	public static <T> void putObjectField(JSONObject jo, String field, T value, // nl
			Function<T, JSONObject> func) //
	{
		putObjectField(jo, field, value, func, null, false);
	}

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