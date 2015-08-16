package com.floreantpos.model.dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.floreantpos.PosException;
import com.floreantpos.model.AttendenceHistory;
import com.floreantpos.model.Shift;
import com.floreantpos.model.Terminal;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.Diner;
import com.floreantpos.util.DinerNotFoundException;

public class DinerDAO extends BaseDinerDAO {
	public final static DinerDAO instance = new DinerDAO();

	/**
	 * Default constructor. Can be used in place of getInstance()
	 */
	public DinerDAO() {
	}

	public List<Diner> findDrivers() {
		Session session = null;
		
		try {
			session = getSession();
			Criteria criteria = session.createCriteria(getReferenceClass());
			criteria.add(Restrictions.eq(Diner.PROP_DRIVER, Boolean.TRUE));
			
			return criteria.list();
		} finally {
			if (session != null) {
				closeSession(session);
			}
		}
	}
	
	public Diner findDiner(int id) {
		Session session = null;
		
		try {
			session = getSession();
			Criteria criteria = session.createCriteria(getReferenceClass());
			criteria.add(Restrictions.eq(Diner.PROP_USER_ID, id));
			
			Object result = criteria.uniqueResult();
			if(result != null) {
				return (Diner) result;
			}
			else {
				//TODO: externalize string
				throw new DinerNotFoundException("Diner with id " + id + " not found");
			}
		} finally {
			if (session != null) {
				closeSession(session);
			}
		}
	}
	
	public Diner findDinerBySecretKey(String secretKey) {
		Session session = null;
		
		try {
			
			session = getSession();
			Criteria criteria = session.createCriteria(getReferenceClass());
			criteria.add(Restrictions.eq(Diner.PROP_PASSWORD, secretKey));
			
			Object result = criteria.uniqueResult();
			return (Diner) result;
		} finally {
			if (session != null) {
				closeSession(session);
			}
		}
	}
	
	public boolean isDinerExist(int id) {
		try {
			Diner diner  = findDiner(id);
			
			return diner != null;
			
		} catch (DinerNotFoundException x) {
			return false;
		}
	}
	
	public Integer findDinerWithMaxId() {
		Session session = null;
		
		try {
			session = getSession();
			Criteria criteria = session.createCriteria(getReferenceClass());
			criteria.setProjection(Projections.max(Diner.PROP_USER_ID));

			List list = criteria.list();
			if(list != null && list.size() > 0) {
				return (Integer) list.get(0);
			}
			
			return null;
		} finally {
			if (session != null) {
				closeSession(session);
			}
		}
	}

	public List<Diner> getClockedInDiner(Terminal terminal) {
		Session session = null;

		try {
			session = getSession();
			Criteria criteria = session.createCriteria(getReferenceClass());
			criteria.add(Restrictions.eq(Diner.PROP_CLOCKED_IN, Boolean.TRUE));
			criteria.add(Restrictions.eq(Diner.PROP_CURRENT_TERMINAL, terminal));

			return criteria.list();
		} finally {
			if (session != null) {
				closeSession(session);
			}
		}

	}

	public void saveClockIn(Diner diner, AttendenceHistory attendenceHistory,
			Shift shift, Calendar currentTime) {
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(diner);
			session.saveOrUpdate(attendenceHistory);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();

			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception x) {
				}
			}
			throw new PosException("Unable to store clock in information", e);

		} finally {
			if (session != null) {
				closeSession(session);
			}
		}
	}

	public void saveClockOut(Diner diner, AttendenceHistory attendenceHistory,
			Shift shift, Calendar currentTime) {
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(diner);
			session.saveOrUpdate(attendenceHistory);

			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception x) {
				}
			}
			throw new PosException("Unable to store clock out information", e);

		} finally {
			if (session != null) {
				closeSession(session);
			}
		}
	}

	private boolean validate(Diner diner, boolean editMode) throws PosException {
		String hql = "from Diner u where u.dinerId=:dinerId and u.type=:dinerType";

		Session session = getSession();
		Query query = session.createQuery(hql);
                    query = query.setParameter("dinerId", diner.getId());
		query = query.setParameter("dinerType", diner.getType());
		
		if (query.list().size() > 0) {
			throw new PosException("Another diner with same ID already exists");
		}

		return true;
	}

	public void saveOrUpdate(Diner diner, boolean editMode) {
		Session session = null;

		try {
			if (!editMode) {
				validate(diner, editMode);
			}
			super.saveOrUpdate(diner);
		} catch (Exception x) {
			throw new PosException("Could not save diner", x);
		} finally {
			closeSession(session);
		}

	}

	// public Diner findByPassword(String password) throws PosException {
	// Session session = null;
	// Transaction tx = null;
	//		
	// String hql = "from Diner u where u.password=:password";
	//		
	// try {
	// session = getSession();
	// tx = session.beginTransaction();
	// Query query = session.createQuery(hql);
	// query = query.setParameter("password", password);
	// Diner diner = (Diner) query.uniqueResult();
	// tx.commit();
	// if(diner == null) {
	// throw new PosException("Diner not found");
	// }
	// return diner;
	// } catch(PosException x) {
	// throw x;
	// } catch (Exception e) {
	// try {
	// if(tx != null) {
	// tx.rollback();
	// }
	// }catch(Exception e2) {}
	// throw new PosException("Unnable to find diner", e);
	// } finally {
	// if(session != null) {
	// session.close();
	// }
	// }
	// }

	public int findNumberOfOpenTickets(Diner diner) throws PosException {
		Session session = null;
		Transaction tx = null;

		String hql = "select count(*) from Ticket ticket where ticket.owner=:owner and ticket."
				+ Ticket.PROP_CLOSED + "settled=false";
		int count = 0;
		try {
			session = getSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query = query.setEntity("owner", diner);
			Iterator iterator = query.iterate();
			if (iterator.hasNext()) {
				count = ((Integer) iterator.next()).intValue();
			}
			tx.commit();
			return count;
		} catch (Exception e) {
			try {
				if (tx != null) {
					tx.rollback();
				}
			} catch (Exception e2) {
			}
			throw new PosException("Unnable to find diner", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}