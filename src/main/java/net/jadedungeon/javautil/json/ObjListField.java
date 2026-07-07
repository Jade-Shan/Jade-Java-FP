package net.jadedungeon.javautil.json;

import java.util.List;
import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;

public class ObjListField {

	public static <T> void putValueJsonArr(JSONObject jo, String field, List<T> list) {
		putValueJsonArr(jo, field, list, false, true);
	}

	public static <T> void putValueJsonArr(JSONObject jo, String field, List<T> list, // nl
			boolean dropNullField, boolean dropNullElem) // nl
	{
		if (null == jo || null == field) {
			// no nothing
		} else if (null == list) {
			if (dropNullField) {
				Object nn = null;
				jo.put(field, nn);
			} else {
				jo.put(field, new JSONArray());
			}
		} else if (null != list) {
			JSONArray arr = new JSONArray();
			jo.put(field, arr);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					T v = list.get(i);
					if (null != v) {
						arr.put(v);
					} else if (!dropNullElem) {
						arr.put(JSONObject.NULL);
					} else {
						// skip this elem
					}
				}
			}
		}
	}

	public static <T> void putObjJsonArr(JSONObject jo, String field, List<T> list, // nl
			Function<T, JSONObject> toJsonObj) // nl
	{
		putObjJsonArr(jo, field, list, toJsonObj, false, true);
	}

	public static <T> void putObjJsonArr(JSONObject jo, String field, List<T> list, // nl
			Function<T, JSONObject> toJsonObj, boolean dropNullField, boolean dropNullElem) // nl
	{
		if (null == jo || null == field) {
			// no nothing
		} else if (null == list) {
			if (dropNullField) {
				Object nn = null;
				jo.put(field, nn);
			} else {
				jo.put(field, new JSONArray());
			}
		} else if (null != list) {
			JSONArray arr = new JSONArray();
			jo.put(field, arr);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					T o = list.get(i);
					if (null != o) {
						arr.put(toJsonObj.apply(o));
					} else if (!dropNullElem) {
						arr.put(JSONObject.NULL);
					} else {
						// skip this elem
					}
				}
			}
		}
	}

}
