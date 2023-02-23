package tw.board.controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.board.domain.BoardBean;
import tw.board.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService boardservice;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{id}")
	 public BoardBean read(@PathVariable("id") Integer id) {
		return boardservice.select(id);
	 }
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/")
	 public List<BoardBean> getall(@RequestParam Map<String, String> params) {
		System.out.println(params.get("firstResult"));
		System.out.println(params.get("maxResult"));
		Integer firstResult=Integer.parseInt(params.get("firstResult"));
		Integer maxResult=Integer.parseInt(params.get("maxResult"));
		return boardservice.select(firstResult,maxResult);
	 }
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/")
	 public void create(@RequestParam Map<String, String> params) throws ParseException {		
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		BoardBean bean=new BoardBean();
		String title=params.get("title");
		Date begin=(Date) simpleDateFormat.parse(params.get("begin"));
		Date end =(Date)simpleDateFormat.parse(params.get("end"));
		String user = params.get("user");
		String content=params.get("content");
		bean.setTitle(title);
		bean.setBegin(begin);
		bean.setEnd(end);
		bean.setUser(user);
		bean.setContent(content);
		 boardservice.insert(bean);
	 }
	
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/")
	 public void update(@RequestParam Map<String, String> params)throws ParseException {		
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		BoardBean bean=new BoardBean();
		Integer id=Integer.parseInt(params.get("id"));
		String title=params.get("title");
		Date begin=(Date) simpleDateFormat.parse(params.get("begin"));
		Date end =(Date)simpleDateFormat.parse(params.get("end"));
		String user = params.get("user");
		String content=params.get("content");
		bean.setId(id);
		bean.setTitle(title);
		bean.setBegin(begin);
		bean.setEnd(end);
		bean.setUser(user);
		bean.setContent(content);
		boardservice.update(bean);
		
	 }
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/{id}")
	 public void delete(@PathVariable("id") Integer id) {
		BoardBean bean=new BoardBean();
		bean.setId(id);
		boardservice.delete(bean); 		 
	 }	
}
