package net.jadedungeon.javautil.json;

import java.util.function.Consumer;

import org.json.JSONObject;

/**
 * JSON 基础工具 —— 将字符串安全地转换为 {@link JSONObject}。
 */
public class BaseJsonTool {

	/**
	 * 将 JSON 字符串转换为 JSONObject。
	 * <p>
	 * 提供错误处理机制：如果解析失败且 errHandler 不为 null，则调用错误处理器
	 * 并返回默认值；如果 errHandler 为 null，则直接抛出异常。
	 * </p>
	 *
	 * @param str          输入的 JSON 字符串
	 * @param defaultValue 解析失败时的默认返回值
	 * @param errHandler   异常处理器（可为 null，为 null 时直接抛出异常）
	 * @return 解析得到的 JSONObject，或解析失败时的默认值
	 * @throws Exception 如果解析失败且 errHandler 为 null
	 */
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
