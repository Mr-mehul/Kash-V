package com.kashv.repos;

import com.kashv.domain.FileData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileDataRepository extends JpaRepository<FileData, Long> {
}
