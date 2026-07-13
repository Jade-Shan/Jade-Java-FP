package net.jadedungeon.javautil.json;

import java.util.List;
import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * ObjListField —— JSON 数组字段写入工具。
 * <p>
 * 将 Java {@link List} 写入 {@link JSONObject} 的指定字段中。
 * 支持两种写入模式：
 * </p>
 * <ul>
 *   <li>{@link #putValueJsonArr(JSONObject, String, List)} —— 写入标量值列表</li>
 *   <li>{@link #putObjJsonArr(JSONObject, String, List, Function)} —— 将对象列表转换为 JSON 数组</li>
 * </ul>
 *
 * <p>支持通过 dropNullField 和 dropNullElem 参数控制 null 的处理方式。</p>
 */
public class ObjListField {

	/**
	 * 将标量值列表写入 JSONObject 的指定字段（简化版，不丢弃 null 字段，丢弃 null 元素）。
	 *
	 * @param <T>   元素类型
	 * @param jo    目标 JSONObject
	 * @param field 字段名
	 * @param list  要写入的值列表
	 */
	public static <T> void putValueJsonArr(JSONObject jo, String field, List<T> list) {
		putValueJsonArr(jo, field, list, false, true);
	}

	/**
	 * 将标量值列表写入 JSONObject 的指定字段（完整参数版本）。
	 *
	 * @param <T>           元素类型
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param list          要写入的值列表
	 * @param dropNullField 为 true 时，list 为 null 则不写入该字段
	 * @param dropNullElem  为 true 时，跳过列表中的 null 元素
	 */
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

	/**
	 * 将对象列表转换为 JSON 数组并写入（简化版）。
	 *
	 * @param <T>      元素类型
	 * @param jo       目标 JSONObject
	 * @param field    字段名
	 * @param list     要写入的对象列表
	 * @param toJsonObj 将对象转换为 JSONObject 的函数
	 */
	public static <T> void putObjJsonArr(JSONObject jo, String field, List<T> list, // nl
			Function<T, JSONObject> toJsonObj) // nl
	{
		putObjJsonArr(jo, field, list, toJsonObj, false, true);
	}

	/**
	 * 将对象列表转换为 JSON 数组并写入（完整参数版本）。
	 *
	 * @param <T>           元素类型
	 * @param jo            目标 JSONObject
	 * @param field         字段名
	 * @param list          要写入的对象列表
	 * @param toJsonObj     将对象转换为 JSONObject 的函数
	 * @param dropNullField 为 true 时，list 为 null 则不写入该字段
	 * @param dropNullElem  为 true 时，跳过列表中的 null 元素
	 */
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
