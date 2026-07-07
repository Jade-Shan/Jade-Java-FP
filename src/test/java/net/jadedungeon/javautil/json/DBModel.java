package net.jadedungeon.javautil.json;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import org.json.JSONObject;

public abstract class DBModel {

	private Long id;
	private LocalDateTime lastChangeTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getLastChangeTime() {
		return lastChangeTime;
	}

	public void setLastChangeTime(LocalDateTime lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}

	
	public static JSONObject fields2JSON(DBModel rec) {
		JSONObject jo = new JSONObject();
		ObjValueField.putLongField(jo, "id",  rec.id, null, false);
		ObjValueField.putLocalDateTimeFieldInLong(jo, "lastChangeTime", rec.lastChangeTime);
		return jo;
	}
	
	public static void fieldsFromJson(JSONObject jo, DBModel rec, Consumer<Exception> errHandler) {
		try {
			rec.id =  JsonValueField.toLong(jo, "id", null, errHandler);
			rec.lastChangeTime =  (JsonValueField.toLocalDateTimeInLong( // nl
					jo, "lastChangeTime", null, errHandler));
		} catch (Exception e) {
			errHandler.accept(e);
		}
	}
	
}
