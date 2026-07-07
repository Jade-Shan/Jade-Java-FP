package net.jadedungeon.javautil.json;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonArrayField {

	public static <T> List<T> toValueList(JSONObject outter, String fieldName, // nl
			JsonArrByIdx<T> getObj, Consumer<Exception> errHandler) throws Exception // nl
	{
		List<T> list = new LinkedList<>();
		if (null == outter || null == fieldName || outter.isNull(fieldName)) {
			// skip empty
		} else {
			JSONArray arr = null;
			try {
				arr = outter.getJSONArray(fieldName);
			} catch (Exception e) {
				if (null == errHandler) { throw e; } else { errHandler.accept(e); }
			}
			if (null != arr && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					try {
						T o = getObj.getByIdx(arr, i);
						if (null != o) { list.add(o); }
					} catch (Exception e) {
						if (null == errHandler) { throw e; } else { errHandler.accept(e); }
					}
				}
			}
		}
		return list;
	}

	public static <T> List<T> toObjList(JSONObject outter, String fieldName, // nl
			Function<JSONObject, T> toObj, Consumer<Exception> errHandler) throws Exception // nl
	{
		List<T> list = new LinkedList<>();
		if (null == outter || null == fieldName || outter.isNull(fieldName)) {
			// skip empty
		} else {
			JSONArray arr = null;
			try {
				arr = outter.getJSONArray(fieldName);
			} catch (Exception e) {
				if (null == errHandler) { throw e; } else { errHandler.accept(e); }
			}
			if (null != arr && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					try {
						JSONObject jo = arr.getJSONObject(i);
						if (null != jo) {
							T o = toObj.apply(jo);
							if (null != o) { list.add(o); }
						}
					} catch (Exception e) {
						if (null == errHandler) { throw e; } else { errHandler.accept(e); }
					}
				}
			}
		}
		return list;
	}

}
