-- ========================================
-- SMIG-SMG ReceiveResponseWEB MVP
-- Oracle Database Setup Script
-- ========================================

-- Connect as SYSTEM or privileged user
-- sqlplus system/password@localhost:1521/XEPDB1

-- Create user for the application
create user smg_app identified by smg_password;

-- Grant necessary privileges
grant connect to smg_app;
grant resource to smg_app;
grant
   unlimited tablespace
to smg_app;

-- Grant additional privileges for sequences and triggers
grant
   create sequence
to smg_app;
grant
   create trigger
to smg_app;

-- Grant session privileges
grant
   create session
to smg_app;

-- Grant table privileges (if needed for specific operations)
grant
   create table
to smg_app;
grant
   create view
to smg_app;
grant
   create procedure
to smg_app;

-- Verify user creation
select username
  from dba_users
 where username = 'SMG_APP';

-- Test connection (optional)
-- You can test the connection using:
-- sqlplus smg_app/smg_password@localhost:1521/XEPDB1

commit;

-- ========================================
-- Setup Complete
-- ========================================
-- Next steps:
-- 1. Set environment variables in your system
-- 2. Run the Spring Boot application
-- 3. Flyway will automatically create the required tables
-- ========================================