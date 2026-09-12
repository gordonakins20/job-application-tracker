package com.gordonakins.tracker.application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
  List<JobApplication> findAllByOrderByAppliedDateDesc();
  List<JobApplication> findByStatusOrderByAppliedDateDesc(ApplicationStatus status);
}
