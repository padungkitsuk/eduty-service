package com.streamit.application.simulate.data;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.streamit.application.dto.CorrectData;
import com.streamit.application.dto.CorrectDetail;
import com.streamit.application.dto.CorrectDetailAddress;
import com.streamit.application.dto.CorrectDetailForm;
import com.streamit.application.dto.SelectData;
import com.streamit.application.service.eduty.EDutyService;
import com.streamit.application.service.master.MasterDataService;
import com.streamit.others.constant.SQLConstantOperType;
import com.streamit.others.constant.SQLConstantWhereType;
import com.streamit.others.dao.InquiryDAO;
import com.streamit.others.jdbc.SearchConditionValues;
import com.streamit.others.jdbc.SearchCriteria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public interface RDSimulate {

	Map<String, Object> simulateDuty() throws Exception;

	String simulateAddress();
}

@Slf4j
@Service
class RDSimulateImp implements RDSimulate {

	@Autowired
	private InquiryDAO controlNumberInquiryDAO;
	@Autowired
	private EDutyService edutyService;
	@Autowired
	private MasterDataService masterDataService;

	public Map<String, Object> simulateDuty() throws Exception {
		Map<String, Object> result = new HashMap<>();
		List<CorrectData> list_data = new ArrayList<>();
		List<CorrectDetailForm> list_detail = new ArrayList<>();

		Faker faker = new Faker();
		Integer[] list_count = { 0, 1, 2, 3, 4 };
		Integer count = list_count[new Random().nextInt(list_count.length)];

		Double[] list_duty = { 500.0, 1000.0, 1500.0 };
		String[] list_prefix = { "นาย", "นาง", "นางสาว" };
		Integer[] list_moo = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		Integer lot_no = this.getRunningNoBy("lot_");
		Integer yyyy_no = this.getRunningNoBy("2565/");
		// log.info("yyyy_no={}",yyyy_no);
		Long time = new Date().getTime();

		String date_str = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		for (int i = 0; i < count; i++) {
			lot_no++;
			time++;

			CorrectData data = new CorrectData();
			data.setNo(i + 1);
			data.setId("ID-" + time);
			data.setTotalDoc(20);
			data.setLotName("lot_" + lot_no);
			data.setApproveStatus("W");
			Double totalDuty = list_duty[new Random().nextInt(list_duty.length)];
			data.setTotalDuty(totalDuty);
			data.setTotalDubDutyAmount(0.0);
			data.setTotalPayment(totalDuty);

			Integer no = 0;
			for (Double k = 0.0; k < data.getTotalDuty(); k += 100) {
				no++;
				yyyy_no++;

				CorrectDetailForm detail = new CorrectDetailForm();
				detail.setId(data.getId());
				detail.setNo(no);
				detail.setInstInfoId("2565/" + yyyy_no);
				detail.setTaxPayerId(String.valueOf(faker.number().randomNumber(13, false)));
				String firstName = this.simulateFirstName();
				String lastName = this.simulateLastName();
				detail.setFullName(firstName + " " + lastName);
				detail.setTotalDuty(100.0);
				detail.setTotalDubDutyAmount(0.0);
				detail.setTotalPayment(100.0);
				detail.setContractNo("test01");
				detail.setContractStartDate(date_str);
				detail.setContractEndDate(date_str);
				detail.setBranchNo("1234");
				detail.setBranchType("1");
				detail.setContractWith("ผู้เช่า");
				detail.setPaymentEndDate(date_str);

				CorrectDetailAddress address = new CorrectDetailAddress();
				address.setPrefixCode("");
				String prefix = list_prefix[new Random().nextInt(list_prefix.length)];
				address.setPrefix(prefix);
				address.setFirstName(firstName);
				address.setLastName(lastName);
				address.setAddrNumber(faker.address().buildingNumber());
				address.setMoo(String.valueOf(list_moo[new Random().nextInt(list_moo.length)]));

				String[] arr_add = this.simulateAddress().split("\\|");
				address.setTambolCode(arr_add[0]);
				address.setTambol(arr_add[1]);
				address.setAmphurCode(arr_add[2]);
				address.setAmphur(arr_add[3]);
				address.setProvinceCode(arr_add[4]);
				address.setProvince(arr_add[5]);
				address.setZipcode(arr_add[6]);

				detail.setAddress(address);

				list_detail.add(detail);

			}

			list_data.add(data);

		}

		Map<String, Object> res1 = edutyService.InsertDuty(list_data);
		edutyService.InsertDutyDetail(list_detail);

		result.put("count", count);
		result.put("check", res1.get("check"));

		return result;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Integer getRunningNoBy(String key_name) throws Exception {

		int year_int = Calendar.getInstance().get(Calendar.YEAR);
		String year_two = String.valueOf(year_int + 543).substring(2, 4);

		SearchCriteria searchCriteria = new SearchCriteria();
		List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();

		try {
			criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "YEAR_NO", SQLConstantOperType.EQUALS,
					new Object[] { year_two }));
			criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "KEY_NAME",
					SQLConstantOperType.EQUALS, new Object[] { key_name }));

			searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[] {}));

			List<SelectData> lists = controlNumberInquiryDAO.findAll(searchCriteria);
			// log.info(new Gson().toJson(lists));

			Integer id = null;
			if (lists.size() > 0) {
				id = lists.get(0).getId();
			}

			return id;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	@SuppressWarnings("unused")
	private String simulateFirstName() {
		String[] list = { "กชมน", "เกริกวิทย์", "เกวลิน", "ไกรวิชญ์", "ขวัญจิรา", "เขมินทร์", "คณนาถ", "ครองขวัญ","คุณภัทร", "คุณานนท์", "จอมขวัญ", "จักรดุลย์", "จารุพิชญ์", "จิตติพัฒน์", "เจตปรียา", "ฉันชนก","ฉันทิต", "ชนกันต์", "ชนมน", "ชนิตพล", "ชรินทร์ทิพย์", "เชิญขวัญ", "ญาณพัฒน์", "ญาณิดา", "ฐานนันท์","ฐิตารีย์", "ฐิติวัฒน์", "ฐีรวัฒน์", "ณัชชนม์", "ณัฏฐกิตติ์", "ณัฐชยาน์", "ณัฐภูมินทร์", "ณิขนันทน์","ดนุนันท์", "ดรัณภพ", "ดารินทร์", "ตุลธร", "เตชภณ", "เตวิช", "ถิรคุณ", "ถิรมน", "ทัตธน", "ทัตพิชา","ทินภัทร", "ทิพย์รัตน์", "ธนวิชญ์", "ธนัชพร", "ธมลวรรณ", "ธรรมปพน", "ธัญธร", "ธีร์วรา", "นนท์ปวิธ","นรกมล", "นฤชิต", "นวินดา", "นิจจารีย์", "นิพพิชฌน์", "นิพิฐพนธ์", "บุณยวีย์", "เบญญาภา","ปกรณ์เกียรติ", "ปพนธีร์", "ปภินวิทย์", "ประณยา", "ปรีณาพรรณ", "ปิยพัทธ์", "ปูริดา", "ผดุงเดช", "ผริตา","ฝนทิพย์", "พชรดนัย", "พชรพล", "พนธกร", "พนิตอนงค์", "พรกรัณย์", "พลวรรธน์", "พักตร์พริ้ง", "พัฒน์นรี","ภพนิพิฐ", "ภวรัญชน์", "ภัทรกันย์", "ภาณุวิชญ์", "ภิญญาพัชญ์", "ภีมวัจน์", "ภูริณัฐ", "มณฑกาญจน์","มนพัทธ์", "มานเมตต์", "เมธพนธ์", "ยลรดี", "ยิ่งคุณ", "รจิตพิชญ์", "รชานนท์", "รณพร", "รติบดี","รัตนพล", "ไรวินท์", "ลัทธพรรณ", "ลัลน์ลลิต", "ล้อมเดช", "วชิรวิทย์", "วรดนัย", "วรปรัชญ์", "วรรณวนัช","วรัชยา", "วัณณุวรรธน์", "วิชชาพร", "วิธวิทย์", "วิมพ์วิภา", "วิมลรัตน์", "วิวัฒน์ชัย", "วิวิธวินท์","วีรวัฒน์", "วีรินทร์", "ไววิทย์", "อชิรญา", "อดิกันต์", "อติกานต์", "อติวัณณ์", "อติวิชญ์", "อธิปัตย์","อธิพันธ์", "อธิวัฒน์", "อนงค์รตี", "อนพัช", "อนรรฆวี", "อนวัทย์", "อนันตพร", "อนันยช", "อนุตตรีย์","อนุพนธ์", "อนุวรรธน์", "อภินัทธ์", "อภิวรรธน์", "อมลณัฐ", "อมลรดา", "อรกานต์", "อรชพร", "อรนลิน","อรพิชญ์", "อรรจน์", "อรรถวิทย์", "อริย์ธัช", "อวัช", "กนต์ธร", "กมลนัทธ์", "กมลพรรณ", "กรชวัล","กฤตพจน์", "กัญจน์อมล", "ก่อฤกษ์", "ก้องภพ", "ขนบพร", "ขวัญหทัย", "คณพศ", "คมธรรม", "คมสันต์", "จรณ์","จรรยมณฑน์", "จรัสพงศ์", "จักรชัย", "ฉันทพัฒน์", "ฉันหทัย", "ชนสรณ์", "ชนัดพล", "ชนันธร", "ชยณัฐ","ชยันต์", "ชวกร", "ชวัลกร", "ชวัลลักษณ์", "ชัยยศ", "ฐปนนท์", "ฐปนัท", "ณฐพงศ์", "ณภัทร", "ณหทัย","ณัชพล", "ณัฏฐพรรณ", "ณัฐปพน", "ณัฐพงศ์", "ณัฐพัชร์", "ดนตร์", "ดลพร", "ตรัณ", "ถลัชนันท์", "ทรงอัปสร","ทักษพร", "ทักษ์ดนัย", "ทัตพงศ์", "ทัตสรวง", "ทัศน์พล", "ธนชัย", "ธมน", "ธยศ", "ธรณ์ธันย์", "ธรรมสรณ์","ธวัฒน์", "ธวัลกร", "ธวัลหทัย" };
		return list[new Random().nextInt(list.length)];

	}

	@SuppressWarnings("unused")
	private String simulateLastName() {
		String[] list = { "สุข", "สม", "ฝน", "ทรง", "พล", "นอบ", "น้อม", "อุ", "ทัย", "มา", "ยอด", "รัก", "วงษ์", "สุ","วรรณ", "ประ", "เสริฐ", "คิด", "วิ", "เกต", "กํา", "ธร", "สัง", "เวียน", "งาม", "โฉม", "น้อย", "ริ","วงศ์", "ชม" };
		return list[new Random().nextInt(list.length)] + "" + list[new Random().nextInt(list.length)] + ""+ list[new Random().nextInt(list.length)];

	}

	@SuppressWarnings({ "unused", "unchecked" })
	public String simulateAddress() {
		
		String result = "";
		
		try {
			String locale = "TH";
			Integer num_pv = this.randomRange(1, 77);

			Map<String, Object> req_pv = new HashMap<>(); req_pv.put("id", num_pv);
			
			Map<String, Object> data_pv = masterDataService.searchProvince(locale, req_pv);
			List<Map<String, Object>> list_pv = (List<Map<String, Object>>) data_pv.get("data");
			String str_pv = "";
			if (list_pv.size() > 0) {
				str_pv = (String) list_pv.get(0).get("name1");
			}

			Map<String, Object> data_ap = masterDataService.searchAmphure(locale, req_pv);
			List<Map<String, Object>> list_ap = (List<Map<String, Object>>) data_ap.get("data");
			Integer ran_ap = this.randomRange(1, list_ap.size());

			Integer num_ap = (Integer) list_ap.get(ran_ap - 1).get("code1");
			String str_ap = (String) list_ap.get(ran_ap - 1).get("name1");

			Map<String, Object> req_tb = new HashMap<>(); req_tb.put("id", num_ap);
			
			Map<String, Object> data_tb = masterDataService.searchTambon(locale, req_tb);
			List<Map<String, Object>> list_tb = (List<Map<String, Object>>) data_tb.get("data");
			Integer ran_tb = this.randomRange(1, list_tb.size());

			Integer num_tb = (Integer) list_tb.get(ran_tb - 1).get("code1");
			Integer zip = (Integer) list_tb.get(ran_tb - 1).get("code2");
			String str_tb = (String) list_tb.get(ran_tb - 1).get("name1");
			
			result = "" + num_tb + "|" + str_tb + "|" + num_ap + "|" + str_ap + "|" + num_pv + "|" + str_pv + "|" + zip;
			
			//result = ""+ num_ap + "|" + str_ap + "|"+num_pv+ "|"+ str_pv;
		}catch (Exception e) {
			log.error("{}",e.getMessage());
		}

		return result;

	}

	@SuppressWarnings("unused")
	private Integer randomRange(Integer min, Integer max) {
		Integer result = 0;

		while (true) {
			result = new Random().nextInt(max + 1);
			if (result >= min)
				break;
		}

		return result;

	}

}
