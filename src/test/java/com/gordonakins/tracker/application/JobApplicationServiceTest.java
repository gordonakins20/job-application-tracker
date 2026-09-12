package com.gordonakins.tracker.application;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class JobApplicationServiceTest {
  private final JobApplicationRepository repository=mock(JobApplicationRepository.class);
  private final JobApplicationService service=new JobApplicationService(repository);
  @Test void updateCopiesFieldsAndKeepsId(){JobApplication old=item("Old Co","Developer",ApplicationStatus.SAVED);old.setId(7L);JobApplication changes=item("New Co","Java Developer",ApplicationStatus.INTERVIEW);when(repository.findById(7L)).thenReturn(Optional.of(old));when(repository.save(old)).thenReturn(old);JobApplication result=service.update(7L,changes);assertEquals(7L,result.getId());assertEquals("New Co",result.getCompany());assertEquals(ApplicationStatus.INTERVIEW,result.getStatus());verify(repository).save(old);}
  @Test void missingIdThrows(){when(repository.findById(99L)).thenReturn(Optional.empty());assertThrows(ResourceNotFoundException.class,()->service.findById(99L));}
  private JobApplication item(String company,String role,ApplicationStatus status){JobApplication x=new JobApplication();x.setCompany(company);x.setRole(role);x.setStatus(status);x.setAppliedDate(LocalDate.of(2026,8,20));x.setNotes("Test notes");return x;}
}
