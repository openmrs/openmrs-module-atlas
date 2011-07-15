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
package org.openmrs.module.atlas.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.atlas.db.StatisticsDAO;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 *
 */
public class HibernateStatisticsDAO implements StatisticsDAO {

	protected static final Log log = LogFactory.getLog(HibernateStatisticsDAO.class);
	
	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * Default public constructor
	 */
	public HibernateStatisticsDAO() { }
	
	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) { 
		this.sessionFactory = sessionFactory;
	}

	
	/**
     * @see org.openmrs.module.atlas.db.StatisticsDAO#getNumberOfPatients()
     */
    @Override
    public Long getNumberOfPatients() throws DAOException {
    	return (Long) sessionFactory.getCurrentSession().createQuery(
		"SELECT COUNT(*) FROM Patient where voided='false'").uniqueResult();
    }

	/**
     * @see org.openmrs.module.atlas.db.StatisticsDAO#getNumberOfVisits()
     */
    @Override
    public Long getNumberOfVisits() throws DAOException {
    	return (Long) sessionFactory.getCurrentSession().createQuery(
    		"SELECT COUNT(*) FROM Visit where voided='false'").uniqueResult();
    }
    
	/**
     * @see org.openmrs.module.atlas.db.StatisticsDAO#getNumberOfObservations()
     */
    @Override
    public Long getNumberOfObservations() throws DAOException {
    	return (Long) sessionFactory.getCurrentSession().createQuery(
		"SELECT COUNT(*) FROM Obs where voided='false'").uniqueResult();
    }
 



}
