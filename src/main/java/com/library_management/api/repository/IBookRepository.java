package com.library_management.api.repository;

import com.library_management.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book> {
}
