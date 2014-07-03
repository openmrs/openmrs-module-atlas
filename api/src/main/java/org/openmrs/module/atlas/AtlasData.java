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

import java.net.URLEncoder;
import java.util.UUID;

/**
 * The AtlasData class is the form backing object for the atlas page. It contains all the data from
 * the atlas bubble and from the gutter.
 */
public class AtlasData {
	
	private UUID id;
	
	/* /Bubble data */
	private String numberOfPatients;
	
	private String numberOfObservations;
	
	private String numberOfEncounters;
	
	private Boolean moduleEnabled;
	
	private Boolean isDirty;
	
    private String serverUrl;
    
	public AtlasData() {

		this.id = null;
		this.numberOfObservations = "?";
		this.numberOfPatients = "?";
		this.numberOfEncounters = "?";
		this.moduleEnabled = false;
		this.isDirty = true;
        this.serverUrl = "";
	}
	
	/**
	 * @param id The implementation's ID
	 */
	public AtlasData(UUID id) {
		this.id = id;
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
	 * @return the numberOfObservations
	 */
	public String getNumberOfObservations() {
		return numberOfObservations;
	}
	
	/**
	 * @param numberOfObservations the numberOfObservations to set
	 */
	public void setNumberOfObservations(String numberOfObservations) {
		this.numberOfObservations = numberOfObservations;
	}
	
	/**
	 * @return the numberOfPatients
	 */
	public String getNumberOfPatients() {
		return numberOfPatients;
	}
	
	/**
	 * @param numberOfPatients the numberOfPatients to set
	 */
	public void setNumberOfPatients(String numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}
	
	/**
	 * @return the numberOfVisits
	 */
	public String getNumberOfEncounters() {
		return numberOfEncounters;
	}
	
	/**
	 * @param numberOfEncounters the numberOfEncounters to set
	 */
	public void setNumberOfEncounters(String numberOfEncounters) {
		this.numberOfEncounters = numberOfEncounters;
	}
	
	/**
	 * @return the moduleEnabled
	 */
	public Boolean getModuleEnabled() {
		return moduleEnabled;
	}
	
	/**
	 * @param moduleEnabled the moduleEnabled to set
	 */
	public void setModuleEnabled(Boolean moduleEnabled) {
		this.moduleEnabled = moduleEnabled;
	}
	
	/**
	 * @return the isDirty
	 */
	public Boolean getIsDirty() {
		return isDirty;
	}
	
	/**
	 * @param isDirty the isDirty to set
	 */
	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}
    
    /**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}
    
    /**
	 * @return the serverUrlEncoded
	 */
	public String getServerUrlEncoded() {
        String patients = (numberOfPatients.equals("?")) ? "" : numberOfPatients;
        String encounters = (numberOfEncounters.equals("?")) ? "" : numberOfEncounters;
        String observations = (numberOfObservations.equals("?")) ? "" : numberOfObservations;
        
		return serverUrl+"?uuid="+id+"&patients=" + patients + "&encounters="
            + encounters+"&observations=" + observations;
	}
		/**
	 * @param serverUrl the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	/**
	 * @see java.lang.Object#toString()
     * @return String
	 */
	@Override
	public String toString() {
		String text = "";
		text = "Atlast Data" + "\nID: " + id + "||" + "\nNr of patients: " + numberOfPatients + "||"
		        + "\nNr of observations: " + numberOfObservations + "||" + "\nNr of visits: " + numberOfEncounters + "||"
		        + "\nUsage disclamer accepted: " + "||" + "\nModule is enabled: " + moduleEnabled + "\nServer URL:"
                + serverUrl + "||";
		
		return text;
	}
    
}
