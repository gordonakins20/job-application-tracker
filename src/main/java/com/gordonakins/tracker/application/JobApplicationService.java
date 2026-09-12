package com.gordonakins.tracker.application;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class JobApplicationService {
  private final JobApplicationRepository repository;
  public JobApplicationService(JobApplicationRepository repository){this.repository=repository;}
  public List<JobApplication> findAll(ApplicationStatus status){return status==null?repository.findAllByOrderByAppliedDateDesc():repository.findByStatusOrderByAppliedDateDesc(status);}
  public JobApplication findById(Long id){return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Application not found: "+id));}
  public JobApplication create(JobApplication item){item.setId(null);return repository.save(item);}
  public JobApplication update(Long id,JobApplication changes){JobApplication item=findById(id);item.setCompany(changes.getCompany());item.setRole(changes.getRole());item.setStatus(changes.getStatus());item.setAppliedDate(changes.getAppliedDate());item.setNotes(changes.getNotes());return repository.save(item);}
  public void delete(Long id){repository.delete(findById(id));}
}
