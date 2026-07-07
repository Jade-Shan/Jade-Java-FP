package net.jadedungeon.javautil.json;

import java.util.function.Consumer;
import java.util.function.Function;

import org.json.JSONObject;

import net.jadedungeon.javautil.collection.struct.Either;

public class JsonObjField {

	public static <T> T toObjOpt(JSONObject jo, String field, // nl
			Function<JSONObject, Either<Exception, T>> func, // nl
			Consumer<Exception> errHandler) // nl
	{
		return toObjOpt(jo, field, func, null, errHandler);
	}

	public static <T> T toObjOpt(JSONObject jo, String field, // nl
			Function<JSONObject, Either<Exception, T>> func, T defaultValue, // nl
			Consumer<Exception> errHandler) // nl
	{
		if (null == jo || null == field || null == func || jo.isNull(field)) {
			return defaultValue;
		} else {
			try {
				Either<?, T> result = func.apply(jo.getJSONObject(field));
				return result.isRight() ? result.rightValue(defaultValue) : defaultValue;
			} catch (Exception e) {
				if (null != errHandler) {
					errHandler.accept(e);
				}
				return defaultValue;
			}
		}
	}

}