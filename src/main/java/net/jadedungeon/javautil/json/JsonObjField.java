package net.jadedungeon.javautil.json;

import java.util.function.Consumer;
import java.util.function.Function;

import org.json.JSONObject;

import net.jadedungeon.javautil.collection.struct.Either;

/**
 * JsonObjField —— JSON 嵌套对象字段读取工具。
 * <p>
 * 从 {@link JSONObject} 中读取嵌套的 JSON 对象字段，
 * 并使用 {@link Either} 类型来区分解析成功（Right）和失败（Left）的结果。
 * 如果解析失败或字段不存在，返回默认值（defaultValue）。
 * </p>
 */
public class JsonObjField {

	/**
	 * 从 JSONObject 读取嵌套对象字段并转换为目标类型（无默认值版本）。
	 *
	 * @param <T>       目标类型
	 * @param jo        源 JSONObject
	 * @param field     嵌套对象字段名
	 * @param func      将 JSONObject 转换为 {@code Either<Exception, T>} 的解析函数
	 * @param errHandler 异常处理器
	 * @return 解析成功时返回结果对象，失败或不存在时返回 null
	 */
	public static <T> T toObjOpt(JSONObject jo, String field, // nl
			Function<JSONObject, Either<Exception, T>> func, // nl
			Consumer<Exception> errHandler) // nl
	{
		return toObjOpt(jo, field, func, null, errHandler);
	}

	/**
	 * 从 JSONObject 读取嵌套对象字段并转换为目标类型（带默认值版本）。
	 * <p>
	 * 解析逻辑：
	 * </p>
	 * <ol>
	 *   <li>如果 jo、field 为 null 或 func 为 null 或字段不存在，返回 defaultValue</li>
	 *   <li>获取嵌套的 JSONObject 并调用 func 进行解析</li>
	 *   <li>如果 func 返回 {@link net.jadedungeon.javautil.collection.struct.Right}，提取其值</li>
	 *   <li>如果 func 返回 Left 或发生异常，调用 errHandler（如果有）并返回 defaultValue</li>
	 * </ol>
	 *
	 * @param <T>          目标类型
	 * @param jo           源 JSONObject
	 * @param field        嵌套对象字段名
	 * @param func         将 JSONObject 转换为 {@code Either<Exception, T>} 的解析函数
	 * @param defaultValue 解析失败时的默认值
	 * @param errHandler   异常处理器
	 * @return 解析成功时返回结果对象，否则返回 defaultValue
	 */
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
