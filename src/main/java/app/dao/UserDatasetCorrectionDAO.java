package app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import app.config.MongoDBManager;
import app.model.CorrectionSummary;
import app.model.DatasetModel;
import app.model.DatasetSuggestionModel;
import app.model.User;
import app.model.UserDatasetCorrection;
import app.sparql.SparqlService;

public class UserDatasetCorrectionDAO {
	public UserDatasetCorrection getDocument(int userId, String id, String datasetVersion) {
		 BasicDBObject searchObj = new BasicDBObject();
		 UserDatasetCorrection item = new UserDatasetCorrection();
		 searchObj.put("id", id);
		 searchObj.put("datasetVersion", datasetVersion);
		 searchObj.put("userId", userId);
		 try {
				DB db = MongoDBManager.getDB("QaldCuratorFiltered");
				DBCollection coll = db.getCollection("UserDatasetCorrection");
				DBCursor cursor = coll.find(searchObj);
				while (cursor.hasNext()) {
					DBObject dbobj = cursor.next();
					Gson gson = new GsonBuilder().create();
					UserDatasetCorrection q = gson.fromJson(dbobj.toString(), UserDatasetCorrection.class);					
					item.setId(q.getId());
					item.setDatasetVersion(q.getDatasetVersion());
					item.setAnswerType(q.getAnswerType());
					item.setAggregation(q.getAggregation());
					item.setOnlydbo(q.getOnlydbo());
					item.setHybrid(q.getHybrid());
					item.setLanguageToQuestion(q.getLanguageToQuestion());
					item.setLanguageToKeyword(q.getLanguageToKeyword());
					item.setSparqlQuery(q.getSparqlQuery());
					item.setPseudoSparqlQuery(q.getPseudoSparqlQuery());
					item.setGoldenAnswer(q.getGoldenAnswer());
					item.setOutOfScope(q.getOutOfScope());
					item.setUserId(q.getUserId());
					item.setRevision(q.getRevision());
					item.setLastRevision(q.getLastRevision());
					item.setTransId(q.getTransId());
					item.setStatus(q.getStatus());
				}
				return item;
		 } catch (Exception e) {}
		 return item;
	 }
	public List<UserDatasetCorrection> getAllDatasets(int userId) {
		List<UserDatasetCorrection> tasks = new ArrayList<UserDatasetCorrection>();
		BasicDBObject searchObj = new BasicDBObject();
		searchObj.put("userId", userId);
		BasicDBObject sortObj = new BasicDBObject();
		sortObj.put("id",1);
			try {
				//call mongoDb
				DB db = MongoDBManager.getDB("QaldCuratorFiltered"); //Database Name
				DBCollection coll = db.getCollection("UserDatasetCorrection"); //Collection
				DBCursor cursor = coll.find(searchObj).sort(sortObj); //Find All
				while (cursor.hasNext()) {
					DBObject dbobj = cursor.next();
					Gson gson = new GsonBuilder().create();
					UserDatasetCorrection q = gson.fromJson(dbobj.toString(), UserDatasetCorrection.class);
					UserDatasetCorrection item = new UserDatasetCorrection();
					item.setDatasetVersion(q.getDatasetVersion());;
					item.setId(q.getId());
					item.setAnswerType(q.getAnswerType());
					item.setAggregation(q.getAggregation());
					item.setOnlydbo(q.getOnlydbo());
					item.setHybrid(q.getHybrid());
					item.setLanguageToQuestion(q.getLanguageToQuestion());
					item.setLanguageToKeyword(q.getLanguageToKeyword());
					item.setSparqlQuery(q.getSparqlQuery());
					item.setPseudoSparqlQuery(q.getPseudoSparqlQuery());
					item.setGoldenAnswer(q.getGoldenAnswer());
					tasks.add(item);
				}
							
			} catch (Exception e) {}
			return tasks;
	}
	/*
	  * This method is used to update document in MongoDB
	  */
	 public void addDocument(UserDatasetCorrection document) {
		 try {
			
			BasicDBObject newDbObj = toBasicDBObject(document);
			
			DB db = MongoDBManager.getDB("QaldCuratorFiltered");
			DBCollection coll = db.getCollection("UserDatasetCorrection");
			
			coll.save(newDbObj);
		 } catch (Exception e) {}
	 }
	 public void updateDocument(UserDatasetCorrection document) {
		 BasicDBObject searchObj = new BasicDBObject();
		 searchObj.put("id", document.getId());
		 searchObj.put("datasetVersion", document.getDatasetVersion());
		 searchObj.put("userId", document.getUserId());
		 try {
			
			BasicDBObject newDbObj = toBasicDBObject(document);
			
			DB db = MongoDBManager.getDB("QaldCuratorFiltered");
			DBCollection coll = db.getCollection("UserDatasetCorrection");
			
			coll.update(searchObj, newDbObj);
		 } catch (Exception e) {}
	 }
	 /*
	  * This method is used to create an object for update or save purpose in MongoDB
	  */
	private BasicDBObject toBasicDBObject(UserDatasetCorrection document) {
		BasicDBObject newdbobj = new BasicDBObject();
		newdbobj.put("transId", document.getTransId());
		newdbobj.put("userId", document.getUserId());
		newdbobj.put("revision", document.getRevision());
		newdbobj.put("lastRevision", document.getLastRevision());
		newdbobj.put("id", document.getId());
		newdbobj.put("datasetVersion", document.getDatasetVersion());
		newdbobj.put("answerType", document.getAnswerType());
		newdbobj.put("aggregation", document.getAggregation());
		newdbobj.put("onlydbo", document.getOnlydbo());
		newdbobj.put("hybrid", document.getHybrid());
		newdbobj.put("sparqlQuery", document.getSparqlQuery());
		newdbobj.put("pseudoSparqlQuery", document.getPseudoSparqlQuery());
		newdbobj.put("goldenAnswer", document.getGoldenAnswer());
		newdbobj.put("languageToQuestion", document.getLanguageToQuestion());
		newdbobj.put("languageToKeyword", document.getLanguageToKeyword());
		newdbobj.put("outOfScope", document.getOutOfScope());
		newdbobj.put("status", document.getStatus());
		
		return newdbobj;
	}
	public Boolean isDocumentExist(int userId, String id, String datasetVersion) {
		BasicDBObject searchObj = new BasicDBObject();
		 searchObj.put("id", id);
		 searchObj.put("datasetVersion", datasetVersion);
		 searchObj.put("userId", userId);
		 try {
				DB db = MongoDBManager.getDB("QaldCuratorFiltered");
				DBCollection coll = db.getCollection("UserDatasetCorrection");
				DBCursor cursor = coll.find(searchObj);
				while (cursor.hasNext()) {
					return true;
				}
				
		 } catch (Exception e) {
			 return false;
		 }
		 return false;
		
	}
	//Correction Parts
		public DatasetSuggestionModel implementCorrection(int userId, String id, String datasetVersion) {
			 BasicDBObject searchObj = new BasicDBObject();
			 DatasetSuggestionModel item = new DatasetSuggestionModel();
			 searchObj.put("id", id);
			 searchObj.put("datasetVersion", datasetVersion);
			 searchObj.put("userId", userId);
			 try {
					DB db = MongoDBManager.getDB("QaldCuratorFiltered");
					DBCollection coll = db.getCollection("UserDatasetCorrection");
					DBCursor cursor = coll.find(searchObj);
					while (cursor.hasNext()) {
						DBObject dbobj = cursor.next();
						Gson gson = new GsonBuilder().create();
						UserDatasetCorrection q = gson.fromJson(dbobj.toString(), UserDatasetCorrection.class);
						String answerType = q.getAnswerType();	
						String languageToQuestionEn = q.getLanguageToQuestion().get("en").toString();
						String[] strArray = (String[]) q.getGoldenAnswer().toArray(new String[ q.getGoldenAnswer().size()]);
						String answer=strArray[0];
						
						if (!answerType.equals(AnswerTypeChecking(answerType,answer))) {						
							item.setAnswerTypeSugg(AnswerTypeChecking(answerType, answer));						
						}else {
							item.setAnswerTypeSugg("");
						}
						String query = q.getSparqlQuery();
						if (AggregationChecking(query).equals(q.getAggregation())) {
							item.setAggregationSugg("");
						}else {
							item.setAggregationSugg(AggregationChecking(query));
						}
						
						String onlyDboValue = q.getOnlydbo().toString();
						if (!onlyDboChecking(query).equals(onlyDboValue)) {
							item.setOnlyDboSugg(onlyDboChecking(query));
						}else {
							item.setOnlyDboSugg("");
						}
						
						String hybridValue = q.getHybrid().toString();
						if (!HybridChecking(query).equals(hybridValue)) {
							item.setHybridSugg(HybridChecking(query));
						}else {
							item.setHybridSugg("");
						}
								
						String outOfScope = String.valueOf(q.getOutOfScope());					
						String oos = outOfScope;	
						if (outOfScopeChecking(query, languageToQuestionEn).equals("false") && outOfScope.equals("false")) {
							oos ="true";
						}else if (outOfScopeChecking(query, languageToQuestionEn).equals("false") && outOfScope.equals("true")) {
							oos="";
						}else if (outOfScopeChecking(query, languageToQuestionEn).equals("true") && outOfScope.equals("null") || outOfScope.isEmpty()) {
							oos="false";
						}else if (outOfScopeChecking(query, languageToQuestionEn).equals("false") && outOfScope.equals("null") || outOfScope.isEmpty()) {
							oos="true";
						}
						//System.out.println("This is the checking result "+outOfScopeChecking(query, languageToQuestionEn));
						item.setOutOfScopeSugg(oos);
					}
					
			 } catch (Exception e) {}
			 return item;
		 }
		//Check Answer Type
		public String AnswerTypeChecking (String answerType, String answerValue) {
			String finalAnswerType = "";		
			if (answerValue.toLowerCase().startsWith("http://")) {
				//System.out.println("This is http");
				if (answerType.toLowerCase().equals("resource")) {
					finalAnswerType = answerType;
				}else
				{				
					finalAnswerType = "resource";
				}	
			}
			else if (validateDateFormat(answerValue))
			{
				//System.out.println("this is date");
				if (answerType.toLowerCase().equals("date")) {
					finalAnswerType = answerType;
				}else
				{
					finalAnswerType =  "date";
				}
			}
			else if ((isNumeric(answerValue)) || (answerValue.matches("\\d.*")))
			{					
				if (answerType.toLowerCase().equals("number")) {
					finalAnswerType = answerType;
				}else{					
					finalAnswerType = "number";
				}
			}else if ((answerValue.toString().equals("true")) || (answerValue.toString().equals("false"))){
				if (answerType.toLowerCase().equals("boolean")) {
					finalAnswerType = answerType;
				}else{					
					finalAnswerType = "boolean";
				}
			}			
			else if (answerValue.toLowerCase().matches("\\w.*")){
				if (answerType.toLowerCase().equals("string")) {
					finalAnswerType = answerType;
				}else{
					finalAnswerType =  "string";
				}
			}		
			return finalAnswerType;
		}
		
