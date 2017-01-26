package com.suman.restController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.suman.dao.BlogDAO;
import com.suman.model.Blog;
import com.suman.model.BlogComment;
import com.suman.model.User;

@RestController
public class BlogController {
	@Autowired
	BlogDAO blogDAO;
	
	@Autowired
	Blog blog;
	
	@Autowired
	User user;
	
	
	/* LIST BLOGS*****///*/
	
	@RequestMapping(value="/blogs",method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> listAllBlogs(){
		System.out.println("calling method listAllBlogs");
		List<Blog> blog=blogDAO.viewBlogs(); //list blogs
		if(blog.isEmpty()){
			return new ResponseEntity<List<Blog>>(HttpStatus.OK);
		}
		return new ResponseEntity<List<Blog>>(blog,HttpStatus.OK);
	}
	
	
	
	
	/* POST BLOGS*****///*/

	@RequestMapping(value="/blog/",method=RequestMethod.POST)
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog,HttpSession httpSession){
	
		blog.setCreated_date(new Date(System.currentTimeMillis()));

		
		//user= (User) httpSession.getAttribute("loggedInUserID");
		
		String loggeddInUserId = (String) httpSession.getAttribute("loggedInUserID");
		blog.setUser_id(loggeddInUserId);     //user name ---id
		blog.setStatus('P');  //published
		
		if(blogDAO.save(blog)==true){
			blog.setErrorCode("200");
			blog.setErrorMessage("blog posted..successfully");			
		}else{
		System.out.println("blog cannot be posted");
		blog.setErrorCode("400");
		blog.setErrorMessage("blog cannot be posted");
		return new ResponseEntity<Blog>(blog,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
			}
 	
	
	/* UPDATE BLOGS*****///*/
	
	
	@RequestMapping(value="/blog/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Blog> updateBlog(@PathVariable("id") String blog_id,@RequestBody Blog blog){
		System.out.println("calling method updateBlog" + blog.getBlog_id());
		if(blogDAO.get(blog_id)==null){
			System.out.println("blog does not exists with id:" + blog.getBlog_id());		
			blog=new Blog();
			blog.setErrorMessage("blog does not exists with id:" + blog.getBlog_id());
			return new ResponseEntity<Blog> (blog,HttpStatus.NOT_FOUND);
		}
		blogDAO.update(blog);
		System.out.println("blog updated successfully");
		return new ResponseEntity<Blog> (blog,HttpStatus.OK);		
	}

	
	
	/* DELETE BLOGS*****///*/
	
	
	@RequestMapping(value="/blog/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Blog> deleteBlog(@PathVariable("id") String blog_id){
		System.out.println("calling method deleteBlog for blog id: " + blog_id);
		Blog blog=blogDAO.get(blog_id);
		if(blog==null){
			System.out.println("blog does not exists with id:" + blog_id);
			blog=new Blog();
			blog.setErrorMessage("blog does not exists with id:" + blog_id);
			return new ResponseEntity<Blog> (blog,HttpStatus.NOT_FOUND);	
		}
		blogDAO.delete(blog);
		System.out.println("blog deleted successfully");
		return new ResponseEntity<Blog> (blog,HttpStatus.OK);		
	}
	
	/* GET BLOGS*****///*/
	
	@RequestMapping(value="/blog/{id}",method=RequestMethod.GET)
	public ResponseEntity<Blog> getBlog(@PathVariable("id") String blog_id){
		System.out.println("calling method getBlog for blog id: " + blog_id);
		Blog blog=blogDAO.get(blog_id);
		if(blog==null){
			System.out.println("blog does not exists with id:" + blog_id);
			blog=new Blog();
			blog.setErrorMessage("blog does not exists with id:" + blog_id);
			return new ResponseEntity<Blog> (blog,HttpStatus.NOT_FOUND);
		}
		System.out.println("blog exists with id:" + blog_id);
		return new ResponseEntity<Blog> (blog,HttpStatus.OK);
	}

	/* Comment save BLOGS*****///*/
	@RequestMapping(value="/blogcomment/{id}",method=RequestMethod.POST)
	public ResponseEntity<BlogComment> createBlogComment(@PathVariable("id") String blog_id,@RequestBody BlogComment blogcomment,HttpSession httpSession){
		System.out.println("calling method createBlogComment" + blogcomment.getBlog_id());
		String looggedInUserID=(String) httpSession.getAttribute("loggedInUserID");
		
		blogcomment.setUser_id(looggedInUserID);
		Date dt=new java.util.Date();
		blogcomment.setBcomment_date(dt.toString());
		blogcomment.setUser_id(looggedInUserID);
		blogcomment.setBlog_id(blog_id);
	//	blogcomment.setBlog_comment_data(blog_comment_data);
		if(blogDAO.addComment(blogcomment)==true){
			blogcomment.setErrorCode("200");
			blogcomment.setErrorMessage("Comment saved");
		
		}else{
			return new ResponseEntity<BlogComment>(blogcomment,HttpStatus.BAD_REQUEST);
		}
				
		return new ResponseEntity<BlogComment>(blogcomment,HttpStatus.OK);
			}

	
	/* list comments BLOGS*****///*/
	@RequestMapping(value="/blogscommentlistperblog/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> listAllBlogsCommentsPerBlog(@PathVariable("id") String blog_id){
		System.out.println("calling method listAllBlogs");
		List<BlogComment> blogcomment=blogDAO.viewMyBlogs(blog_id);
		if(blogcomment.isEmpty()){
			return new ResponseEntity<List<BlogComment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BlogComment>>(blogcomment,HttpStatus.OK);
	}


	@RequestMapping(value="/blogscommentlist/",method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> listAllBlogsComments(){
		System.out.println("calling method listAllBlogs");
		List<BlogComment> blogcomment=blogDAO.viewComments();
		if(blogcomment.isEmpty()){
			return new ResponseEntity<List<BlogComment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BlogComment>>(blogcomment,HttpStatus.OK);
	}

}






