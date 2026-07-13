package net.jadedungeon.javautil.json;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JsonArrayField —— JSON 数组字段读取工具。
 * <p>
 * 从 {@link JSONObject} 中读取数组字段，并将其元素转换为 Java 对象列表。
 * 支持两种读取模式：
 * </p>
 * <ul>
 *   <li>{@link #toValueList(JSONObject, String, JsonArrByIdx, Consumer)} —— 按索引自定义提取</li>
 *   <li>{@link #toObjList(JSONObject, String, Function, Consumer)} —— 将每个 JSONObject 元素转换为对象</li>
 * </ul>
 *
 * <p>统一使用 {@link Consumer}{@code <Exception>} 错误处理器模式，
 * 如果 errHandler 为 null，异常将直接抛出。</p>
 */
public class JsonArrayField {

	/**
	 * 从 JSONObject 的指定字段读取 JSONArray，按索引将每个元素转换为目标类型。
	 * <p>
	 * 使用 {@link JsonArrByIdx} 函数式接口自定义每个元素的转换逻辑。
	 * </p>
	 *
	 * @param <T>       目标元素类型
	 * @param outter    源 JSONObject
	 * @param fieldName 数组字段名
	 * @param getObj    按索引提取元素的函数
	 * @param errHandler 异常处理器（可为 null）
	 * @return 转换后的对象列表
	 * @throws Exception 如果处理过程中发生异常且 errHandler 为 null
	 */
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

	/**
	 * 从 JSONObject 的指定字段读取 JSONArray，将每个 JSONObject 元素转换为目标类型。
	 *
	 * @param <T>       目标元素类型
	 * @param outter    源 JSONObject
	 * @param fieldName 数组字段名
	 * @param toObj     将 JSONObject 转换为目标对象的函数
	 * @param errHandler 异常处理器（可为 null）
	 * @return 转换后的对象列表
	 * @throws Exception 如果处理过程中发生异常且 errHandler 为 null
	 */
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
