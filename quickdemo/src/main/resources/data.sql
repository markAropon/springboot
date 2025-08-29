-- -- Patients
-- INSERT INTO patients (name, phone, insurance) VALUES
--  ('Juan Dela Cruz', '+63-917-123-4567', 'PhilHealth'),
--  ('Maria Santos', '+63-927-555-2345', 'PhilHealth'),
--  ('Jose Rizal', '+63-936-876-1122', 'PhilHealth'),
--  ('Emily Johnson', '+1-202-555-0198', 'Blue Cross'),
--  ('Kim Min-ji', '+82-10-2345-6789', 'NHIS Korea');

-- -- Doctors
-- INSERT INTO doctors (name, specialty) VALUES
--  ('Dr. Antonio Luna', 'Cardiology'),
--  ('Dr. Grace Lee', 'Pediatrics'),
--  ('Dr. James Smith', 'Orthopedics'),
--  ('Dr. Sofia Hernandez', 'Dermatology');

-- -- Admission purposes
-- INSERT INTO admission_purposes (purpose) VALUES
--  ('Routine Checkup'),
--  ('Emergency'),
--  ('Surgery'),
--  ('Maternity Care');

-- -- Admissions
-- INSERT INTO admissions (patient_id, doctor_id, purpose_id, admission_date, discharge_date, status, room_number) VALUES
--  (1, 1, 2, NOW() - INTERVAL '10 days', NOW() - INTERVAL '8 days', 'Discharged', '101A'),
--  (2, 2, 1, NOW() - INTERVAL '5 days', NULL, 'Admitted', '202B'),
--  (3, 3, 3, NOW() - INTERVAL '20 days', NOW() - INTERVAL '15 days', 'Discharged', '303C'),
--  (4, 4, 4, NOW() - INTERVAL '2 days', NULL, 'Admitted', '404D');

-- -- Medical history
-- INSERT INTO medical_history (patient_id, condition, icd10_code, diagnosed_date, status) VALUES
--  (1, 'Hypertension', 'I10', '2020-01-15', 'Ongoing'),
--  (2, 'Asthma', 'J45', '2019-03-10', 'Ongoing'),
--  (3, 'Diabetes Mellitus Type 2', 'E11', '2018-07-22', 'Ongoing');

-- -- Vital signs
-- INSERT INTO vital_signs (admission_id, recorded_date, temperature, blood_pressure, heart_rate, respiratory_rate, spo2, weight, height) VALUES
--  (1, NOW() - INTERVAL '9 days', 37.2, '130/85', 78, 18, 98.5, 70, 170),
--  (2, NOW() - INTERVAL '4 days', 38.5, '120/80', 90, 22, 96.0, 55, 160);

-- -- Diagnoses
-- INSERT INTO diagnoses (admission_id, icd10_code, diagnosis_type, notes, recorded_date) VALUES
--  (1, 'I10', 'Primary', 'High blood pressure observed', NOW() - INTERVAL '9 days'),
--  (2, 'J45', 'Primary', 'Asthma attack, prescribed inhaler', NOW() - INTERVAL '4 days');

-- -- Prescriptions
-- INSERT INTO prescriptions (admission_id, doctor_id, medication, dose, route, frequency, duration, start_date, end_date, instructions) VALUES
--  (1, 1, 'Losartan', '50mg', 'Oral', 'Once daily', '30 days', CURRENT_DATE - 10, CURRENT_DATE + 20, 'Take after meals'),
--  (2, 2, 'Salbutamol Inhaler', '2 puffs', 'Inhalation', 'As needed', '14 days', CURRENT_DATE - 5, CURRENT_DATE + 9, 'Use when shortness of breath occurs');

-- -- Treatments
-- INSERT INTO treatments (doctor_id, admission_id, treatment_date, description, cost) VALUES
--  (1, 1, NOW() - INTERVAL '9 days', 'Blood pressure monitoring and counseling', 1500),
--  (2, 2, NOW() - INTERVAL '4 days', 'Nebulization therapy', 1000);

-- -- Treatment plans
-- INSERT INTO treatment_plans (admission_id, doctor_id, title, goal, status, created_date) VALUES
--  (1, 1, 'Hypertension Management Plan', 'Lower BP below 130/80', 'Ongoing', NOW() - INTERVAL '9 days'),
--  (2, 2, 'Asthma Control Plan', 'Reduce frequency of asthma attacks', 'Ongoing', NOW() - INTERVAL '4 days');

