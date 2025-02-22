package com.efedemoapp.SecurityApp.users.repository;

import com.efedemoapp.SecurityApp.users.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {


    List<Books> findByUserId(Long userId);
}
