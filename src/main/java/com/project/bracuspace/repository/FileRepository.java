package com.project.bracuspace.repository;


import com.project.bracuspace.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, Long> {

    Iterable<File> findFileByUserId(Long userId);

    void deleteByFileName(String fileName);

}