-- -- Plan items
-- INSERT INTO plan_items (plan_id, name, type, scheduled_date, performed_date, status, cost) VALUES
--  (1, 'Daily BP Monitoring', 'Nursing Care', NOW() - INTERVAL '8 days', NULL, 'Scheduled', 500),
--  (2, 'Nebulization Session', 'Therapy', NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 'Completed', 800);

-- -- Treatment outcomes
-- INSERT INTO treatment_outcomes (treatment_id, outcome, notes, recorded_date) VALUES
--  (1, 'Improved', 'Blood pressure stabilized', NOW() - INTERVAL '7 days'),
--  (2, 'Stable', 'Patient responded well to treatment', NOW() - INTERVAL '2 days');

-- -- Lab orders
-- INSERT INTO lab_orders (admission_id, doctor_id, test_name, ordered_date, status) VALUES
--  (1, 1, 'CBC', NOW() - INTERVAL '9 days', 'Completed'),
--  (2, 2, 'Chest X-Ray', NOW() - INTERVAL '3 days', 'Pending');

-- -- Lab results
-- INSERT INTO lab_results (lab_order_id, result_name, result_value, unit, reference_range, abnormal_flag, result_date) VALUES
--  (1, 'WBC', '7.2', 'x10^9/L', '4.0-10.0', 'N', NOW() - INTERVAL '8 days');

-- -- Documents
-- INSERT INTO documents (admission_id, patient_id, source, title, file_url, mime_type, uploaded_date, notes) VALUES
--  (1, 1, 'Hospital', 'Discharge Summary', 'http://example.com/discharge1.pdf', 'application/pdf', NOW() - INTERVAL '8 days', 'Summary after hypertension management'),
--  (2, 2, 'Clinic', 'Asthma Report', 'http://example.com/asthma2.pdf', 'application/pdf', NOW() - INTERVAL '2 days', 'Report of asthma care');

-- -- Insurances
-- INSERT INTO insurances (payer_name, payer_code) VALUES
--  ('PhilHealth', 'PH01'),
--  ('Blue Cross', 'BC01'),
--  ('NHIS Korea', 'KR01');

-- -- Patient insurances
-- INSERT INTO patient_insurances (patient_id, insurance_id, policy_number, coverage_start, coverage_end, is_primary) VALUES
--  (1, 1, 'PH-123456', '2020-01-01', '2030-01-01', TRUE),
--  (4, 2, 'BC-987654', '2021-01-01', '2026-01-01', TRUE),
--  (5, 3, 'KR-555888', '2022-06-01', '2027-06-01', TRUE);

-- -- Bills
-- INSERT INTO bills (admission_id, total_amount, paid_amount, status) VALUES
--  (1, 3000, 3000, 'Paid'),
--  (2, 1800, 1000, 'Partially Paid');

-- -- Bill items
-- INSERT INTO bill_items (bill_id, category, description, amount, source_table, source_id) VALUES
--  (1, 'Consultation', 'Doctor consultation fee', 1500, 'treatments', 1),
--  (1, 'Lab', 'CBC Test', 1500, 'lab_orders', 1),
--  (2, 'Therapy', 'Nebulization', 800, 'treatments', 2),
--  (2, 'Lab', 'Chest X-Ray', 1000, 'lab_orders', 2);

-- -- Invoices
-- INSERT INTO invoices (admission_id, invoice_date, total_amount, status) VALUES
--  (1, NOW() - INTERVAL '8 days', 3000, 'Paid'),
--  (2, NOW() - INTERVAL '2 days', 1800, 'Pending');

-- -- Invoice items
-- INSERT INTO invoice_items (invoice_id, bill_item_id, amount) VALUES
--  (1, 1, 1500),
--  (1, 2, 1500),
--  (2, 3, 800),
--  (2, 4, 1000);

-- -- Payments
-- INSERT INTO payments (invoice_id, payer_type, insurance_id, amount, payment_date, method, reference) VALUES
--  (1, 'Insurance', 1, 3000, NOW() - INTERVAL '7 days', 'Bank Transfer', 'TXN123'),
--  (2, 'Patient', NULL, 1000, NOW() - INTERVAL '1 days', 'Cash', 'RCPT456');
