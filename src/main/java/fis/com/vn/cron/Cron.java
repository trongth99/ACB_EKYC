package fis.com.vn.cron;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fis.com.vn.component.ConfigProperties;
import fis.com.vn.repository.BusinessRepository;


@Component
public class Cron {
	private static final Logger LOGGER = LoggerFactory.getLogger(Cron.class);
	
	@Autowired ConfigProperties configProperties;
	@Autowired CheckServiceAi checkServiceAi;
	@Autowired  BusinessRepository businessRepository;
	
	@Scheduled(cron = "0 * * * * *")
	public void layCauHinh() {
		configProperties.resetConfig();
	}
	
	@Scheduled(cron = "0 * * * * *")
	public void checkServiceAi() {
		LOGGER.info("Check URL AI");
		checkServiceAi.start();
	}
	
	@Scheduled(cron = "0 0 7 * * *")
	public void deleteAccount() throws ParseException {
	   String valueDate = configProperties.getConfig().getXoa_tai_khoan_doanh_nghiep();
	   int ngay = -Integer.parseInt(valueDate);
      
		System.out.println("ngay2222: "+ngay);
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, ngay);
        String date = dateFormat.format(new Date());
        SimpleDateFormat dateFormatSql = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date datenew = dateFormatSql.parse(date);
	        businessRepository.deleteAccount(datenew);
	}
}
