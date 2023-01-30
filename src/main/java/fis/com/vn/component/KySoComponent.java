package fis.com.vn.component;

import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import fis.com.vn.common.FileHandling;
import fis.com.vn.esigncloud.ESignCloudConstant;
import fis.com.vn.esigncloud.eSignCall;
import fis.com.vn.esigncloud.eSignCall2;
import fis.com.vn.esigncloud.datatypes.SignCloudMetaData;
import fis.com.vn.esigncloud.datatypes.SignCloudResp;

@Component
public class KySoComponent {
	@Value("${KY_SO_FOLDER}")
	String KY_SO_FOLDER;
	
	@Value("${MOI_TRUONG}")
	String MOI_TRUONG;
	
	
	public String signFile(String base64, String textKy, String uid, String passCode) throws Exception {
		
		byte[] pdfData = Base64.getDecoder().decode(base64);
		
		String ORGANIZATION_SIGNATURE_UUID = uid;
        String agreementUUid = ORGANIZATION_SIGNATURE_UUID;
        
        HashMap<String, String> singletonSigningForItem01 = new HashMap<>();
        singletonSigningForItem01.put("PAGENO", "2");
        singletonSigningForItem01.put("POSITIONIDENTIFIER", textKy);
        singletonSigningForItem01.put("RECTANGLEOFFSET", "-30,-70");
        singletonSigningForItem01.put("RECTANGLESIZE", "170,70");
        singletonSigningForItem01.put("VISIBLESIGNATURE", "True");
        singletonSigningForItem01.put("VISUALSTATUS", "False");
        singletonSigningForItem01.put("SHOWSIGNERINFO", "True");
        singletonSigningForItem01.put("SIGNERINFOPREFIX", "Ký bởi:");
        singletonSigningForItem01.put("SHOWDATETIME", "True");
        singletonSigningForItem01.put("DATETIMEPREFIX", "Ký ngày:");
        singletonSigningForItem01.put("SHADOWSIGNATUREPROPERTIES", "all");
        
        SignCloudMetaData signCloudMetaData = new SignCloudMetaData();
        
        signCloudMetaData.setSingletonSigning(singletonSigningForItem01);
        String jsonResponse = "";
        if(MOI_TRUONG.equals("dev")) {
        	eSignCall2 service = new eSignCall2();
			jsonResponse = service.prepareFileForSignCloudForOrganization(
					agreementUUid, 
					ESignCloudConstant.AUTHORISATION_METHOD_PASSCODE, 
					passCode,
					ESignCloudConstant.SYNCHRONOUS,
					null, 
					null, 
					pdfData, 
					"pdf_file_name.pdf", 
					ESignCloudConstant.MIMETYPE_PDF, 
					signCloudMetaData);
        } else {
			eSignCall service = new eSignCall();
			jsonResponse = service.prepareFileForSignCloudForOrganization(
					agreementUUid, 
					ESignCloudConstant.AUTHORISATION_METHOD_PASSCODE, 
					passCode,
					ESignCloudConstant.SYNCHRONOUS,
					null, 
					null, 
					pdfData, 
					"pdf_file_name.pdf", 
					ESignCloudConstant.MIMETYPE_PDF, 
					signCloudMetaData);
        }
		
		ObjectMapper objectMapper = new ObjectMapper();
		SignCloudResp signCloudResp = objectMapper.readValue(jsonResponse, SignCloudResp.class);
		if (signCloudResp.getResponseCode() == 0
				|| signCloudResp.getResponseCode() == 1018) {
            if (signCloudResp.getSignedFileData() != null) {
            	FileHandling fileHandling = new FileHandling();
            	String imgFolderLog = fileHandling.getFolder(KY_SO_FOLDER+"/") ;
                String file = imgFolderLog + UUID.randomUUID().toString() +".pdf";
                FileOutputStream fos = new FileOutputStream(file);
                IOUtils.write(signCloudResp.getSignedFileData(), fos);
                fos.close();
                return file;
            } else {
            	throw new Exception("Error signing: SignedData is NULL");
            }
        } else {
        	throw new Exception("Error signing: "+signCloudResp.getResponseCode());
        }
		
	}
}
