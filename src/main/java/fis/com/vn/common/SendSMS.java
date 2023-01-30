package fis.com.vn.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fis.com.vn.component.ConfigProperties;
import fis.com.vn.entities.RespApi;
import fis.com.vn.exception.ErrorException;

@Component
public class SendSMS {
	@Autowired ConfigProperties configProperties;
	private static final Logger LOGGER = LoggerFactory.getLogger(SendSMS.class);
	
	public String postRequestSMS(String phone, String message) throws ErrorException {
		RespApi respApi = new RespApi();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Username", configProperties.getConfig().getUsername_sms());
			jsonObject.put("Password", configProperties.getConfig().getPassword_sms());
			jsonObject.put("PhoneNumber", phone);
			jsonObject.put("PrefixId", "PTFTAICHINH");
			jsonObject.put("CommandCode", "PTFTAICHINH");
			jsonObject.put("RequestId", "0");
			jsonObject.put("MsgContent", message);
			jsonObject.put("MsgContentTypeId", 0);
			jsonObject.put("FeeTypeId", 0);
			
			LOGGER.info("SMS send phone: "+ phone);
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost("http://apiv2.incomsms.vn/MtService/SendSms");
			StringEntity params = new StringEntity(jsonObject.toString(), "UTF-8");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			
			String responseString = new BasicResponseHandler().handleResponse(response);
			LOGGER.info("SMS response str: "+ responseString);
			JSONObject object = new JSONObject(responseString);
			LOGGER.info("SMS response: "+ object.toString());
			if(!object.getString("StatusCode").equals("1")) throw new Exception("Không gửi được tin nhắn");
			
			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error send SMS: "+ e);
			respApi.setStatus(400);
			respApi.setMessage("Lỗi hệ thống");
			throw new ErrorException("Không gửi được tin nhắn");
		}
	}
}
