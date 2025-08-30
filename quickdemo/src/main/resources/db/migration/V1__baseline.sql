-- Drop tables in reverse order of dependencies
DROP TABLE IF EXISTS invoice_items CASCADE;
DROP TABLE IF EXISTS invoices CASCADE;
DROP TABLE IF EXISTS bill_items CASCADE;
DROP TABLE IF EXISTS bills CASCADE;
DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS patient_insurances CASCADE;
DROP TABLE IF EXISTS insurances CASCADE;
DROP TABLE IF EXISTS documents CASCADE;
DROP TABLE IF EXISTS lab_results CASCADE;
DROP TABLE IF EXISTS lab_orders CASCADE;
DROP TABLE IF EXISTS treatment_outcomes CASCADE;
DROP TABLE IF EXISTS plan_items CASCADE;
DROP TABLE IF EXISTS treatment_plans CASCADE;
DROP TABLE IF EXISTS treatments CASCADE;
DROP TABLE IF EXISTS prescriptions CASCADE;
DROP TABLE IF EXISTS diagnoses CASCADE;
DROP TABLE IF EXISTS vital_signs CASCADE;
DROP TABLE IF EXISTS medical_history CASCADE;
DROP TABLE IF EXISTS admissions CASCADE;
DROP TABLE IF EXISTS admission_purposes CASCADE;
DROP TABLE IF EXISTS doctors CASCADE;
DROP TABLE IF EXISTS patients CASCADE;

-- Patients
CREATE TABLE patients (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    insurance VARCHAR(255)
);

-- Doctors
CREATE TABLE doctors (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR
);

-- Admission purposes
CREATE TABLE admission_purposes (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    purpose VARCHAR(255) NOT NULL
);

-- Admissions
CREATE TABLE admissions (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_id INT NOT NULL REFERENCES patients(id),
    doctor_id INT NOT NULL REFERENCES doctors(id),
    purpose_id INT NOT NULL REFERENCES admission_purposes(id),
    admission_date TIMESTAMP NOT NULL,
    discharge_date TIMESTAMP NULL,
    status VARCHAR,
    room_number VARCHAR
);

-- Medical history
CREATE TABLE medical_history (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_id INT NOT NULL REFERENCES patients(id),
    condition VARCHAR NOT NULL,
    icd10_code VARCHAR,
    diagnosed_date DATE,
    status VARCHAR
);

-- Vital signs
CREATE TABLE vital_signs (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    recorded_date TIMESTAMP NOT NULL,
    temperature DECIMAL,
    blood_pressure VARCHAR,
    heart_rate INT,
    respiratory_rate INT,
    spo2 DECIMAL,
    weight DECIMAL,
    height DECIMAL
);

-- Diagnoses
CREATE TABLE diagnoses (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    icd10_code VARCHAR NOT NULL,
    diagnosis_type VARCHAR NOT NULL,
    notes TEXT,
    recorded_date TIMESTAMP
);

-- Prescriptions
CREATE TABLE prescriptions (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    doctor_id INT NOT NULL REFERENCES doctors(id),
    medication VARCHAR NOT NULL,
    dose VARCHAR NOT NULL,
    route VARCHAR NOT NULL,
    frequency VARCHAR NOT NULL,
    duration VARCHAR,
    start_date DATE,
    end_date DATE,
    instructions TEXT
);

-- Treatments
CREATE TABLE treatments (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    doctor_id INT NOT NULL REFERENCES doctors(id),
    admission_id INT NOT NULL REFERENCES admissions(id),
    treatment_date TIMESTAMP NOT NULL,
    description VARCHAR NOT NULL,
    cost DECIMAL
);

-- Treatment plans
CREATE TABLE treatment_plans (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    doctor_id INT NOT NULL REFERENCES doctors(id),
    title VARCHAR NOT NULL,
    goal TEXT,
    status VARCHAR,
    created_date TIMESTAMP
);

-- Plan items
CREATE TABLE plan_items (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    plan_id INT NOT NULL REFERENCES treatment_plans(id),
    name VARCHAR NOT NULL,
    type VARCHAR,
    scheduled_date TIMESTAMP,
    performed_date TIMESTAMP,
    status VARCHAR,
    cost DECIMAL
);

-- Treatment outcomes
CREATE TABLE treatment_outcomes (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    treatment_id INT NOT NULL REFERENCES treatments(id),
    outcome VARCHAR NOT NULL,
    notes TEXT,
    recorded_date TIMESTAMP
);

-- Lab orders
CREATE TABLE lab_orders (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    doctor_id INT NOT NULL REFERENCES doctors(id),
    test_name VARCHAR NOT NULL,
    ordered_date TIMESTAMP NOT NULL,
    status VARCHAR
);

-- Lab results
CREATE TABLE lab_results (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lab_order_id INT NOT NULL REFERENCES lab_orders(id),
    result_name VARCHAR NOT NULL,
    result_value VARCHAR,
    unit VARCHAR,
    reference_range VARCHAR,
    abnormal_flag VARCHAR,
    result_date TIMESTAMP
);

-- Documents
CREATE TABLE documents (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    patient_id INT NOT NULL REFERENCES patients(id),
    source VARCHAR,
    title VARCHAR,
    file_url VARCHAR,
    mime_type VARCHAR,
    uploaded_date TIMESTAMP,
    notes TEXT
);

-- Insurances
CREATE TABLE insurances (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    payer_name VARCHAR NOT NULL,
    payer_code VARCHAR
);

-- Patient insurances
CREATE TABLE patient_insurances (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_id INT NOT NULL REFERENCES patients(id),
    insurance_id INT NOT NULL REFERENCES insurances(id),
    policy_number VARCHAR NOT NULL,
    coverage_start DATE,
    coverage_end DATE,
    is_primary BOOLEAN DEFAULT TRUE
);

-- Bills
CREATE TABLE bills (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    total_amount DECIMAL NOT NULL,
    paid_amount DECIMAL DEFAULT 0,
    status VARCHAR
);

-- Bill items
CREATE TABLE bill_items (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bill_id INT NOT NULL REFERENCES bills(id),
    category VARCHAR NOT NULL,
    description VARCHAR,
    amount DECIMAL NOT NULL,
    source_table VARCHAR,
    source_id INT
);

-- Invoices
CREATE TABLE invoices (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admission_id INT NOT NULL REFERENCES admissions(id),
    invoice_date TIMESTAMP NOT NULL,
    total_amount DECIMAL NOT NULL,
    status VARCHAR
);

-- Invoice items
CREATE TABLE invoice_items (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    invoice_id INT NOT NULL REFERENCES invoices(id),
    bill_item_id INT NOT NULL REFERENCES bill_items(id),
    amount DECIMAL NOT NULL
);
-- Payments
CREATE TABLE payments (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    invoice_id INT NOT NULL REFERENCES invoices(id),
    payer_type VARCHAR NOT NULL,
    insurance_id INT REFERENCES insurances(id),
    amount DECIMAL NOT NULL,
    payment_date TIMESTAMP NOT NULL,
    method VARCHAR,
    reference VARCHAR
);