		//Check Query Modifier on SPARQL query
		public String queryModifierChecking (String partOfSparql) {
			Pattern p = Pattern.compile("(GROUP BY|HAVING|ORDER BY|LIMIT)");
			Matcher m = p.matcher(partOfSparql);
			
			String queryModifier = ""; 
			if (m.find()) {
				if (partOfSparql.contains("GROUP BY")) {
					queryModifier = "GROUP BY";
				}else if (partOfSparql.contains("HAVING")) {
				  	queryModifier = "HAVING";
				} else if (partOfSparql.contains("ORDER BY")) {
					queryModifier = "ORDER BY";
				} else {
					queryModifier = "LIMIT";
				}			
			}
			return queryModifier;			
		}
		
		//Check Aggregation Value
		public String AggregationChecking (String sparqlValue)
		{
			Pattern p = Pattern.compile("(COUNT|SUM|AVG|MIN|MAX|SAMPLE|GROUP_CONCAT|GROUPCONCAT|VECTOR_AGG|COUNT DISTINCT)");
			Matcher m = p.matcher(sparqlValue);
			
			if (m.find()) {			
				return ("true");
			}else
			{						
				return ("false");
			}			
		}
		
		//Check onlyDbo Value
		public String onlyDboChecking (String sparqlQuery) {
			if (sparqlQuery.toString().toLowerCase().contains("dbo:"))
			{
				return ("true");
			}else
			{
				return ("false");
			}
		}
		
