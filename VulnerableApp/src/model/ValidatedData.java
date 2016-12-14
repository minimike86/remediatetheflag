package model;

import java.util.List;

import org.json.simple.JSONObject;

public class ValidatedData {

	private JSONObject json;
	private boolean withErrors;
	private List<String> errors;

	public ValidatedData(JSONObject json, boolean withErrors, List<String> errors) {
		this.json = json;
		this.withErrors = withErrors;
		this.errors = errors;
	}
	
	public JSONObject getJson() {
		return json;
	}
	public void setJson(JSONObject json) {
		this.json = json;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public boolean isWithErrors() {
		return withErrors;
	}
	public void setWithErrors(boolean withErrors) {
		this.withErrors = withErrors;
	}
}
