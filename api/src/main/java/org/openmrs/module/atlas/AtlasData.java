/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.atlas;

import java.util.UUID;


/**
 *
 */
public class AtlasData {
	 private UUID id;
	 private String name;
	 private Double latitude;
	 private Double longitude;
	 private String website;
	 //contact details
	 private String contactPhoneNumber;
	 private String contactEmailAddress;
	 //statistics
	 private Boolean includeNumberOfPatients;
	 private Boolean includeNumberOfObservations;
	 private Boolean includeNumberOfVisits;
	 private Long numberOfPatients;
	 private Long numberOfObservations;
	 private Long numberOfVisits;


	 
    /**
     * 
     */
    public AtlasData() {
    	this.contactEmailAddress = "";
  	    this.contactPhoneNumber = "";
  	    this.id = null;
  	    this.name = "";
  	    this.includeNumberOfObservations = false;
  	    this.includeNumberOfPatients = false;
  	    this.includeNumberOfVisits = false;
  	    this.latitude = 0.0;
  	    this.longitude = 0.0;
  	    this.website ="";
    }
    /**
     * 
     */
    public AtlasData(UUID id, String name, String website, Double latitude, Double longitude, 
                     String contactPhoneNumber, String contactEmailAddress, 
                     Boolean includeNumberOfPatients, Boolean includeNumberOfObservations, Boolean includeNumberOfVisits) {
	    this.contactEmailAddress = contactEmailAddress;
	    this.contactPhoneNumber = contactPhoneNumber;
	    this.id = id;
	    this.name = name;
	    this.includeNumberOfObservations = includeNumberOfObservations;
	    this.includeNumberOfPatients = includeNumberOfPatients;
	    this.includeNumberOfVisits = includeNumberOfVisits;
	    this.latitude = latitude;
	    this.longitude = longitude;
	    this.website = website;
    }
    
    
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}
	 
    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
	    this.id = id;
    }
    
    /**
     * @return the contactEmailAddress
     */
    public String getContactEmailAddress() {
	    return contactEmailAddress;
    }
    
    /**
     * @param contactEmailAddress the contactEmailAddress to set
     */
    public void setContactEmailAddress(String contactEmailAddress) {
	    this.contactEmailAddress = contactEmailAddress;
    }
    
    /**
     * @return the contactPhoneNumber
     */
    public String getContactPhoneNumber() {
	    return contactPhoneNumber;
    }
    
    /**
     * @param contactPhoneNumber the contactPhoneNumber to set
     */
    public void setContactPhoneNumber(String contactPhoneNumber) {
	    this.contactPhoneNumber = contactPhoneNumber;
    }
    
    /**
     * @return the includeNumberOfObservations
     */
    public Boolean getIncludeNumberOfObservations() {
	    return includeNumberOfObservations;
    }
    
    /**
     * @param includeNumberOfObservations the includeNumberOfObservations to set
     */
    public void setIncludeNumberOfObservations(Boolean includeNumberOfObservations) {
	    this.includeNumberOfObservations = includeNumberOfObservations;
    }
    
    /**
     * @return the includeNumberOfPatients
     */
    public Boolean getIncludeNumberOfPatients() {
	    return includeNumberOfPatients;
    }
    
    /**
     * @param includeNumberOfPatients the includeNumberOfPatients to set
     */
    public void setIncludeNumberOfPatients(Boolean includeNumberOfPatients) {
	    this.includeNumberOfPatients = includeNumberOfPatients;
    }
    
    /**
     * @return the includeNumberOfVisits
     */
    public Boolean getIncludeNumberOfVisits() {
	    return includeNumberOfVisits;
    }
    
    /**
     * @param includeNumberOfVisits the includeNumberOfVisits to set
     */
    public void setIncludeNumberOfVisits(Boolean includeNumberOfVisits) {
	    this.includeNumberOfVisits = includeNumberOfVisits;
    }
    
    /**
     * @return the latitude
     */
    public Double getLatitude() {
	    return latitude;
    }
    
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
	    this.latitude = latitude;
    }
    /**
     * @return the longitude
     */
    public Double getLongitude() {
	    return longitude;
    }
    
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
	    this.longitude = longitude;
    }
    
    /**
     * @return the name
     */
    public String getName() {
	    return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
	    this.name = name;
    }
      
    /**
     * @return the website
     */
    public String getWebsite() {
	    return website;
    }
    
    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
	    this.website = website;
    } 
    
    
    /**
     * @return the numberOfObservations
     */
    public Long getNumberOfObservations() {
	    return numberOfObservations;
    }
    
    
    /**
     * @param numberOfObservations the numberOfObservations to set
     */
    public void setNumberOfObservations(Long numberOfObservations) {
	    this.numberOfObservations = numberOfObservations;
    }
    
    
    /**
     * @return the numberOfPatients
     */
    public Long getNumberOfPatients() {
	    return numberOfPatients;
    }
    
    
    /**
     * @param numberOfPatients the numberOfPatients to set
     */
    public void setNumberOfPatients(Long numberOfPatients) {
	    this.numberOfPatients = numberOfPatients;
    }
    
    
    /**
     * @return the numberOfVisits
     */
    public Long getNumberOfVisits() {
	    return numberOfVisits;
    }
    
    
    /**
     * @param numberOfVisits the numberOfVisits to set
     */
    public void setNumberOfVisits(Long numberOfVisits) {
	    this.numberOfVisits = numberOfVisits;
    }
    
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	String text = "";
    	text = "Atlast Data"
    		   +"\nID: "+id
    		   +"\nName: "+name
    		   +"\nWebsite: "+website
    		   +"\nLatitude: "+latitude
    		   +"\nLongitude: "+longitude
    		   +"\nContact Phone Number: "+contactPhoneNumber
    		   +"\nContact Email: "+contactEmailAddress
    		   +"\nInclude nr of patients: "+includeNumberOfPatients.toString()
    		   +"\nInclude nr of observations: "+includeNumberOfObservations.toString()
    		   +"\nInclude nr of visits: "+includeNumberOfVisits.toString();
    	return text;
    }

}
