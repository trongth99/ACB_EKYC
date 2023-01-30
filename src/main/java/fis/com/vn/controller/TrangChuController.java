package fis.com.vn.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fis.com.vn.repository.EkycDoanhNghiepRepository;
import fis.com.vn.repository.EkycKysoDtoRepository;
import fis.com.vn.table.ThongKeEkycKyso;

@Controller
public class TrangChuController extends BaseController{
	@Autowired EkycDoanhNghiepRepository ekycDoanhNghiepRepository;
	@Autowired EkycKysoDtoRepository ekycKysoDtoRepository;
	
	@GetMapping(value = "/") 
	public String get(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) throws ParseException {
		String type = allParams.getOrDefault("type", "daily");
		SimpleDateFormat dateFormatSql = new SimpleDateFormat("dd/MM/yyyy");
		String typeUser = req.getSession().getAttribute("username").toString();
		List<ThongKeEkycKyso> thongKeTrangThais = new ArrayList<>();
		if(typeUser.equals("supper_admin")) {
			 thongKeTrangThais = ekycKysoDtoRepository.thongKeTrangThai(dateFormatSql.parse(getFromDate(type)),
					dateFormatSql.parse(getToDate(type)));
		}else {
			thongKeTrangThais = ekycKysoDtoRepository.thongKeTrangThaiUser(dateFormatSql.parse(getFromDate(type)),
					dateFormatSql.parse(getToDate(type)),typeUser);
		}
		
		System.out.println(thongKeTrangThais.size());
		model.addAttribute("thongKeTrangThais", thongKeTrangThais);
		
		return "dashboard/dashboard";
	}
	
	private String getFromDate(String type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		
		if(type.equals("daily")) return dateFormat.format(new Date());
		if(type.equals("weekly")) {
			DateFormat format2 = new SimpleDateFormat("EEE");
			String finalDay = format2.format(new Date());
			
			return getBackDate(finalDay);
		}
		if(type.equals("monthly")) {
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -1);
			return dateFormat.format(calendar.getTime());
		}
		if(type.equals("yearly")) {
			calendar.setTime(new Date());
			calendar.add(Calendar.YEAR, -1);
			return dateFormat.format(calendar.getTime());
		}
		
		return dateFormat.format(new Date());
	}
	private String getBackDate(String day) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if (day.equals("Sat")) {
			calendar.add(Calendar.DAY_OF_MONTH, -6);
			return dateFormat.format(calendar.getTime());
		} else if (day.equals("Fri")) {
			calendar.add(Calendar.DAY_OF_MONTH, -5);
			return dateFormat.format(calendar.getTime());
		} else if (day.equals("Thu")) {
			calendar.add(Calendar.DAY_OF_MONTH, -4);
			return dateFormat.format(calendar.getTime());
		} else if (day.equals("Wed")) {
			calendar.add(Calendar.DAY_OF_MONTH, -3);
			return dateFormat.format(calendar.getTime());
		} else if (day.equals("Tue")) {
			calendar.add(Calendar.DAY_OF_MONTH, -2);
			return dateFormat.format(calendar.getTime());
		} else if (day.equals("Mon")) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			return dateFormat.format(calendar.getTime());
		} else if (day.equals("Sun")) {
			return dateFormat.format(calendar.getTime());
		}
		return "";
	}
	private String getToDate(String type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return dateFormat.format(calendar.getTime());
	}
}
