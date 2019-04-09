package com.project.jw.repository;

import com.project.jw.model.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosRepository extends JpaRepository<Videos, Long> {
    List<Videos> findByStatus(Boolean status);

}
