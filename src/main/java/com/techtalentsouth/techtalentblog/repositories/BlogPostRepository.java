package com.techtalentsouth.techtalentblog.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techtalentsouth.techtalentblog.blogpost.BlogPost;

@Repository
public interface BlogPostRepository extends CrudRepository<BlogPost,Long>{
	@Override
	List<BlogPost> findAll(); //can override the type that we get back when we call findall
}
