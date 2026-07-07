package net.jadedungeon.javautil.json;

import java.util.function.Consumer;

import org.json.JSONObject;

import net.jadedungeon.javautil.collection.struct.Either;
import net.jadedungeon.javautil.collection.struct.Left;
import net.jadedungeon.javautil.collection.struct.Right;

public class JobStatus {
	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Either<Exception, JSONObject> toJsonObject(JobStatus rec) {
		try {
			JSONObject jo = new JSONObject();
			ObjValueField.putStrField(jo, "code", rec.code);
			ObjValueField.putStrField(jo, "desc", rec.desc);
			return Right.of(jo);
		} catch (Exception e) {
			return Left.of(e);
		}
	}

	public static Either<Exception, JobStatus> fromJsonObject(JSONObject jo, Consumer<Exception> errHandler) {
		try {
			JobStatus rec = new JobStatus();
			rec.setCode(JsonValueField.toStr(jo, "code", errHandler));
			rec.setDesc(JsonValueField.toStr(jo, "desc", errHandler));
			return Right.of(rec);
		} catch (Exception e) {
			return Left.of(e);
		}
	}

}
