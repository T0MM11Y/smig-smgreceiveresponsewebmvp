@echo off
REM ========================================
REM SMIG-SMG ReceiveResponseWEB MVP
REM Build and Run Script for Windows
REM ========================================

echo SMIG-SMG ReceiveResponseWEB MVP - Build and Run

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java 17+ is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven 3.9+
    pause
    exit /b 1
)

echo.
echo Step 1: Cleaning previous build...
call mvn clean

if %errorlevel% neq 0 (
    echo Build failed during clean phase
    pause
    exit /b 1
)

echo.
echo Step 2: Running tests...
call mvn test

if %errorlevel% neq 0 (
    echo Tests failed
    pause
    exit /b 1
)

echo.
echo Step 3: Building application...
call mvn package -DskipTests

if %errorlevel% neq 0 (
    echo Build failed during package phase
    pause
    exit /b 1
)

echo.
echo Step 4: Starting application...
echo Application will start on http://localhost:8080/smg-api
echo Swagger UI will be available at http://localhost:8080/smg-api/swagger-ui.html
echo.
echo Press Ctrl+C to stop the application
echo.

call mvn spring-boot:run

pause
