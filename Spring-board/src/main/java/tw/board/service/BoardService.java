package tw.board.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.board.dao.BoardDAO;
import tw.board.domain.BoardBean;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardDAO boardDao;
	
	public BoardBean select(Integer id) {
		
		BoardBean bean= boardDao.select(id);
		
		return bean;
	}
	public List<BoardBean> select(Integer firstResult, Integer maxResult) {
		
		List<BoardBean> list= boardDao.select(firstResult, maxResult);
		
		return list;
	}
	public BoardBean insert(BoardBean bean) {
		return boardDao.insert(bean);
		
	}
//	id, title, release, end, user, content
	public BoardBean update(BoardBean bean) {
		BoardBean result = null;
		if(bean!=null && bean.getId()!=null) {
			result = boardDao.update(bean.getId(),bean.getTitle(), bean.getBegin(),
					bean.getEnd(), bean.getUser(),bean.getContent());
		}
		return result;
	}
	public boolean delete(BoardBean bean) {
		boolean result = false;
		if(bean!=null && bean.getId()!=null) {
			result = boardDao.delete(bean.getId());
		}
		return result;
	}
	
}
