package com.irononetech.android.formcomponent;

import java.util.ArrayList;


public class FormResubmitObject {

		String refno = "";
		public String getRefNo(){
			return refno;
		}
		public void setRefNo(String name){
			refno = name;
		}
		
		boolean isSEARCH = false;
		public boolean getisSEARCH(){
			return isSEARCH;
		}
		public void setisSEARCH(boolean search){
			isSEARCH = search;
		}
		
		
		
		//------------------IMAGE SECTION--------------------------------------------
		//------------------Contain only the name without extension------------------

		//Point of Impact
		String pointofimpactimgname = "";
		public String getPointofImpactImgName() {
			return pointofimpactimgname;
		}
		public void setPointofImpactImgName(String name) {
			pointofimpactimgname = name;
		}
		
		
		
		//Accident Images
		// contain path file name with extension
		ArrayList<String> fileListaccDetails;
		public ArrayList<String> getAccDetailsImageList(){
			return fileListaccDetails;
		}
		public void setAccDetailsImageList(ArrayList<String> accFiles){
			fileListaccDetails = accFiles;
		}
		
		
			
		
		//Document Image Subsection
		// contain path file name with extension
		ArrayList<String> fileListDLStatement;
		public ArrayList<String> getDLStatementImageList(){
			return fileListDLStatement;
		}
		public void setDLStatementImageList(ArrayList<String> fileList){
			fileListDLStatement = fileList;
		}
		
		ArrayList<String> fileListTechnicalOfficerComments;
		public ArrayList<String> getTechnicalOfficerCommentsImageList(){
			return fileListTechnicalOfficerComments;
		}
		public void setTechnicalOfficerCommentsImageList(ArrayList<String> fileList){
			fileListTechnicalOfficerComments = fileList;
		}
		
		ArrayList<String> ClaimFormImage;
		public ArrayList<String> getClaimFormImageImageList(){
			return ClaimFormImage;
		}
		public void setClaimFormImageImageList(ArrayList<String> fileList){
			ClaimFormImage = fileList;
		}
		
		ArrayList<String> ARI;
		public ArrayList<String> getARIImageList(){
			return ARI;
		}
		public void setARIImageList(ArrayList<String> fileList){
			ARI = fileList;
		}
		
		ArrayList<String> DR;
		public ArrayList<String> getDRImageList(){
			return DR;
		}
		public void setDRImageList(ArrayList<String> fileList){
			DR = fileList;
		}
		
		ArrayList<String> SeenVisit;
		public ArrayList<String> getSeenVisitImageList(){
			return SeenVisit;
		}
		public void setSeenVisitImageList(ArrayList<String> fileList){
			SeenVisit = fileList;
		}
		
		ArrayList<String> SpecialReport1;
		public ArrayList<String> getSpecialReport1ImageList(){
			return SpecialReport1;
		}
		public void setSpecialReport1ImageList(ArrayList<String> fileList){
			SpecialReport1 = fileList;
		}
		
		ArrayList<String> SpecialReport2;
		public ArrayList<String> getSpecialReport2ImageList(){
			return SpecialReport2;
		}
		public void setSpecialReport2ImageList(ArrayList<String> fileList){
			SpecialReport2 = fileList;
		}
		
		ArrayList<String> SpecialReport3;
		public ArrayList<String> getSpecialReport3ImageList(){
			return SpecialReport3;
		}
		public void setSpecialReport3ImageList(ArrayList<String> fileList){
			SpecialReport3 = fileList;
		}
		
		ArrayList<String> Supplementary1;
		public ArrayList<String> getSupplementary1ImageList(){
			return Supplementary1;
		}
		public void setSupplementary1ImageList(ArrayList<String> fileList){
			Supplementary1 = fileList;
		}
		
		ArrayList<String> Supplementary2;
		public ArrayList<String> getSupplementary2ImageList(){
			return Supplementary2;
		}
		public void setSupplementary2ImageList(ArrayList<String> fileList){
			Supplementary2 = fileList;
		}
		
		ArrayList<String> Supplementary3;
		public ArrayList<String> getSupplementary3ImageList(){
			return Supplementary3;
		}
		public void setSupplementary3ImageList(ArrayList<String> fileList){
			Supplementary3 = fileList;
		}
		
		ArrayList<String> Supplementary4;
		public ArrayList<String> getSupplementary4ImageList(){
			return Supplementary4;
		}
		public void setSupplementary4ImageList(ArrayList<String> fileList){
			Supplementary4 = fileList;
		}
		
		ArrayList<String> Acknowledgment;
		public ArrayList<String> getAcknowledgmentImageList(){
			return Acknowledgment;
		}
		public void setAcknowledgmentImageList(ArrayList<String> fileList){
			Acknowledgment = fileList;
		}
		
		ArrayList<String> SalvageReport;
		public ArrayList<String> getSalvageReportImageList(){
			return SalvageReport;
		}
		public void setSalvageReportImageList(ArrayList<String> fileList){
			SalvageReport = fileList;
		}
}