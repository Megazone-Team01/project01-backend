package com.mzcteam01.mzcproject01be.domains.file.repository;

import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
