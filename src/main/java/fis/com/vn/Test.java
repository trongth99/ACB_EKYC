package fis.com.vn;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import fis.com.vn.api.entities.Params;
import fis.com.vn.common.Utils;

public class Test {
	public static void main(String[] args) throws JsonMappingException {
		Params params = new Params();
		params.setAnhTinNhan("aaaa");
		
		Params params2 = new Params();
		params2.setAnhHoaDon("bbb");
		
		updateObjectToObject(params, params2);
		
		System.out.println(new Gson().toJson(params));
	}
	
	public static <T> void updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.updateValue(source, objectEdit);
	}
}
