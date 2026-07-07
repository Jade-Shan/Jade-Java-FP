package net.jadedungeon.javautil.json;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONObject;

import net.jadedungeon.javautil.collection.struct.Either;
import net.jadedungeon.javautil.collection.struct.Left;
import net.jadedungeon.javautil.collection.struct.Right;
import net.jadedungeon.javautil.common.TimeUtils;

public class UserModel extends DBModel {

	private Boolean checked;
	private Integer count;
	private Long userId;
	private Double progress;
	private String cardNumber;

	private Timestamp stampTime;
	private Date returnTime ;
	private Date markTime ;
	private LocalDate birthday;
	private LocalDateTime sendTime;

	private JobStep jobStep;

	private List<String> brands;
	private List<JobStatus> jobStatus;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}

	public List<String> getBrands() {
		return brands;
	}

	public void setBrands(List<String> brands) {
		this.brands = brands;
	}

	public List<JobStatus> getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(List<JobStatus> jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public Timestamp getStampTime() {
		return stampTime;
	}

	public void setStampTime(Timestamp stampTime) {
		this.stampTime = stampTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Date getMarkTime() {
		return markTime;
	}

	public void setMarkTime(Date markTime) {
		this.markTime = markTime;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public JobStep getJobStep() {
		return jobStep;
	}

	public void setJobStep(JobStep jobStep) {
		this.jobStep = jobStep;
	}

	public static Either<Exception, JSONObject> toJsonObject(UserModel rec) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.FMT_DATETIME);
			JSONObject jo = DBModel.fields2JSON(rec);
			// value type field 
			ObjValueField.putBooleanField(jo, "checked"   , rec.checked   );
			ObjValueField.putIntField    (jo, "count"     , rec.count     );
			ObjValueField.putLongField   (jo, "userId"    , rec.userId    );
			ObjValueField.putDoubleField (jo, "progress"  , rec.progress  );
			ObjValueField.putStrField    (jo, "cardNumber", rec.cardNumber);
			// time type field
			ObjValueField.putDateFieldInLong         (jo, "returnTime", rec.returnTime);
			ObjValueField.putTimestampFieldInLong    (jo, "stampTime" , rec.stampTime);
			ObjValueField.putLocalDateFieldInLong    (jo, "birthday"  , rec.birthday );
			ObjValueField.putLocalDateTimeFieldInLong(jo, "sendTime"  , rec.sendTime );
			ObjValueField.putDateFieldInStr          (jo, "markTime"  , rec.markTime, sdf);
			// object type field
			ObjValueField.putObjectField (jo, "jobStep", rec.jobStep, // nl
					v -> JobStep.toJsonObject(v).rightValue(null));
			// value element list
			ObjListField.putValueJsonArr(jo, "brands", rec.brands);
			// obj element list
			ObjListField.putObjJsonArr(jo, "jobStatus", rec.jobStatus, // nl
					o -> JobStatus.toJsonObject(o).rightValue(null));
			return Right.of(jo);
		} catch (Exception e) {
			return Left.of(e);
		}
	}

	public static Either<Exception, UserModel> fromJsonObject(JSONObject jo, // nl
			Consumer<Exception> errHandler) // nl
	{
		UserModel rec = new UserModel();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.FMT_DATETIME);
			DBModel.fieldsFromJson(jo, rec, errHandler);
			// value type field 
			rec.checked    = JsonValueField.toBoolean(jo, "checked"   , errHandler);
			rec.count      = JsonValueField.toInt    (jo, "count"     , errHandler);
			rec.userId     = JsonValueField.toLong   (jo, "userId"    , errHandler);
			rec.progress   = JsonValueField.toDouble (jo, "progress"  , errHandler);
			rec.cardNumber = JsonValueField.toStr    (jo, "cardNumber", errHandler);
			// time type field
			rec.returnTime = JsonValueField.toDateInLong         (jo, "returnTime", errHandler);
			rec.stampTime  = JsonValueField.toTimestampInLong    (jo, "stampTime" , errHandler);
			rec.birthday   = JsonValueField.toLocalDateInLong    (jo, "birthday"  , errHandler);
			rec.sendTime   = JsonValueField.toLocalDateTimeInLong(jo, "sendTime"  , errHandler);
			rec.markTime   = JsonValueField.toDateInStr(jo, "markTime"  , sdf, errHandler);
			// object type field
			rec.jobStep   = JsonObjField.<JobStep>toObjOpt(jo, "jobStep", // nl
					o -> JobStep.fromJsonObject(o, errHandler), errHandler);
			// value element list
			rec.brands = JsonArrayField.toValueList(jo, "brands", // nl
					(arr, idx) -> arr.getString(idx), errHandler);
			// obj element list
			rec.jobStatus = JsonArrayField.<JobStatus>toObjList(jo, "jobStatus", // nl
					json -> JobStatus.fromJsonObject(json, errHandler).rightValue(null), errHandler);
			return Right.of(rec);
		} catch (Exception e) {
			return Left.of(e);
		}
	}

}
