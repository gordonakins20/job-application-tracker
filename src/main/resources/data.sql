INSERT INTO job_applications (company,role,status,applied_date,notes)
SELECT 'Great Lakes Software','Junior Java Developer','APPLIED',DATE '2026-08-20','Submitted through company website'
WHERE NOT EXISTS (SELECT 1 FROM job_applications WHERE company='Great Lakes Software');
INSERT INTO job_applications (company,role,status,applied_date,notes)
SELECT 'Motor City Digital','Web Developer','INTERVIEW',DATE '2026-08-18','Phone interview scheduled'
WHERE NOT EXISTS (SELECT 1 FROM job_applications WHERE company='Motor City Digital');
