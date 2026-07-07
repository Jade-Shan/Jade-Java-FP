package net.jadedungeon.javautil.json;

import org.json.JSONArray;

@FunctionalInterface
public interface JsonArrByIdx<T> {

	public T getByIdx(JSONArray arr, int idx) throws Exception;

}