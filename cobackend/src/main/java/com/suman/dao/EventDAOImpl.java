package com.suman.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.suman.model.Event;

@Repository(value = "EventDAO")
public class EventDAOImpl implements EventDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public EventDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean save(Event event) {
		try {
			sessionFactory.getCurrentSession().save(event);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(Event event) {
		try {
			sessionFactory.getCurrentSession().update(event);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Event event) {
		try {
			sessionFactory.getCurrentSession().delete(event);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Event get(int eventID) {
		String hql = "from Event where event_id=" + eventID ;

		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Event> list = (List<Event>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("event retrieved from DAOImpl");
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public List<Event> list() {
		String hql = " from Event";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

}
