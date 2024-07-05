package com.irononetech.android.XML;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.dataretrievecomponent.DataRetrieveWebServiceObject;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.formcomponent.view.NewSelectVehicleClassMapping;
import com.irononetech.android.formcomponent.view.OldSelectVehicleClassMapping;
import com.irononetech.android.formcomponent.view.PossibleDRMapping;
import com.irononetech.android.imageuploadcomponent.ImageUploadEnums;
import com.irononetech.android.searchcomponent.SearchWebServiceObject;
import com.irononetech.android.template.GenException;

public class XMLHandler {

	static Logger LOG = LoggerFactory.getLogger(XMLHandler.class);

	public static void getResponse(WebServiceObject webServiceObject) throws GenException, Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			String responseXML = webServiceObject.getXmlText();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(is);		// this.getInputStream());
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("Status");	// ITEM);

			// LogFile.d("XMLHANDLER_getResponse= ", + items.getLength() + "=="
			// );

			for (int i = 0; i < items.getLength(); i++) {
				// Message message = new Message();
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String name = property.getNodeName();
					if (name.equalsIgnoreCase("code")) {
						String codeValue = property.getFirstChild().getNodeValue();
						webServiceObject.setCode(codeValue);
					} else if (name.equalsIgnoreCase("description")) {
						String disValue = property.getFirstChild().getNodeValue();
						webServiceObject.setDescription(disValue);
					}
					if (name.equalsIgnoreCase("visitId")) {
						String val = property.getFirstChild().getNodeValue();
						webServiceObject.setVisitId(val);
					}
					
					
					if (name.equalsIgnoreCase("CurrentWebVersion")) {
						String val = property.getFirstChild().getNodeValue();
						webServiceObject.setCurrentWebVersion(val);
					}
					if (name.equalsIgnoreCase("MinimumSupportedAppVersion")) {
						String val = property.getFirstChild().getNodeValue();
						webServiceObject.setMinimumSupportedAppVersion(val);
					}
					if (name.equalsIgnoreCase("LatestAppVersioninGooglePlay")) {
						String val = property.getFirstChild().getNodeValue();
						webServiceObject.setLatestAppVersioninGooglePlay(val);
					}
					if (name.equalsIgnoreCase("ForceLatestVersionToUsers")) {
						String val = property.getFirstChild().getNodeValue();
						webServiceObject.setForceLatestVersionToUsers(val);
					}
					if (name.equalsIgnoreCase("GooglePlayAppURL")) {
						String val = property.getFirstChild().getNodeValue();
						webServiceObject.setGooglePlayAppURL(val);
					}
				}
			}
			// return webServiceObject;
		} catch (Exception e) {
			LOG.error("getResponse:10408");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}


	public static void getSearchResponseByVehicleNoOrJobNo(WebServiceObject webServiceObject) throws GenException, Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			String responseXML = webServiceObject.getXmlText();
			//LogFile.d("\nINFO ", "getSearchResponseByVehicleNoOrJobNo: " + responseXML);

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));
			ArrayList<String> jobList = new ArrayList<String>();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			doc.getDocumentElement().normalize();

			//Element root = dom.getDocumentElement();
			//NodeList items = root.getElementsByTagName("Data"); // ITEM);
			String jobNo = "";
			String vehicleNo = "";

			NodeList nList_Job = doc.getElementsByTagName("Job");
			for (int temp = 0; temp < nList_Job.getLength(); temp++) {
				Node nNode = nList_Job.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					jobNo = eElement.getElementsByTagName("JobNo").item(0).getTextContent();
					vehicleNo = eElement.getElementsByTagName("VehicleNo").item(0).getTextContent();
				}

				NodeList nList_Visits = nList_Job.item(temp).getChildNodes();// .getElementsByTagName("Visit");

				for (int tem = 0; tem < nList_Visits.getLength(); tem++) {
					if (nList_Visits.item(tem).getNodeName().equalsIgnoreCase("Visits")) {

						NodeList nList_Visit = nList_Visits.item(tem).getChildNodes();

						for (int temi = 0; temi < nList_Visit.getLength(); temi++) {
							if (nList_Visit.item(temi).getNodeName().equalsIgnoreCase("Visit")) {

								Node nNodei = nList_Visit.item(temi);

								if (nNodei.getNodeType() == Node.ELEMENT_NODE) {
									Element eElementi = (Element) nNodei;

									//NodeList no = eElementi.getChildNodes();

									/*for (int temii = 0; temii < no.getLength(); temii++) {
									if (no.item(temii).getNodeType() == Node.ELEMENT_NODE) {
										Element eElementii = (Element) no.item(temii);*/

									//String ssds = eElementi.getElementsByTagName("VisitId").item(0).getTextContent();

									String vId = eElementi.getElementsByTagName("VisitId").item(0).getTextContent();
									String vNameId = eElementi.getElementsByTagName("VisitTypeId").item(0).getTextContent();
									String vName = eElementi.getElementsByTagName("VisitType").item(0).getTextContent();
									String vDate = eElementi.getElementsByTagName("VisitDate").item(0).getTextContent().split(" ")[0];
									//String spChar = " \u00BB  ";  //Unicode char of >>

									if (Application.isInVisitPage) {
										if (vNameId.equalsIgnoreCase("0")){
											jobList.add(jobNo + "_" + vehicleNo + "_" + vDate);
											nvps.add(new BasicNameValuePair(vId, jobNo + "_" + vehicleNo + "_" + vDate));
											//
										}
										//nvps.add(new BasicNameValuePair("Job No: ",	jobNo));
									} else {
										if (vNameId.equalsIgnoreCase("0")){
											jobList.add(jobNo + "|" + vehicleNo);
											nvps.add(new BasicNameValuePair("-1", vId + "_" + jobNo));
										}
										jobList.add(vName + "_" + vDate);
										nvps.add(new BasicNameValuePair(vId, vName + "_" + vehicleNo + "_" + vDate));											
									}
								}
							}
						}
					}
				}
			}

			((SearchWebServiceObject) webServiceObject).setJobsByVehicleNoOrJobNo(jobList);
			Application.setJobOrVehicleNoWithVisitId(nvps);
			//((SearchWebServiceObject) webServiceObject).setSearchedJobsWithVisitId(nvps);
		} catch (Exception e) {
			LOG.error("getSearchResponseByVehicleNoOrJobNo:10419");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}


	public static void getJobDetailsByVisitId(WebServiceObject webServiceObject) throws GenException, Exception {

		String responseXML = webServiceObject.getXmlText();
		//LogFile.d("\nINFO ", TAG + "getJobDetailsByVisitId  RESPONSEXML: " + responseXML);

		try {
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));
			FormObject formObject = Application.createFormObjectInstance();	// new FormObject();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("FieldList");

			//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy KK:mm a");// "MMMMM d, yyyy h:mm a");
			// SimpleDateFormat formatter = new
			// SimpleDateFormat("MMMMM d, yyyy h:mm a");
			//String StartDateTime = formatter.format(Calendar.getInstance().getTime());
			
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					formObject.setJobNo(getTagValue("JobNo", eElement));
					formObject.setTimeReported(getTagValue("TimeReported", eElement) == null ? "" : getTagValue("TimeReported", eElement));
					formObject.setOrgTimeReported(getTagValue("OriginalTimeReported", eElement) == null ? "" : getTagValue("OriginalTimeReported", eElement));
					formObject.setTimeVisited(getTagValue("TimeVisited", eElement) == null ? "" : getTagValue("TimeVisited", eElement));
					formObject.setVehicleNo(getTagValue("VehicleNo", eElement) == null ? "" : getTagValue("VehicleNo", eElement));
					formObject.setNameofCaller(getTagValue("CallerName", eElement) == null ? "" : getTagValue("CallerName", eElement));
					formObject.setContactNo(getTagValue("CallerContactNo", eElement) == null ? "" : getTagValue("CallerContactNo", eElement));
					formObject.setNameofInsured(getTagValue("InsuredName", eElement) == null ? "" : getTagValue("InsuredName", eElement));
					formObject.setVehicleTypeandColor(getTagValue("VehicleTypeColor", eElement) == null ? "" : getTagValue("VehicleTypeColor", eElement));
					formObject.setDateandTimeofAccident(getTagValue("AccidentTime", eElement) == null ? "" : getTagValue("AccidentTime", eElement));
					formObject.setLocationofAccident(getTagValue("AccidentLocation", eElement) == null ? "" : getTagValue("AccidentLocation", eElement));
					formObject.setPolicyCoverNoteNo(getTagValue("Policy_CoverNoteNo", eElement) == null ? "" : getTagValue("Policy_CoverNoteNo", eElement));
					formObject.setPolicyCoverNoteSerialNo(getTagValue("Policy_CoverNoteSerialNo", eElement) == null ? "" : getTagValue("Policy_CoverNoteSerialNo", eElement));
					formObject.setCoverNoteIssuedBy(getTagValue("CoverNoteIssuedBy", eElement) == null ? ""	: getTagValue("CoverNoteIssuedBy", eElement));
					formObject.setReasonsforIssuingCoverNote(getTagValue("CoverNoteReasons", eElement) == null ? ""	: getTagValue("CoverNoteReasons", eElement));
					formObject.setChasisNo(getTagValue("ChassisNo", eElement) == null ? "" : getTagValue("ChassisNo", eElement));
					formObject.setEngineNo(getTagValue("EngineNo", eElement) == null ? "" : getTagValue("EngineNo", eElement));
					formObject.setDriversName(getTagValue("DriverName",	eElement) == null ? "" : getTagValue("DriverName", eElement));
					formObject.setDrivingLicenceNo(getTagValue("LicenceNo",	eElement) == null ? "" : getTagValue("LicenceNo", eElement));
					formObject.setExpiryDateOfLicence(getTagValue("LicenceExpiryDate", eElement) == null ? "" : getTagValue("LicenceExpiryDate", eElement));
					formObject.setTypeOfDrivingLicence(getTagValue("LicenceType", eElement) == null ? "0" : getTagValue("LicenceType", eElement));
					formObject.setNICNoOfDriver(getTagValue("DriverNIC", eElement) == null ? "" : getTagValue("DriverNIC", eElement));
					
					/*
					 * Thisaru Guruge
					 * 01 / 02 / 2016
					 * To add new NIC type, We have to add the separate variable to save the type of NIC
					 * This is to set it.
					 */
					formObject.setTypeOfNICNo(getTagValue("NICType", eElement) == null ? "" : getTagValue("NICType", eElement));
					
					formObject.setCompetence(getTagValue("DriverCompetence", eElement) == null ? "1" : getTagValue("DriverCompetence", eElement));
					formObject.setMeterReading(getTagValue("MeterReading", eElement) == null ? "" : getTagValue("MeterReading", eElement));
					formObject.setAreTheyContributory(getTagValue("TyreContributory", eElement) == null ? "1" : getTagValue("TyreContributory", eElement));
					formObject.setTypeOfGoodsCarried(getTagValue("GoodsType", eElement) == null ? "" : getTagValue("GoodsType", eElement));
					formObject.setWeightOfGoodsCarried(getTagValue("GoodsWeight", eElement) == null ? "" : getTagValue("GoodsWeight", eElement));
					formObject.setDamagesToTheGoods(getTagValue("GoodsDamages", eElement) == null ? "" : getTagValue("GoodsDamages", eElement));
					formObject.setOverLoaded(getTagValue("OverLoaded", eElement) == null ? "1" : getTagValue("OverLoaded", eElement));
					formObject.setOverWeightContributory(getTagValue("OverWeightContributory", eElement) == null ? "1" : getTagValue("OverWeightContributory", eElement));
					formObject.setOtherVehiclesInvolved(getTagValue("OtherVehiclesInvolved", eElement) == null ? ""	: getTagValue("OtherVehiclesInvolved", eElement));
					formObject.setThirdPartyDamagesProperty(getTagValue("ThirdPartyDamages", eElement) == null ? "" : getTagValue("ThirdPartyDamages", eElement));
					formObject.setInjuries_InsuredAnd3rdParty(getTagValue("Injuries", eElement) == null ? "" : getTagValue("Injuries", eElement));
					formObject.setNearestPoliceStation(getTagValue("NearestPoliceStation", eElement) == null ? "" : getTagValue("NearestPoliceStation", eElement));
					formObject.setPurposeOfJourney(getTagValue("JourneyPurpose", eElement) == null ? "1" : getTagValue("JourneyPurpose", eElement));
					formObject.setRelationshipBetweenDriverAndInsured(getTagValue("DriverRelationship", eElement) == null ? "1" : getTagValue("DriverRelationship", eElement));
					formObject.setPAVValue(getTagValue("PavValue", eElement) == null ? "" : getTagValue("PavValue", eElement));
					//formObject.setComments(getTagValue("Comment", eElement) == null ? "" : getTagValue("Comment", eElement));
					formObject.setAppCost(getTagValue("ApproxRepairCost", eElement) == null ? "" : getTagValue("ApproxRepairCost", eElement));
					formObject.setOnSiteEstimation(getTagValue("SiteEstimation", eElement) == null ? "0" : getTagValue("SiteEstimation", eElement));
					formObject.setpossibleDR_Other(getTagValue("OtherPossibleDR", eElement) == null ? "" : getTagValue("OtherPossibleDR", eElement));
					formObject.setdamagedItemsOtherField(getTagValue("OtherDamagedItems", eElement) == null ? "" : getTagValue("OtherDamagedItems", eElement));
					formObject.setpredamagedItemsOtherField(getTagValue("OtherPreAccidentDamages", eElement) == null ? "" : getTagValue("OtherPreAccidentDamages", eElement));
					formObject.setvehicleType(getTagValue("VehicleType", eElement) == null ? 0 : Integer.parseInt(getTagValue("VehicleType", eElement)));
					// formObject.setClaimProcessBranch(getTagValue("ClaimProcessBranch", eElement)==null? "1" :getTagValue("ClaimProcessBranch", eElement));
					formObject.setClaimProcessingBranch(getTagValue("ClaimProcessBranch", eElement) == null ? "" : getTagValue("ClaimProcessBranch", eElement));
					formObject.setContactNooftheInsured(getTagValue("ContactNoOfTheInsured", eElement) == null ? "" : getTagValue("ContactNoOfTheInsured", eElement));
					formObject.setConsistancyByCSR(getTagValue("CSRConsistency", eElement) == null ? "1" : getTagValue("CSRConsistency", eElement));
					formObject.setCSRUserName(getTagValue("CSRUserName", eElement) == null ? "" : getTagValue("CSRUserName", eElement));
					// formObject.setSvrImageCount(getTagValue("TotalImageCount", eElement)==null? "0" :getTagValue("TotalImageCount", eElement));
					formObject.setVisitId(getTagValue("VisitId", eElement) == null ? "" : getTagValue("VisitId", eElement));
					//further_review_new
					formObject.setIsFurtherReviewNeeded(getTagValue("FurtherReview", eElement) == null? "0" : getTagValue("FurtherReview", eElement));
				}
			}

			formObject.setComments(getComments(webServiceObject));

			getImageDetailsFromServer(webServiceObject, false, formObject, null);

			// ---------------------------------------------------------------------------------------------
			/*NodeList nDamagedItemNodes = doc.getElementsByTagName("DamagedItems");
			for (int t = 0; t < nDamagedItemNodes.getLength(); t++) {

				Node nNode1 = nDamagedItemNodes.item(t);
				if (nNode1.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement1 = (Element) nNode1;
					formObject.setvehicleType(getTagValue("Type", eElement1) == null ? 0 : Integer.parseInt(getTagValue("Type", eElement1)));
				}
			}*/

			int p1 = Application.get4DArrSizeSec1();
			int p2 = Application.get4DArrSizeSec2();
			int p3 = Application.get4DArrSizeSec3();
			int p4 = Application.get4DArrSizeSec4();

			boolean[][][][] damagedItemsArray = new boolean[p1][p2][p3][p4];
			for (int i = 0; i < p1; i++) {
				for (int j = 0; j < p2; j++) {
					for (int k = 0; k < p3; k++) {
						damagedItemsArray[i][j][k][0] = false;
						damagedItemsArray[i][j][k][1] = false;
					}
				}
			}

			NodeList nDamagedItemsList = doc.getElementsByTagName("DamagedItems");
			for (int u = 0; u < nDamagedItemsList.getLength(); u++) {

				Node nNode2 = nDamagedItemsList.item(u);
				if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

					NodeList damagedItemsNodeList = nNode2.getChildNodes();

					for (int k = 0; k < damagedItemsNodeList.getLength(); k++) {
						Node itemNode = damagedItemsNodeList.item(k);
						String itemNodeName = itemNode.getNodeName();
						if (itemNodeName.equalsIgnoreCase("item")) {
							String datalistItem = itemNode.getFirstChild().getNodeValue();
							String[] listItems = datalistItem.split("\\/");
							//damagedItemsArray[Integer.parseInt(listItems[0])][Integer.parseInt(listItems[1])][Integer.parseInt(listItems[2])][Integer.parseInt(listItems[3])] = true;
							damagedItemsArray[Integer.parseInt("0")][Integer.parseInt(listItems[1])][Integer.parseInt(listItems[2])][Integer.parseInt("0")] = true;
						}
					}
				}
			}

			if (formObject.getvehicleType() == 1) {
				formObject.setBooleanlistCar(damagedItemsArray);
			} else if (formObject.getvehicleType() == 2) {
				formObject.setBooleanlistCar(damagedItemsArray);
			} else if (formObject.getvehicleType() == 3) {
				formObject.setBooleanlistLorry(damagedItemsArray);
			} else if (formObject.getvehicleType() == 4) {
				formObject.setBooleanlistVan(damagedItemsArray);
			} else if (formObject.getvehicleType() == 5) {
				formObject.setBooleanlistThreeWheel(damagedItemsArray);
			} else if (formObject.getvehicleType() == 6) {
				formObject.setBooleanlistMotorcycle(damagedItemsArray);
			} else if (formObject.getvehicleType() == 7) {
				formObject.setBooleanlistTractor4WD(damagedItemsArray);
			} else if (formObject.getvehicleType() == 8) {
				formObject.setBooleanlistHandTractor(damagedItemsArray);
			}
			// -----------------------------------------------------------------------------------------------------

			// ------------------------------PRE--------------------------------------------------------------------
			/*NodeList npreDamagedItemNodes = doc.getElementsByTagName("PreAccidentDamages");
			for (int v = 0; v < npreDamagedItemNodes.getLength(); v++) {

				Node npreNode = npreDamagedItemNodes.item(v);
				if (npreNode.getNodeType() == Node.ELEMENT_NODE) {

					Element epreElement = (Element) npreNode;
					formObject.setvehicleType(getTagValue("Type", epreElement) == null ? 0 : Integer.parseInt(getTagValue("Type", epreElement)));
				}
			}*/

			boolean[][][][] predamagedItemsArray = new boolean[p1][p2][p3][p4];
			for (int i = 0; i < p1; i++) {
				for (int j = 0; j < p2; j++) {
					for (int k = 0; k < p3; k++) {
						predamagedItemsArray[i][j][k][0] = false;
						predamagedItemsArray[i][j][k][1] = false;
					}
				}
			}

			NodeList npreDamagedItemsList = doc.getElementsByTagName("PreAccidentDamages");
			for (int w = 0; w < npreDamagedItemsList.getLength(); w++) {

				Node npreNode = npreDamagedItemsList.item(w);
				if (npreNode.getNodeType() == Node.ELEMENT_NODE) {

					NodeList predamagedItemsNodeList = npreNode.getChildNodes();

					for (int k = 0; k < predamagedItemsNodeList.getLength(); k++) {
						Node preitemNode = predamagedItemsNodeList.item(k);
						String itempreNodeName = preitemNode.getNodeName();
						if (itempreNodeName.equalsIgnoreCase("item")) {
							String predatalistItem = preitemNode.getFirstChild().getNodeValue();
							String[] prelistItems = predatalistItem.split("\\/");
							//predamagedItemsArray[Integer.parseInt(prelistItems[0])][Integer.parseInt(prelistItems[1])][Integer.parseInt(prelistItems[2])][Integer.parseInt(prelistItems[3])] = true;
							predamagedItemsArray[Integer.parseInt("0")][Integer.parseInt(prelistItems[1])][Integer.parseInt(prelistItems[2])][Integer.parseInt("0")] = true;
						}
					}
				}
			}

			if (formObject.getvehicleType() == 1) {
				formObject.setBooleanprelistCar(predamagedItemsArray);
			} else if (formObject.getvehicleType() == 2) {
				formObject.setBooleanprelistCar(predamagedItemsArray);
			} else if (formObject.getvehicleType() == 3) {
				formObject.setBooleanprelistLorry(predamagedItemsArray);
			} else if (formObject.getvehicleType() == 4) {
				formObject.setBooleanprelistVan(predamagedItemsArray);
			} else if (formObject.getvehicleType() == 5) {
				formObject.setBooleanprelistThreeWheel(predamagedItemsArray);
			} else if (formObject.getvehicleType() == 6) {
				formObject.setBooleanprelistMotorcycle(predamagedItemsArray);
			} else if (formObject.getvehicleType() == 7) {
				formObject.setBooleanprelistTractor4WD(predamagedItemsArray);
			} else if (formObject.getvehicleType() == 8) {
				formObject.setBooleanprelistHandTractor(predamagedItemsArray);
			}
			// -----------------------------------------------------------------------------------------------

			NodeList nPossibleDRList = doc.getElementsByTagName("PossibleDR");
			for (int x = 0; x < nPossibleDRList.getLength(); x++) {

				Node nNode3 = nPossibleDRList.item(x);
				if (nNode3.getNodeType() == Node.ELEMENT_NODE) {

					NodeList vehicleClassNodeList = nNode3.getChildNodes();
					boolean[] possibleDR = new boolean[PossibleDRMapping.values().length];
					Arrays.fill(possibleDR, Boolean.FALSE);

					for (int k = 0; k < vehicleClassNodeList.getLength(); k++) {
						Node itemNode = vehicleClassNodeList.item(k);
						String classNodeName = itemNode.getNodeName();
						if (classNodeName.equalsIgnoreCase("item")) {
							String item = itemNode.getFirstChild().getNodeValue();
							int i = 0;
							for (PossibleDRMapping fm : PossibleDRMapping.values()) {
								if (fm.getString().equalsIgnoreCase(item)) {
									// LogFile.d("\n\n", fm.getString() +"==" +
									// item + "==" + i);
									possibleDR[i] = true;
								}
								i++;
							}
						}
					}
					formObject.setselectPossibleDR(possibleDR);
				}
			}

			NodeList nPolicyList = doc.getElementsByTagName("Policy_CoverNotePeriod");
			for (int w = 0; w < nPolicyList.getLength(); w++) {
				Node nNode4 = nPolicyList.item(w);
				if (nNode4.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement1 = (Element) nNode4;
					formObject.setPolicyCoverNotePeriodFrom(getTagValue("from", eElement1) == null ? "" : getTagValue("from", eElement1));
					formObject.setPolicyCoverNotePeriodTo(getTagValue("to", eElement1) == null ? "" : getTagValue("to", eElement1));
				}
			}

			NodeList nLicenseNewOldList = doc.getElementsByTagName("LicenseNewOld");
			for (int temp = 0; temp < nLicenseNewOldList.getLength(); temp++) {
				Node nNode = nLicenseNewOldList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					formObject.setSelectDL_NewOld(getTagValue("IsNew", eElement) == null ? "1" : getTagValue("IsNew", eElement));
				}
			}

			NodeList nVehicleClassList = doc.getElementsByTagName("VehicleClass");
			for (int temp = 0; temp < nVehicleClassList.getLength(); temp++) {
				Node nNode = nVehicleClassList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					if (formObject.getSelectDL_NewOld().equals("0")) {
						NodeList vehicleClassNodeList = nNode.getChildNodes();
						boolean[] vehicleClassOld = new boolean[12];
						Arrays.fill(vehicleClassOld, Boolean.FALSE);
						for (int k = 0; k < vehicleClassNodeList.getLength(); k++) {
							Node classNode = vehicleClassNodeList.item(k);
							String classNodeName = classNode.getNodeName();
							if (classNodeName.equalsIgnoreCase("class")) {
								String vehiClass = classNode.getFirstChild().getNodeValue();
								int i = 0;
								for (OldSelectVehicleClassMapping fm : OldSelectVehicleClassMapping.values()) {
									if (Integer.toString(fm.getInt()).equalsIgnoreCase(vehiClass)) {
										vehicleClassOld[i] = true;
									}
									i++;
								}
							}
						}
						formObject.setselectVehicleClassForOld(vehicleClassOld);
					} else if (formObject.getSelectDL_NewOld().equals("1")) {
						NodeList vehicleClassNodeList = nNode.getChildNodes();
						boolean[] vehicleClassNew = new boolean[13];
						Arrays.fill(vehicleClassNew, Boolean.FALSE);
						for (int k = 0; k < vehicleClassNodeList.getLength(); k++) {
							Node classNode = vehicleClassNodeList.item(k);
							String classNodeName = classNode.getNodeName();
							if (classNodeName.equalsIgnoreCase("class")) {
								String vehiClass = classNode.getFirstChild().getNodeValue();
								int i = 0;
								for (NewSelectVehicleClassMapping fm : NewSelectVehicleClassMapping.values()) {
									if (Integer.toString(fm.getInt()).equalsIgnoreCase(vehiClass)) {
										vehicleClassNew[i] = true;
									}
									i++;
								}
							}
						}
						formObject.setselectVehicleClassForNew(vehicleClassNew);
					}
				}
			}

			NodeList nTyreConditionList = doc.getElementsByTagName("TyreCondition");
			int[] tyreCondition = new int[6];
			for (int temp = 0; temp < nTyreConditionList.getLength(); temp++) {

				Node nNode = nTyreConditionList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					try {
						tyreCondition[0] = Integer.parseInt(getTagValue("FR", eElement));
					} catch (Exception e) {
						tyreCondition[0] = 1;
					}
					try {
						tyreCondition[1] = Integer.parseInt(getTagValue("FL", eElement));
					} catch (Exception e) {
						tyreCondition[1] = 1;
					}
					try {
						tyreCondition[2] = Integer.parseInt(getTagValue("RRL", eElement));
					} catch (Exception e) {
						tyreCondition[2] = 1;
					}
					try {
						tyreCondition[3] = Integer.parseInt(getTagValue("RLR", eElement));
					} catch (Exception e) {
						tyreCondition[3] = 1;
					}
					try {
						tyreCondition[4] = Integer.parseInt(getTagValue("RLL", eElement));
					} catch (Exception e) {
						tyreCondition[4] = 1;
					}
					try {
						tyreCondition[5] = Integer.parseInt(getTagValue("RRR", eElement));
					} catch (Exception e) {
						tyreCondition[5] = 1;
					}
					boolean[] tyreConditionBool = new boolean[Application.getTyreConditionArrSize()];
					Arrays.fill(tyreConditionBool, Boolean.FALSE);
					
					tyreConditionBool[tyreCondition[0] - 1] = true;
					int tmp = 3;  //3 means no of sub items minus 1
					for (int i = 1; i < tyreCondition.length; i++) {
						tyreConditionBool[tyreCondition[i] + tmp] = true;
						tmp += 4; //4 means no of sub items
					}
					
					/*tyreConditionBool[tyreCondition[1] + 2] = true;
					tyreConditionBool[tyreCondition[2] + 5] = true;
					tyreConditionBool[tyreCondition[3] + 8] = true;
					tyreConditionBool[tyreCondition[4] + 11] = true;
					tyreConditionBool[tyreCondition[5] + 14] = true;*/
					
					formObject.setselectTirecondition(tyreConditionBool);
				}
			}
			((DataRetrieveWebServiceObject) webServiceObject).setFormObject(formObject);

		} catch (Exception e) {
			LOG.error("getJobDetailsByVisitId:10410");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", "Error reading incoming data.\n" + e.getMessage());
		}
	}

	public static void getVisitDetailsByVisitId(WebServiceObject webServiceObject) throws GenException, Exception {

		String responseXML = webServiceObject.getXmlText();
		//LogFile.d("\nINFO ", TAG + "getVisitDetailsByVisitId  RESPONSEXML: " + responseXML);

		try {
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));
			VisitObject vObject = Application.createVisitObjectInstance();	// new VisitObject();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("FieldList");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					vObject.setVisitId(getTagValue("VisitId", eElement));
					vObject.setJobNo(getTagValue("JobNo", eElement));
					vObject.setChassisNo(getTagValue("ChassisNo", eElement) == null ? "" : getTagValue("ChassisNo", eElement));
					vObject.setEngineNo(getTagValue("EngineNo", eElement) == null ? "" : getTagValue("EngineNo", eElement));
					vObject.setVisitId(getTagValue("VisitId", eElement) == null ? "" : getTagValue("VisitId", eElement));
					//vObject.setVisitNo(getTagValue("VisitNo", eElement) == null ? "" : getTagValue("VisitNo", eElement));

					int inspTypeId = 0;
					if(getTagValue("VisitType", eElement) != null)
						inspTypeId = Integer.parseInt(getTagValue("VisitType", eElement));

					vObject.setInspectionType(inspTypeId);
					vObject.setInspectionTypeArrIndex(inspTypeId);

					vObject.setInspectionTypeInText(getTagValue("InspectionType",	eElement) == null ? "" : getTagValue("InspectionType", eElement));
					vObject.setInspectionDate(getTagValue("VisitDate", eElement) == null ? "" : getTagValue("VisitDate", eElement));
					//vObject.setVisitedById(getTagValue("VisitBy",	eElement) == null ? "" : getTagValue("VisitBy", eElement));
					//vObject.setVisitedByName(getTagValue("VisitByName", eElement) == null ? "" : getTagValue("VisitByName", eElement));
					//vObject.setVisitedByCSRCode(getTagValue("Code", eElement) == null ? "" : getTagValue("Code", eElement));

					vObject.setVisitFolderName(getTagValue("VisitId", eElement));
					vObject.setisSEARCH(true);

					vObject.setComments(getComments(webServiceObject));
					getImageDetailsFromServer(webServiceObject, true, null, vObject);

					((DataRetrieveWebServiceObject) webServiceObject).setVisitObject(vObject);
				}
			}
		} catch (Exception e) {
			LOG.error("getVisitDetailsByVisitId:11410");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	private static String getTagValue(String sTag, Element eElement) {
		try {
			if (sTag != null && eElement != null) {
				NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

				if (nlList != null) {
					Node nValue = (Node) nlList.item(0);
					if (nValue != null) {
						String value = nValue.getNodeValue().trim();
						//value = value.trim();
						return value;
					}
				}
			}
			return null;
		} catch (Exception e) {
			LOG.error("getTagValue:10411");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}


	private static String getComments(WebServiceObject webServiceObject){
		try {
			String responseXML = webServiceObject.getXmlText();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("CommentItem");
			String completeComment = "";
			String spChar = "\u00BB ";  //Unicode char of >>

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					String CommentedBy = getTagValue("CommentedBy", eElement);
					String CommentedDate = getTagValue("CommentedDate", eElement);
					String Comment = getTagValue("Comment", eElement);

					if(temp != 0){
						completeComment =	completeComment +
								spChar + CommentedBy + "   (" + CommentedDate + ")\n" + 											 
								Comment  + "\n\n";
					}else{
						completeComment = 	spChar + CommentedBy + "   (" + CommentedDate + ")\n" + 
								completeComment +
								Comment + "\n\n";
					}
				}
			}
			return completeComment;
		} catch (Exception e) {
			LOG.error("getComments:11410");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }			
			return "";
		}
	}

	private static String getImageDetailsFromServer(WebServiceObject webServiceObject, boolean isVist, FormObject fo, VisitObject vo){
		try {
			String responseXML = webServiceObject.getXmlText();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("ImageType");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					int ImageTypeId = Integer.parseInt(getTagValue("ImageTypeId", eElement));
					String imageNameList = getTagValue("ImageIds", eElement);  //Ex: 12,15,18
					String imageFolderName = "";

					for (ImageUploadEnums fm : ImageUploadEnums.values()) {
						if(ImageTypeId == fm.getInt()) {
							imageFolderName = fm.toString(); //.getString();
							break;
						}
					}

					if (!isVist) {  //SA Form
						String imgNameAndPath = URL.getSLIC_JOBS() + fo.getJobNo() + "/" + imageFolderName + "/##.jpg";
						ArrayList<String> tempImgArr = new ArrayList<String>();
						String[] arr = imageNameList.split(",");
						for (String imgName : arr) {
							tempImgArr.add(imgNameAndPath.replace("##", imgName));
						}

						switch (ImageTypeId) {
						case 1:
							fo.setPointOfImpactList(tempImgArr);
							break;
						case 3:
							fo.setAccidentImageList(tempImgArr);
							break;
						case 4:
							fo.setDLStatementImageList(tempImgArr);
							break;
						case 5:
							fo.setTechnicalOfficerCommentsImageList(tempImgArr);
							break;
						case 6:
							fo.setClaimFormImageImageList(tempImgArr);
							break;
						default:
							LOG.info("EXCEPTION ", "getImageDetailsFromServer:11512" + "Unknown Img Type " + ImageTypeId);
							
							break;
						}
					} else {	//Visit
						String imgNameAndPath = URL.getSLIC_VISITS() + vo.getVisitId() + "/DocImages/" + imageFolderName + "/##.jpg";
						ArrayList<String> tempImgArr = new ArrayList<String>();
						String[] arr = imageNameList.split(",");
						for (String imgName : arr) {
							tempImgArr.add(imgNameAndPath.replace("##", imgName));
						}

						switch (ImageTypeId) {
						case 5:
							vo.setTechnicalOfficerCommentsImageList(tempImgArr);
							break;
						case 20:
							vo.setInspectionPhotosSeenVisitsAnyOtherImageList(tempImgArr);
							break;
						case 21:
							vo.setEstimateAnyotherCommentsImageList(tempImgArr);
							break;
						default:
							LOG.info("EXCEPTION ", "getImageDetailsFromServer:11412" + "Unknown img type " + ImageTypeId);
							break;
						}
					}
				}
			}
			return "";
		} catch (Exception e) {
			LOG.error("getImageDetailsFromServer:11411");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}

	public static List<NameValuePair> getChatRecords(WebServiceObject ws) {
		try {
			//ArrayList<String> jobList = new ArrayList<String>();
			List<NameValuePair> list = new ArrayList<NameValuePair>();

			String responseXML = ws.getXmlText();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Visit");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					String VisitType = getTagValue("VisitType", eElement);
					String VisitDate = getTagValue("VisitDate", eElement);
					list.add(new BasicNameValuePair(VisitType + "_" + VisitDate, "-1"));

					NodeList nlList = eElement.getElementsByTagName("CommentItem");
					for (int i = 0; i < nlList.getLength(); i++) {
						Node nNode1 = nlList.item(i);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement1 = (Element) nNode1;

							String CommentedBy = getTagValue("CommentedBy", eElement1);
							String CommentedDate = getTagValue("CommentedDate", eElement1);
							String Comment = getTagValue("Comment", eElement1);

							list.add(new BasicNameValuePair(CommentedBy + "_" + CommentedDate, Comment));		
						}
					}
				}
			}
			return list;
		} catch (Exception e) {
			LOG.error("getComments:11410");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return null;
	}

	public static List<NameValuePair> getVisitStatusRecords(WebServiceObject ws) {
		return null;
	}
}