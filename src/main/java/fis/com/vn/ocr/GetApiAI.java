package fis.com.vn.ocr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fis.com.vn.common.StringUtils;
import fis.com.vn.component.ConfigProperties;

@Component
public class GetApiAI {
	@Autowired ConfigProperties configProperties;
	
	int soSanh = 0;
	int liveness = 0;
	int ocr = 0;
	
	public synchronized String getUrlOcr() {
		if(ocr == 0) {
			ocr = 1;
			if(!StringUtils.isEmpty(configProperties.getConfig().getLink_ocr_cmt_cccd_fis()))
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis();
			else if (!StringUtils.isEmpty(configProperties.getConfig().getLink_ocr_cmt_cccd_fis1())) 
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis1();
			else
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis2();
		} else if(ocr == 1) {
			ocr = 2;
			if(!StringUtils.isEmpty(configProperties.getConfig().getLink_ocr_cmt_cccd_fis1()))
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis1();
			else if (!StringUtils.isEmpty(configProperties.getConfig().getLink_ocr_cmt_cccd_fis2())) 
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis2();
			else
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis();
		} else {
			ocr = 0;
			if(!StringUtils.isEmpty(configProperties.getConfig().getLink_ocr_cmt_cccd_fis2()))
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis2();
			else if(!StringUtils.isEmpty(configProperties.getConfig().getLink_ocr_cmt_cccd_fis1()))
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis1();
			else
				return configProperties.getConfig().getLink_ocr_cmt_cccd_fis();
		}
	}
}
