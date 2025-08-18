@echo off
REM ========================================
REM SMIG-SMG ReceiveResponseWEB MVP
REM Environment Setup Script for Windows
REM ========================================

echo Setting up environment variables for SMIG-SMG ReceiveResponseWEB MVP...

REM Database Configuration
setx DATABASE_URL "jdbc:oracle:thin:@localhost:1521/XEPDB1"
setx DATABASE_USERNAME "smg_app"
setx DATABASE_PASSWORD "12345678"
setx DATABASE_SCHEMA "SMG_APP"

REM Spring Profile
setx SPRING_PROFILES_ACTIVE "dev"
1
REM Server Configuration
setx SERVER_PORT "8080"

echo Environment variables have been set successfully!
echo Please restart your command prompt or IDE to use the new environment variables.

echo.
echo Environment variables that have been set:
echo DATABASE_URL: jdbc:oracle:thin:@localhost:1521/XEPDB1
echo DATABASE_USERNAME: smg_app
echo DATABASE_PASSWORD: smg_password
echo DATABASE_SCHEMA: SMG_APP
echo SPRING_PROFILES_ACTIVE: dev
echo SERVER_PORT: 8080

echo.
echo NOTE: These variables will be available in NEW command prompt sessions.
echo Current session may not reflect the new values immediately.

echo.
echo Next steps:
echo 1. Restart your command prompt or IDE
echo 2. Make sure Oracle Database is running
echo 3. Run database setup script (database-setup.sql)
echo 4. Build and run the application: mvn spring-boot:run

pause
