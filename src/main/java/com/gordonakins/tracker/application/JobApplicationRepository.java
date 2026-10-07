package com.gordonakins.tracker.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findAllByOrderByAppliedDateDesc();

    List<JobApplication> findByStatusOrderByAppliedDateDesc(ApplicationStatus status);

    List<JobApplication>
    findByCompanyContainingIgnoreCaseOrRoleContainingIgnoreCaseOrderByAppliedDateDesc(
            String company,
            String role
    );

    List<JobApplication>
    findByStatusAndCompanyContainingIgnoreCaseOrStatusAndRoleContainingIgnoreCaseOrderByAppliedDateDesc(
            ApplicationStatus status1,
            String company,
            ApplicationStatus status2,
            String role
    );
}