package com.example.prcc2.repository;

import com.example.prcc2.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
@NoRepositoryBean
public interface PostRepository extends JpaRepository<Post, Long> {

}

