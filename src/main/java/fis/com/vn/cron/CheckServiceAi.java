package fis.com.vn.cron;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fis.com.vn.component.ConfigProperties;
import fis.com.vn.repository.ConfigRepository;
import fis.com.vn.table.Config;

@Component
public class CheckServiceAi {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckServiceAi.class);
	
	@Autowired ConfigProperties configProperties;
	@Autowired ConfigRepository configRepository;
	public void start() {
		Config configOcr2 = configRepository.findByMa("link_ocr_cmt_cccd_fis2");
		Config configOcr1 = configRepository.findByMa("link_ocr_cmt_cccd_fis1");
		Config configOcr = configRepository.findByMa("link_ocr_cmt_cccd_fis");
		
		boolean checkOcr = checkService(configOcr, "Nice to meet you!");
		boolean checkOcr1 = checkService(configOcr1, "Nice to meet you!");
		boolean checkOcr2 = checkService(configOcr2, "Nice to meet you!");
		
		update(configOcr1, checkOcr1);
		update(configOcr2, checkOcr2);
		update(configOcr, checkOcr);
	}
	
	private void update(Config config, boolean check) {
		if(config != null) {
			if(check) {
				config.setTrangThai(1);
				configRepository.save(config);
			} else {
				LOGGER.info("URL AI ERROR: {}", config.getGiaTri());
				config.setTrangThai(0);
				configRepository.save(config);
			}
		}
	}
	
	int  timeOutRequest = 8*1000;
	private  RequestConfig.Builder timeout() {
		RequestConfig.Builder requestConfig = RequestConfig.custom();
		requestConfig.setConnectTimeout(timeOutRequest);
		requestConfig.setConnectionRequestTimeout(timeOutRequest);
		requestConfig.setSocketTimeout(timeOutRequest);
		
		return requestConfig;
	}
	private boolean checkService(Config config, String textCompare) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet uploadFile = new HttpGet(config.getGiaTri().replaceAll("/api/[\\w\\W]*", ""));
			uploadFile.setConfig(timeout().build());
	
			CloseableHttpResponse response = httpClient.execute(uploadFile);
			HttpEntity responseEntity = response.getEntity();
			String text = IOUtils.toString(responseEntity.getContent(), StandardCharsets.UTF_8.name());
			System.out.println(text);
			if(text.equals(textCompare)) return true;
		} catch (Exception e) {
		}
		return false;
	}
}
