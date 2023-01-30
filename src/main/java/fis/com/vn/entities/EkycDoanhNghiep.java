package fis.com.vn.entities;

import java.util.ArrayList;

import lombok.Data;

@Data
public class EkycDoanhNghiep {
	// step 1
	String fileBusinessRegistration;
	String fileAppointmentOfChiefAccountant;
	String fileBusinessRegistrationCertificate;
	String fileDecisionToAppointChiefAccountant;
	String fileInvestmentCertificate;
	String fileCompanyCharter;
	String fileSealSpecimen;
	String fileFatcaForms;
	String fileOthers;
	ArrayList<String> files;

	// step2
	String nameOfTheAccountHolder;
	String number;
	String dateAccountOpening;
	String nameCompany;
	String registeredAddress;
	String operatingAddress;
	String countryOfIncorporation;
	String registrationNumber;
	String straight2BankGroupID;
	String mailingAddress;
	String swiftBankIDCode;
	String mobileOfficeTelephone;
	String contactPerson;
	String emailAddress;

	ArrayList<Account> listAccount;

	String confirmation;
	String registeringEmailAddress;
	String chequebookRequired;
	//
	String shortName;
	String nameInEnglish;
	String faxNumber;
	String taxCode;
	String applicableAccountingSystems;
	String taxMode;
	String residentStatus;
	String businessActivities;
	String yearlyAveragenumber;
	String totalSalesTurnover;
	String totalCapital;
	String companysSealRegistration;
	String sampleOfTheSeal;
	String agreeToReceive;
	String applicantsRepresentative;
	String namePlus1;
	String datePlus1;
	String namePlus2;
	String datePlus2;

	// step4
	ArrayList<InfoPerson> legalRepresentator;
	ArrayList<InfoPerson> chiefAccountant;
	String haveAChiefAccountant;
	String haveAcccountHolder ;
	ArrayList<InfoPerson> listOfLeaders;
	ArrayList<InfoPerson> personAuthorizedAccountHolder;
	ArrayList<InfoPerson> personAuthorizedChiefAccountant;
	String specialInstructions;

	//
	String token;
	String tokenCheck;
	
	String allInOne;

	//
	String fileKy;
	String fileDangKy;

	String status;
	String relationshipManagerName;

	ArrayList<InfoPerson> userDesignation;

	String registerUser ;
	String specialInstructionsUser;
}
