package tw.board.dao;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import tw.board.domain.BoardBean;

@Repository
public class BoardHibernate implements BoardDAO {
	@PersistenceContext
	private Session session;
	
	public Session getSession() {
		return session;
	}
	@Override
	public BoardBean select(Integer id) {
		if(id!=null) {
			return getSession().get(BoardBean.class, id);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BoardBean> select(Integer firstResult, Integer maxResult) {
		Query query = getSession().createQuery(
				"from BoardBean", BoardBean.class).setFirstResult(firstResult).setMaxResults(maxResult);		
		return query.getResultList();
		
//		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
//		CriteriaQuery<BoardBean> criteriaQuery =
//				criteriaBuilder.createQuery(BoardBean.class);
//		Root<BoardBean> root = criteriaQuery.from(BoardBean.class);
//		TypedQuery<BoardBean> typedQuery = getSession().createQuery(criteriaQuery);
//		return typedQuery.getResultList();
	}
	@Override
	public BoardBean insert(BoardBean bean) {
				getSession().save(bean);
				return bean;
	}
	
	public BoardBean update(BoardBean bean) {
		if(bean!=null && bean.getId()!=null) {
			BoardBean temp = getSession().get(BoardBean.class, bean.getId());
			if(temp!=null) {
				return (BoardBean) getSession().merge(bean);
			}
		}
		return null;
	}
	
	@Override
	public BoardBean update(Integer id ,String title, Date begin, Date end,
			String user, String content) {
		if(id!=null) {
			BoardBean temp = getSession().get(BoardBean.class, id);
			if(temp!=null) {
				temp.setTitle(title);
				temp.setBegin(begin);;
				temp.setEnd(end);
				temp.setUser(user);
				temp.setContent(content);
				return temp;
			}
		}
		return null;
	}
	@Override
	public boolean delete(Integer id) {
		if(id!=null) {
			BoardBean temp = getSession().get(BoardBean.class, id);
			if(temp!=null) {
				getSession().delete(temp);
				return true;
			}
		}
		return false;
	}
}
