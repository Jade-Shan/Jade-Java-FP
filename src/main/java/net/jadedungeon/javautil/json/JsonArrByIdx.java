package net.jadedungeon.javautil.json;

import org.json.JSONArray;

/**
 * 函数式接口 —— 按索引从 {@link JSONArray} 中提取元素。
 * <p>
 * 用于 {@link JsonArrayField#toValueList} 方法，允许自定义每个数组元素的解析逻辑。
 * </p>
 *
 * @param <T> 提取结果的目标类型
 */
@FunctionalInterface
public interface JsonArrByIdx<T> {

	/**
	 * 从 JSONArray 的指定索引位置提取元素并转换为目标类型。
	 *
	 * @param arr JSON 数组
	 * @param idx 元素索引
	 * @return 转换后的目标类型对象
	 * @throws Exception 如果提取或转换过程中发生错误
	 */
	public T getByIdx(JSONArray arr, int idx) throws Exception;

}
