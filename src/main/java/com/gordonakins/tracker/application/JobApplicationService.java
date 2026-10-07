package com.gordonakins.tracker.application;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

  private final JobApplicationRepository repository;

  public JobApplicationService(JobApplicationRepository repository) {
    this.repository = repository;
  }

  public List<JobApplication> findAll(
          ApplicationStatus status,
          String search
  ) {

    boolean hasSearch = search != null && !search.isBlank();

    // No status and no search
    if (status == null && !hasSearch) {
      return repository.findAllByOrderByAppliedDateDesc();
    }

    // Status only
    if (status != null && !hasSearch) {
      return repository.findByStatusOrderByAppliedDateDesc(status);
    }

    String searchText = search.trim();

    // Search only
    if (status == null) {
      return repository
              .findByCompanyContainingIgnoreCaseOrRoleContainingIgnoreCaseOrderByAppliedDateDesc(
                      searchText,
                      searchText
              );
    }

    // Status + search
    return repository
            .findByStatusAndCompanyContainingIgnoreCaseOrStatusAndRoleContainingIgnoreCaseOrderByAppliedDateDesc(
                    status,
                    searchText,
                    status,
                    searchText
            );
  }

  public JobApplication findById(Long id) {
    return repository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Application not found: " + id
                    )
            );
  }

  public JobApplication create(JobApplication item) {
    item.setId(null);
    return repository.save(item);
  }

  public JobApplication update(Long id, JobApplication changes) {
    JobApplication item = findById(id);

    item.setCompany(changes.getCompany());
    item.setRole(changes.getRole());
    item.setStatus(changes.getStatus());
    item.setAppliedDate(changes.getAppliedDate());
    item.setNotes(changes.getNotes());

    return repository.save(item);
  }

  public void delete(Long id) {
    repository.delete(findById(id));
  }
}