		//Check Hybrid Value
		public String HybridChecking (String sparqlValue) {
			if (sparqlValue.toLowerCase().contains("text:")) {
				return ("true");
			}else
			{
				return ("false");
			}
		}
		
		//Convert a string into a number	
		public static boolean isNumeric(String str)  
		{  
		  try  
		  {  
		    double d = Double.parseDouble(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
		}
		
		//Check whether a string is a date	
		public static boolean validateDateFormat(String input) {

	        return input.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})");
	    }
		
		//Check Out of Scope Value
		//Check Out of Scope Value
		public String outOfScopeChecking(String sparqlQuery, String languageToQuestionEn) {		
			SparqlService ss = new SparqlService();	
			String resultStatus="";
			if (ss.isASKQuery(languageToQuestionEn)) {			
				if (ss.getResultAskQuery(sparqlQuery).equals("null")) {
					resultStatus = "false";
				}else {
					resultStatus = "true";
				}
					
			}else {
				if (ss.isNullAnswerFromEndpoint(sparqlQuery)) {
					resultStatus = "false";
				}else {
					resultStatus = "true";
				}
							
			}
			return resultStatus;
		}
		// did item feature cureated?
		public Boolean isItemCurated (int userId, String id, String datasetVersion, String item) {
			BasicDBObject searchObj = new BasicDBObject();
			searchObj.put("userId", userId);
			searchObj.put("logType", "curate");
			searchObj.put("logInfo.id", id);
			searchObj.put("logInfo.datasetVersion", datasetVersion);
			searchObj.put("logInfo.field", item);
			try {
				DB db = MongoDBManager.getDB("QaldCuratorFiltered");
				DBCollection coll = db.getCollection("UserLog");
				DBCursor cursor = coll.find(searchObj);
				while (cursor.hasNext()) {
					return true;
				}
			}catch (Exception e) {}
			return false;			
		}
		public int countQaldDataset(int userId, String datasetVersion) {
			BasicDBObject searchObj = new BasicDBObject();
			searchObj.put("userId", userId);
			searchObj.put("datasetVersion", datasetVersion);
			try {
					//call mongoDb
					DB db = MongoDBManager.getDB("QaldCuratorFiltered"); //Database Name
					DBCollection coll = db.getCollection("UserDatasetCorrection"); //Collection
					DBCursor cursor = coll.find(searchObj); //Find All
					
					return cursor.count();				
				} catch (Exception e) {}
			
			return 0;
		}
		//determine previous document
		public UserDatasetCorrection getPreviousDocument(int userId, String currentId, String datasetVersion) {
			BasicDBObject searchObj = new BasicDBObject();
			BasicDBObject cSearchObj = new BasicDBObject();
			cSearchObj.put("$lt", currentId);
			searchObj.put("id", cSearchObj);
			searchObj.put("userId", userId);
			BasicDBObject sortObj = new BasicDBObject();
			sortObj.put("id", -1);
			UserDatasetCorrection item = new UserDatasetCorrection();
			try {
				//call mongoDb
				DB db = MongoDBManager.getDB("QaldCuratorFiltered"); //Database Name
				DBCollection coll = db.getCollection("UserDatasetCorrection"); //Collection
				DBCursor cursor = coll.find(searchObj).sort(sortObj).limit(1); 
				while (cursor.hasNext()) {
					DBObject dbobj = cursor.next();
					Gson gson = new GsonBuilder().create();
					UserDatasetCorrection q = gson.fromJson(dbobj.toString(),UserDatasetCorrection.class);
					item.setId(q.getId());
					item.setDatasetVersion(q.getDatasetVersion());
					item.setAnswerType(q.getAnswerType());
					item.setAggregation(q.getAggregation());
					item.setOnlydbo(q.getOnlydbo());
					item.setHybrid(q.getHybrid());
					item.setLanguageToQuestion(q.getLanguageToQuestion());
					item.setLanguageToKeyword(q.getLanguageToKeyword());
					item.setSparqlQuery(q.getSparqlQuery());
					item.setPseudoSparqlQuery(q.getPseudoSparqlQuery());
					item.setGoldenAnswer(q.getGoldenAnswer());
					item.setOutOfScope(q.getOutOfScope());
					item.setUserId(q.getUserId());
					item.setRevision(q.getRevision());
					item.setLastRevision(q.getLastRevision());
					item.setTransId(q.getTransId());
					item.setStatus(q.getStatus());
				}
								
			} catch (Exception e) {}
			return item;
		}
		//determine next document
		public UserDatasetCorrection getNextDocument(int userId, String currentId, String datasetVersion) {
			BasicDBObject searchObj = new BasicDBObject();
			BasicDBObject cSearchObj = new BasicDBObject();
			cSearchObj.put("$gt", currentId);
			searchObj.put("id", cSearchObj);
			searchObj.put("userId", userId);
			
			BasicDBObject sortObj = new BasicDBObject();
			sortObj.put("id", 1);
			UserDatasetCorrection item = new UserDatasetCorrection();
			try {
				//call mongoDb
				DB db = MongoDBManager.getDB("QaldCuratorFiltered"); //Database Name
				DBCollection coll = db.getCollection("UserDatasetCorrection"); //Collection
				DBCursor cursor = coll.find(searchObj).sort(sortObj).limit(1); 
				while (cursor.hasNext()) {
					DBObject dbobj = cursor.next();
					Gson gson = new GsonBuilder().create();
					UserDatasetCorrection q = gson.fromJson(dbobj.toString(),UserDatasetCorrection.class);
					item.setId(q.getId());
					item.setDatasetVersion(q.getDatasetVersion());
					item.setAnswerType(q.getAnswerType());
					item.setAggregation(q.getAggregation());
					item.setOnlydbo(q.getOnlydbo());
					item.setHybrid(q.getHybrid());
					item.setLanguageToQuestion(q.getLanguageToQuestion());
					item.setLanguageToKeyword(q.getLanguageToKeyword());
					item.setSparqlQuery(q.getSparqlQuery());
					item.setPseudoSparqlQuery(q.getPseudoSparqlQuery());
					item.setGoldenAnswer(q.getGoldenAnswer());
					item.setOutOfScope(q.getOutOfScope());
					item.setUserId(q.getUserId());
					item.setRevision(q.getRevision());
					item.setLastRevision(q.getLastRevision());
					item.setTransId(q.getTransId());
					item.setStatus(q.getStatus());
				}
								
			} catch (Exception e) {}
			return item;
		}
		
