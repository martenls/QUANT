package app.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.internal.Path;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import app.config.MongoDBManager;
import app.dao.CookieDAO;
import app.dao.DocumentDAO;
import app.dao.UserDAO;
import app.dao.UserDatasetCorrectionDAO;
import app.model.DatasetModel;
import app.model.DocumentList;
import app.model.User;
import app.model.UserDatasetCorrection;
import app.sparql.SparqlService;

@Controller
public class DashboardController {
	//@Autowired
	  
	  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Cookie[] cks = request.getCookies();
		CookieDAO cookieDao = new CookieDAO();
		ModelAndView mav = new ModelAndView("dashboard");
		//mav.addObject("isValidate", cookieDao.isValidate(cks));
		//Get name of the user
		UserDAO userDao = new UserDAO();
		User user = userDao.getUserByUsername(cookieDao.getAuth(cks));
		//String Name = user.getName();
		//mav.addObject("cokie", cookieDao.getAuth(cks));
		mav.addObject("name", user.getName());
		mav.addObject("role", user.getRole());//define role
		if (cookieDao.isValidate(cks)) {
			DocumentDAO documentDao = new DocumentDAO();
			int qald1 = documentDao.countQaldDataset("QALD1_Test_dbpedia") + documentDao.countQaldDataset("QALD1_Train_dbpedia");
			int qald2 = documentDao.countQaldDataset("QALD2_Test_dbpedia") + documentDao.countQaldDataset("QALD2_Train_dbpedia");
			int qald3 = documentDao.countQaldDataset("QALD3_Test_dbpedia") + documentDao.countQaldDataset("QALD3_Train_dbpedia");
			int qald4 = documentDao.countQaldDataset("QALD4_Test_Multilingual") + documentDao.countQaldDataset("QALD4_Train_Multilingual");
			int qald5 = documentDao.countQaldDataset("QALD5_Test_Multilingual") + documentDao.countQaldDataset("QALD5_Train_Multilingual");
			int qald6 = documentDao.countQaldDataset("QALD6_Test_Multilingual") + documentDao.countQaldDataset("QALD6_Train_Multilingual");
			int qald7 = documentDao.countQaldDataset("QALD7_Test_Multilingual") + documentDao.countQaldDataset("QALD7_Train_Multilingual");
			int qald8 = documentDao.countQaldDataset("QALD8_Test_Multilingual") + documentDao.countQaldDataset("QALD8_Train_Multilingual");
			mav.addObject("qald1", qald1);
			mav.addObject("qald2", qald2);
			mav.addObject("qald3", qald3);
			mav.addObject("qald4", qald4);
			mav.addObject("qald5", qald5);
			mav.addObject("qald6", qald6);
			mav.addObject("qald7", qald7);
			mav.addObject("qald8", qald8);
			
			UserDatasetCorrectionDAO udcDao = new UserDatasetCorrectionDAO();
			int qald1Correction = udcDao.countQaldDataset(user.getId(), "QALD1_Test_dbpedia") +  udcDao.countQaldDataset(user.getId(), "QALD1_Train_dbpedia");
			int qald2Correction = udcDao.countQaldDataset(user.getId(), "QALD2_Test_dbpedia") +  udcDao.countQaldDataset(user.getId(), "QALD2_Train_dbpedia");
			int qald3Correction = udcDao.countQaldDataset(user.getId(), "QALD3_Test_dbpedia") +  udcDao.countQaldDataset(user.getId(), "QALD3_Train_dbpedia");
			int qald4Correction = udcDao.countQaldDataset(user.getId(), "QALD4_Test_Multilingual") +  udcDao.countQaldDataset(user.getId(), "QALD4_Train_Multilingual");
			int qald5Correction = udcDao.countQaldDataset(user.getId(), "QALD5_Test_Multilingual") +  udcDao.countQaldDataset(user.getId(), "QALD5_Train_Multilingual");
			int qald6Correction = udcDao.countQaldDataset(user.getId(), "QALD6_Test_Multilingual") +  udcDao.countQaldDataset(user.getId(), "QALD6_Train_Multilingual");
			int qald7Correction = udcDao.countQaldDataset(user.getId(), "QALD7_Test_Multilingual") +  udcDao.countQaldDataset(user.getId(), "QALD7_Train_Multilingual");
			int qald8Correction = udcDao.countQaldDataset(user.getId(), "QALD8_Test_Multilingual") +  udcDao.countQaldDataset(user.getId(), "QALD8_Train_Multilingual");
			
			mav.addObject("qald1Correction", qald1Correction);
			mav.addObject("qald2Correction", qald2Correction);
			mav.addObject("qald3Correction", qald3Correction);
			mav.addObject("qald4Correction", qald4Correction);
			mav.addObject("qald5Correction", qald5Correction);
			mav.addObject("qald6Correction", qald6Correction);
			mav.addObject("qald7Correction", qald7Correction);
			mav.addObject("qald8Correction", qald8Correction);
			
			mav.addObject("csList", udcDao.getCorrectionSummary());
			
		}else {
			redirectAttributes.addFlashAttribute("message","Session Expired.");
			mav = new ModelAndView("redirect:/login");
		}
	    return mav;
	  }
	  
	  //function to export master dataset
	  @RequestMapping(value = "/download-master-dataset/{qald-test}/{qald-train}", method = RequestMethod.GET)
		public ModelAndView downloadMasterDataset(@PathVariable("qald-test") String qaldTest,@PathVariable("qald-train") String qaldTrain, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws FileNotFoundException, IOException {
			Cookie[] cks = request.getCookies();
			CookieDAO cookieDao = new CookieDAO();
			if (!cookieDao.isValidate(cks)) {
				redirectAttributes.addFlashAttribute("message","Session Expired.");
				ModelAndView mav = new ModelAndView("redirect:/login");
				return mav;
			}
			JSONArray list = new JSONArray();			
	        BasicDBObject searchObj = new BasicDBObject();
			searchObj.put("id",1);	
			String[] qaldName = qaldTrain.split("_");
			SparqlService ss = new SparqlService();
			
				try {
					//call mongoDb
					DB db = MongoDBManager.getDB("QaldCuratorFiltered"); //Database Name
					DBCollection coll = db.getCollection(qaldTrain); //Collection
					DBCursor cursor = coll.find().sort(searchObj); //Find All
					while (cursor.hasNext()) {
						DBObject dbobj = cursor.next();
						Gson gson = new GsonBuilder().create();
						DatasetModel q = gson.fromJson(dbobj.toString(), DatasetModel.class);	
						String formatedSparqlQuery = q.getSparqlQuery().replaceAll("\r\n|\r|\n|\t", " ");
						q.setDatasetVersion(qaldTrain);
						q.setSparqlQuery(formatedSparqlQuery);
						list.add(q);
					}
					//get QALD Test dataset
					DBCollection coll1 = db.getCollection(qaldTest); //Collection
					DBCursor cursor1 = coll1.find().sort(searchObj); //Find All
					while (cursor1.hasNext()) {
						DBObject dbobj1 = cursor1.next();
						Gson gson1 = new GsonBuilder().create();
						DatasetModel q1 = gson1.fromJson(dbobj1.toString(), DatasetModel.class);	
						String formatedSparqlQuery = q1.getSparqlQuery().replaceAll("\r\n|\r|\n|\t", " ");
						q1.setDatasetVersion(qaldTest);
						q1.setSparqlQuery(formatedSparqlQuery);
						list.add(q1);
					}
								
				} catch (Exception e) {}
				
				//contruct dataset information
				JSONObject newdbobj = new JSONObject();
				newdbobj.put("id", qaldName[0]+" dataset");
				
				JSONObject obj = new JSONObject();
				obj.put("dataset", newdbobj);
		        obj.put("questions", list);
		        JSONArray objFinal = new JSONArray();
		        objFinal.add(obj);
		    
		        //write dataset into a file in json format
		        ObjectMapper mapper = new ObjectMapper();
				ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
				writer.writeValue(new File("C:\\exportMasterDataset\\"+qaldName[0]+".json"), objFinal);		        
	        
			ModelAndView mav = new ModelAndView("redirect:/dashboard");
			return mav;
		}
	  //function to export curated dataset
	  @RequestMapping(value = "/download-curated-dataset/{qald-test}/{qald-train}", method = RequestMethod.GET)
		public ModelAndView downloadCuratedDataset(@PathVariable("qald-test") String qaldTest,@PathVariable("qald-train") String qaldTrain, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws FileNotFoundException, IOException {
			Cookie[] cks = request.getCookies();
			CookieDAO cookieDao = new CookieDAO();
			if (!cookieDao.isValidate(cks)) {
				redirectAttributes.addFlashAttribute("message","Session Expired.");
				ModelAndView mav = new ModelAndView("redirect:/login");
				return mav;
			}
			//get the user
			UserDAO userDao = new UserDAO();
			User user = userDao.getUserByUsername(cookieDao.getAuth(cks));
			int userId = user.getId();
			
			//get curated questions
			List<DatasetModel> listCuratedQuestions = new ArrayList<DatasetModel>();
			BasicDBObject searchObj = new BasicDBObject();
			searchObj.put("userId", userId);
			searchObj.put("datasetVersion", qaldTrain);
			BasicDBObject sortObj = new BasicDBObject();
			sortObj.put("id",1);
				try {
					//call mongoDb
					DB db = MongoDBManager.getDB("QaldCuratorFiltered"); //Database Name
					DBCollection coll = db.getCollection("UserDatasetCorrection"); //Collection
					DBCursor cursor = coll.find(searchObj).sort(sortObj); //Find All
					UserDatasetCorrectionDAO udcDao = new UserDatasetCorrectionDAO();
					while (cursor.hasNext()) {
						DBObject dbobj = cursor.next();
						Gson gson = new GsonBuilder().create();				
						DatasetModel q = gson.fromJson(dbobj.toString(), DatasetModel.class);						
						listCuratedQuestions.add(q);
					}
					searchObj.put("userId", userId);
					searchObj.put("datasetVersion", qaldTest);
					DBCursor cursor1 = coll.find(searchObj).sort(sortObj); //Find All
					while (cursor1.hasNext()) {
						DBObject dbobj = cursor1.next();
						Gson gson = new GsonBuilder().create();
						DatasetModel q = gson.fromJson(dbobj.toString(), DatasetModel.class);						
						listCuratedQuestions.add(q);
					}								
				} catch (Exception e) {}
			
			//write the curated questions into a json format file
			//contruct dataset information
			String[] qaldName = qaldTrain.split("_");
			JSONObject newdbobj = new JSONObject();
			newdbobj.put("id", qaldName[0]+" dataset");
			
			JSONObject obj = new JSONObject();
			obj.put("dataset", newdbobj);
	        obj.put("questions", listCuratedQuestions);
	        JSONArray objFinal = new JSONArray();
	        objFinal.add(obj);
	    
	        //write the curated dataset into a file in json format
	        ObjectMapper mapper = new ObjectMapper();
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			writer.writeValue(new File("C:\\exportCuratedDataset\\"+qaldName[0]+".json"), objFinal);		        
        
		ModelAndView mav = new ModelAndView("redirect:/dashboard");
		return mav;	
	  }			
}
