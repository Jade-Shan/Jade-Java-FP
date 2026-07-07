package net.jadedungeon.javautil.json;


import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonArr2Stream {

	/**
	 * 把JsonArray转为Json的Stream
	 * 
	 * @param jsonArr JsonArray
	 * @return Json的Stream
	 */
	public static Stream<JSONObject> trans2Stream(JSONArray jsonArr) {
		DalJsonArrIterator iterator = new DalJsonArrIterator(jsonArr);
		Spliterator<JSONObject> spliter = Spliterators.spliteratorUnknownSize( // nl
				iterator, Spliterator.IMMUTABLE); // 不可变
		return StreamSupport.stream(spliter, false); // 指定这个流是不能并行处理流
	}

}

/**
 * 辅助类，用于迭代Json数组中的元素
 * 
 * @author qwshan
 *
 */
class DalJsonArrIterator implements Iterator<JSONObject> {

	private JSONArray jsonArr;
	private int idx = 0;

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
