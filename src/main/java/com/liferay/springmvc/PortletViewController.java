package com.liferay.springmvc;




import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import java.io.File;
import org.apache.axis.encoding.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

// Document and Media*
import java.io.IOException;
import com.itextpdf.text.Paragraph;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLSyncConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
@Controller
@RequestMapping("VIEW")
public class PortletViewController  {
	//DBUtil db = new DBUtil();
	@RenderMapping
	public String viewHomePage(RenderRequest request, RenderResponse response) {
		// get user information
		PortletSession session=request.getPortletSession();
		try {User user;
			user = UserServiceUtil.getUserById(Long.parseLong(request.getRemoteUser()));
			session.setAttribute("fullname", user.getFullName());
			session.setAttribute("firstname", user.getFirstName());
			session.setAttribute("screenname", user.getScreenName());
			session.setAttribute("lastname", user.getLastName());
			session.setAttribute("emailadress", user.getEmailAddress());
			session.setAttribute("jobtitle", user.getJobTitle());
			session.setAttribute("female", user.getFemale());
			JSONObject firstObject = new JSONObject(user.getPhones().toString().replace("[", "").replace("]", "").replace("extension=","extension=null").replaceAll(":","-" ).replaceAll("=",":" ).replaceAll(" ", ""));
			session.setAttribute("number", firstObject.getString("number"));
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//{mvccVersion=0, uuid=a488fb2d-493c-9c64-862f-a3511ee7b835, phoneId=48403, companyId=20116, userId=20156, userName=Oumaima Nouar, createDate=2017-12-14 08:57:12.257, modifiedDate=2017-12-14 08:57:12.257, classNameId=20042, classPK=20157, number=+33605780750, extension=, typeId=11008, primary=true}
		return "form";
	}
	@RenderMapping(params = "action=showForm")
	public String viewByParameter(Map<String, Object> map) {
		Customer customer = new Customer();
		map.put("customer", customer);
		
		return "form";
	}
	@RenderMapping(params = "act=showForm2")
	public String viewByParameter2(RenderRequest request, RenderResponse response) {
		PortletSession session=request.getPortletSession();
		String iddemand=(String) session.getAttribute("iddemand");
		String file=(String) session.getAttribute("file");
		String fileName=(String) session.getAttribute("fileName");
		try {
			if(iddemand!=null && file!=null ) {
			Customer c=new Customer();
			if(!c.getFile_yousign(iddemand,file).equals(null))
			{ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			code_pdf(themeDisplay,c.getFile_yousign(iddemand,file),request,fileName);
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file1=new File(fileName+".pdf");
		File file2=new File(fileName+"signe.pdf");
		try{
			file1.delete();
			file2.delete();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		request.getPortletSession().removeAttribute("iddemand");
		request.getPortletSession().removeAttribute("file");
		request.getPortletSession().removeAttribute("fileName");

		return "form";
	}
	@RenderMapping(params = "action=success")
	public String viewSuccess() {
		return "success";
	}
	@ActionMapping(value = "handleCustomer")
	public void getCustomerData(
			@ModelAttribute("customer") Customer customer,
			ActionRequest actionRequest, ActionResponse actionResponse,
			Model model) {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			String sql = "INSERT INTO \"user\" (firstname, lastname, email, id_demande, tel)";
			//Insert into database
			 sql += " VALUES('"+customer.getFirstName()+"','"+customer.getLastName()+"','"+customer.getEmail()+"','"+customer.getIdDemand()+"','"+customer.getTel()+"')";
			System.out.println(sql);
			// db.Execute(sql); 
			generate_pdf(themeDisplay, actionRequest,customer.getFirstName()+" "+customer.getLastName().toUpperCase());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//customer.setPath(customer.getFileUpload().getStorageDescription().substring(4, customer.getFileUpload().getStorageDescription().length()-1));
		PortletSession session=actionRequest.getPortletSession();
		String fileName=(String) session.getAttribute("fileName");
		try{			customer.sendFile_yousign(customer.code_base64(fileName+".pdf"),fileName);
		}catch( Exception e) {e.printStackTrace();}
		actionResponse.setRenderParameter("action", "success");
		model.addAttribute("successModel", customer);
		session.setAttribute("iddemand",customer.getIdDemand());
		session.setAttribute("file",customer.getFilebase64());
	}

@ModelAttribute("customer")
	public Customer getCommandObject() {
return new Customer();
}
	//generate pdf
	public void generate_pdf(ThemeDisplay themeDisplay,ActionRequest actionRequest,String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{	
	//Generate pdf file
	com.itextpdf.text.Document document = new com.itextpdf.text.Document();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
	LocalDateTime now = LocalDateTime.now();
	String fileName=name.replace(" ", "")+dtf.format(now);	
	File file = new File(fileName+".pdf");
	//String emplacement=file.getAbsolutePath();
	try
	{	//OutputStream  outputStream=new FileOutputStream(file);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
		document.open();
		User user;
		try {
			user = UserServiceUtil.getUserById(Long.parseLong(actionRequest.getRemoteUser()));
			String chaine = user.getFirstName();
			char[] char_table = chaine.toCharArray();
			char_table[0]=Character.toUpperCase(char_table[0]);
			Paragraph entete0 = new Paragraph(new String(char_table)+" "+user.getLastName().toUpperCase());
			Paragraph entete1 = new Paragraph(user.getEmailAddress());
			JSONObject firstObject = new JSONObject(user.getPhones().toString().replace("[", "").replace("]", "").replace("extension=","extension=null").replaceAll(":","-" ).replaceAll("=",":" ).replaceAll(" ", ""));
			Paragraph entete2 = new Paragraph(firstObject.getString("number"));
			//[{mvccVersion=0, uuid=23ee7449-9681-69a7-2c6d-a55c88e2eeca, addressId=48401, companyId=20116, userId=20156, userName=Oumaima Nouar, createDate=Thu Dec 14 08:57:12 GMT 2017, modifiedDate=Thu Dec 14 08:57:12 GMT 2017, classNameId=20042, classPK=20157, street1=1 Avenue Leon Blum, street2=, street3=, city=Maisons Alfort, zip=94700, regionId=3014, countryId=3, typeId=11000, mailing=false, primary=true}]
			JSONObject Adresse = new JSONObject(user.getAddresses().toString().replace("[", "").replace("]", "").replace("=,","=null,").replaceAll(":","-" ).replaceAll("=",":" ).replace(":", ":\"").replace(",", "\",").replace("}", "\"}"));
			Paragraph entete3 = new Paragraph(Adresse.getString("street1"));
			Paragraph entete4 = new Paragraph(Adresse.getInt("zip")+" "+Adresse.getString("city").toUpperCase());
			Paragraph entete5 = new Paragraph("France");
			entete0.setAlignment(Element.ALIGN_LEFT);
			entete1.setAlignment(Element.ALIGN_LEFT);
			entete2.setAlignment(Element.ALIGN_LEFT);
			entete3.setAlignment(Element.ALIGN_LEFT);
			entete4.setAlignment(Element.ALIGN_LEFT);
			entete5.setAlignment(Element.ALIGN_LEFT);
			document.add(entete0);
			document.add(entete1);
			document.add(entete2);
			document.add(entete3);
			document.add(entete4);
			document.add(entete5);
		Paragraph paragraph = new Paragraph("Attestation");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		document.addAuthor("Auteuuuur");
		document.addCreationDate();
		document.addCreator("Createuuuuurr");
		document.addTitle("Titleeeeeeee");
		document.addSubject("Suuuuuubject");
		Paragraph paragraph0 = new Paragraph("Objet : Attestation sur l'honneur   ");
		paragraph0.setAlignment(Element.ALIGN_LEFT);
		paragraph0.setSpacingBefore(100f);
		document.add(paragraph0);
		Paragraph paragraph1;
		if(user.getFemale()) {
			paragraph1 = new Paragraph("	Je soussignée Madame "+name+", "
				+ "atteste sur l'honneur (exposer avec exactitude les faits faisant l'objet de la déclaration).  "
				+ "J'ai connaissance des sanctions pénales encourues par l'auteur d'une fausse attestation.   "
				);}
		else {
			paragraph1 = new Paragraph("	Je soussigné Monsieur "+name+", "
					+ "atteste sur l'honneur (exposer avec exactitude les faits faisant l'objet de la déclaration).  "
					+ "J'ai connaissance des sanctions pénales encourues par l'auteur d'une fausse attestation.   "
					);
		}
		Paragraph paragraph12 = new Paragraph("Fait pour servir et valoir ce que de droit. ");
		paragraph1.setAlignment(Element.ALIGN_LEFT);
		paragraph1.setSpacingBefore(50f);
		document.add(paragraph1);
		document.add(paragraph12);
		document.close();
		writer.setCompressionLevel(0);
		writer.setCompressionLevel(9);
		writer.setFullCompression();
		writer.close();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	} catch (com.itextpdf.text.DocumentException e){
		e.printStackTrace();}
	catch (FileNotFoundException e){
		e.printStackTrace();}
	// Save file
	String mimeType =  MimeTypesUtil.getContentType(file);
    String title = fileName;
	String description = "This file is added via programatically";
	long repositoryId = themeDisplay.getScopeGroupId();
    try
    {  	Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
    	Long groupId = themeDisplay.getLayout().getGroupId();
    	
    	// Stocker dans les docs privés 
    	
    	User user;
		user = UserServiceUtil.getUserById(Long.parseLong(actionRequest.getRemoteUser()));
		ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),actionRequest);
		DLFolder dirtest;

		 try {
    		 //folder public
    		 //dir = DLFolderLocalServiceUtil.getFolder(groupId, parentFolderId, "Documents");
    		 //folder privé
			 dirtest = DLFolderLocalServiceUtil.getFolder(user.getGroupId(), parentFolderId, "Documents");
    	 } catch(Exception e) {
    		//folder public
    	 	//dir = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId,false,parentFolderId, "Documents",  "This folder is added via programatically",false, serviceContext);

    		 //folder privé$
    		 dirtest = DLFolderLocalServiceUtil.addFolder(user.getUserId(), user.getGroupId(), user.getGroupId(),false,parentFolderId, "Documents",  "This folder is added via programatically",false, serviceContext);

    		 
    	 }
    	InputStream is = new FileInputStream( file );
    	 FileEntry dlFileEntry =DLAppServiceUtil.addFileEntry(user.getGroupId(), dirtest.getFolderId(), fileName+".pdf", mimeType, 
    			title, description, "", is, file.length(), serviceContext);

    	 // 	Stocker dans les docs publics 
    	 
    	 /*DLFolder dir = DLFolderLocalServiceUtil.getFolder(groupId, parentFolderId, "Documents");
    	FileEntry	 dlFileEntry =DLAppServiceUtil.addFileEntry(repositoryId, dir.getFolderId(), fileName+".pdf", mimeType, 
 			title, description, "", is, file.length(), serviceContext);*/
    	 Map<String, Serializable> workflowContext = new HashMap<String, Serializable>();
    	 long userId=themeDisplay.getUserId();
		    DLFileEntryLocalServiceUtil.updateStatus(userId, dlFileEntry.getFileVersion().getFileVersionId() ,WorkflowConstants.STATUS_INCOMPLETE,serviceContext , workflowContext);
		    
    
     } catch (Exception e)
    {
    	e.printStackTrace();
    }
    PortletSession session=actionRequest.getPortletSession();
	session.setAttribute("fileName",fileName);
	
}
	
	// base64 to pdf
	public void code_pdf(ThemeDisplay themeDisplay,String filebase64,RenderRequest actionRequest,String fileName) throws IOException, PortalException {
	

	try {	
		Base64 b = new Base64();
		byte[] imageBytes = b.decode(filebase64);
		FileOutputStream fileout;
		fileout = new FileOutputStream(fileName+".pdf");
		fileout.write(imageBytes);
		fileout.close();
		File file = new File(fileName+".pdf");
		String mimeType =  MimeTypesUtil.getContentType(file);
	    String title = fileName+" signed";
		String description = "This file is added via programatically (Signed by yousign)";
		long repositoryId = themeDisplay.getScopeGroupId();
		long userId=themeDisplay.getUserId();
		ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),actionRequest);
	    try
	    {  	Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	    	Long groupId = themeDisplay.getLayout().getGroupId();
	    	User user;
			user = UserServiceUtil.getUserById(Long.parseLong(actionRequest.getRemoteUser()));
			

	    	DLFolder dir;
	    	dir = DLFolderLocalServiceUtil.getFolder(user.getGroupId(), parentFolderId, "Documents");
	    	InputStream is = new FileInputStream( file );
	    	Map<String, Serializable> workflowContext = new HashMap<String, Serializable>();
	    	//fileEntry public 
	    	//FileEntry dlFileEntry =DLAppServiceUtil.getFileEntry(groupId, dir.getFolderId(), fileName);
	    	//fileEntry privé 
	    	FileEntry dlFileEntry =DLAppServiceUtil.getFileEntry(user.getGroupId(), dir.getFolderId(), fileName);
	    	 DLFileEntryLocalServiceUtil.updateStatus(user.getUserId(), dlFileEntry.getFileVersion().getFileVersionId(), WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);		
	    	 DLFileEntryLocalServiceUtil.updateFileEntry(user.getUserId(), dlFileEntry.getFileEntryId(), fileName+".pdf", mimeType, fileName, description, "Fichier signé", true, 0, null, file, is, file.length(), serviceContext);
	    	 DLFileEntryLocalServiceUtil.updateStatus(user.getUserId(), dlFileEntry.getLatestFileVersion().getFileVersionId(), WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);
	    	 
	    } catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}// Or PDF file

}




}