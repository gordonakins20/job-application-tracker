package com.gordonakins.tracker.application;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity @Table(name="job_applications")
public class JobApplication {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank(message="Company is required") @Size(max=100) private String company;
  @NotBlank(message="Role is required") @Size(max=100) private String role;
  @NotNull(message="Status is required") @Enumerated(EnumType.STRING) private ApplicationStatus status;
  @NotNull(message="Application date is required") private LocalDate appliedDate;
  @Size(max=500) private String notes;
  public JobApplication() {}
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getCompany(){return company;} public void setCompany(String v){company=v;}
  public String getRole(){return role;} public void setRole(String v){role=v;}
  public ApplicationStatus getStatus(){return status;} public void setStatus(ApplicationStatus v){status=v;}
  public LocalDate getAppliedDate(){return appliedDate;} public void setAppliedDate(LocalDate v){appliedDate=v;}
  public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
}
