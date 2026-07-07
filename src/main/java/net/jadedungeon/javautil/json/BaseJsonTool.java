package net.jadedungeon.javautil.json;

import java.util.function.Consumer;

import org.json.JSONObject;

public class BaseJsonTool {

	public static JSONObject toJsonObj(String str, JSONObject defaultValue, // nl
			Consumer<Exception> errHandler) throws Exception // nl
	{
		JSONObject jo = defaultValue;
		try {
			jo = new JSONObject(str);
		} catch (Exception e) {
			if (null == errHandler) { throw e; } else { errHandler.accept(e); }
		}
		return jo;
	}

}