		public List<CorrectionSummary> getCorrectionSummary() {
			List<CorrectionSummary> csList = new ArrayList<CorrectionSummary>();List<User> users = new ArrayList<User>();
			 BasicDBObject searchObj = new BasicDBObject();
			 searchObj.put("id", 1);
			 try {
				//call mongoDb
				DB db = MongoDBManager.getDB("QaldCuratorFiltered"); //Database Name
				DBCollection coll = db.getCollection("User"); //Collection
				DBCursor cursor = coll.find().sort(searchObj); //Find All
				while (cursor.hasNext()) {
					DBObject dbobj = cursor.next();
					Gson gson = new GsonBuilder().create();
					
					User q = gson.fromJson(dbobj.toString(), User.class);
					
					CorrectionSummary itemCS = new CorrectionSummary();
					itemCS.setUserId(q.getId());
					itemCS.setName(q.getName());
					itemCS.setUsername(q.getUsername());
					itemCS.setQald1(countQaldDataset(q.getId(), "QALD1_Test_dbpedia")+countQaldDataset(q.getId(), "QALD1_Train_dbpedia"));
					itemCS.setQald2(countQaldDataset(q.getId(), "QALD2_Test_dbpedia")+countQaldDataset(q.getId(), "QALD2_Train_dbpedia"));
					itemCS.setQald3(countQaldDataset(q.getId(), "QALD3_Test_dbpedia")+countQaldDataset(q.getId(), "QALD3_Train_dbpedia"));
					itemCS.setQald4(countQaldDataset(q.getId(), "QALD4_Test_Multilingual")+countQaldDataset(q.getId(), "QALD4_Train_Multilingual"));
					itemCS.setQald5(countQaldDataset(q.getId(), "QALD5_Test_Multilingual")+countQaldDataset(q.getId(), "QALD5_Train_Multilingual"));
					itemCS.setQald6(countQaldDataset(q.getId(), "QALD6_Test_Multilingual")+countQaldDataset(q.getId(), "QALD6_Train_Multilingual"));
					itemCS.setQald7(countQaldDataset(q.getId(), "QALD7_Test_Multilingual")+countQaldDataset(q.getId(), "QALD7_Train_Multilingual"));
					itemCS.setQald8(countQaldDataset(q.getId(), "QALD8_Test_Multilingual")+countQaldDataset(q.getId(), "QALD8_Train_Multilingual"));
					csList.add(itemCS);
					
				}
				return csList;
			 }catch (Exception e) {
				 
			 }
			return null;
		}
		
}
