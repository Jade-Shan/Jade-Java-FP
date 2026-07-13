package net.jadedungeon.javautil.json;


import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JsonArr2Stream —— 将 {@link JSONArray} 转换为 Java 8 {@link Stream}。
 */
public class JsonArr2Stream {

	/**
	 * 把 {@link JSONArray} 转为 {@link JSONObject} 的 {@link Stream}。
	 * <p>
	 * 返回的是非并行的顺序流（不可变），适用于对 JSON 数组元素进行
	 * 函数式 map/filter/reduce 操作。
	 * </p>
	 *
	 * @param jsonArr 源 JSONArray
	 * @return 包含 JSON 对象元素的 Stream
	 */
	public static Stream<JSONObject> trans2Stream(JSONArray jsonArr) {
		DalJsonArrIterator iterator = new DalJsonArrIterator(jsonArr);
		Spliterator<JSONObject> spliter = Spliterators.spliteratorUnknownSize( // nl
				iterator, Spliterator.IMMUTABLE); // 不可变
		return StreamSupport.stream(spliter, false); // 指定这个流是不能并行处理流
	}

}

/**
 * 辅助类，用于迭代 JSON 数组中的元素。
 * <p>
 * 封装了 {@link JSONArray} 的遍历逻辑，实现了 {@link Iterator} 接口，
 * 便于通过 {@link Spliterator} 适配为 Stream。
 * </p>
 */
class DalJsonArrIterator implements Iterator<JSONObject> {

	/** 被迭代的 JSON 数组 */
	private JSONArray jsonArr;
	/** 当前迭代位置索引 */
	private int idx = 0;

	/**
	 * 构造迭代器。
	 *
	 * @param jsonArr 要迭代的 JSONArray
	 */
	public DalJsonArrIterator(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}

	@Override
	public boolean hasNext() {
		return null != jsonArr && idx < jsonArr.length();
	}

	@Override
	public JSONObject next() {
		return jsonArr.getJSONObject(idx++);
	}

	@Override
	public String toString() {
		return null == jsonArr ? "JsonArray:is-null" : // nl
				String.format("JsonArray:[%d/%d]", idx, jsonArr.length());
	}

}
