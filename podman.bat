@echo off
setlocal enabledelayedexpansion

if "%1"=="" goto :show_help
if "%1"=="help" goto :show_help
if "%1"=="build" goto :build
if "%1"=="start" goto :start
if "%1"=="stop" goto :stop
if "%1"=="down" goto :down
if "%1"=="logs" goto :logs
if "%1"=="status" goto :status

echo Comando desconocido: %1
goto :show_help

:show_help
echo Logistica Sistema - Podman Helper
echo.
echo Uso: podman.bat [comando]
echo.
echo   build    Construir todas las imagenes
echo   start    Construir e iniciar todos los servicios
echo   stop     Detener todos los servicios
echo   down     Detener y eliminar contenedores
echo   logs     Ver logs de todos los servicios
echo   status   Ver estado de los contenedores
goto :eof

:build
echo Construyendo imagenes...
podman build -f tarifa-service/Dockerfile -t tarifa-service:latest .
if %errorlevel% neq 0 ( echo ERROR: fallo build tarifa-service & exit /b 1 )
podman build -f envio-service/Dockerfile -t envio-service:latest .
if %errorlevel% neq 0 ( echo ERROR: fallo build envio-service & exit /b 1 )
podman build -f notificacion-service/Dockerfile -t notificacion-service:latest .
if %errorlevel% neq 0 ( echo ERROR: fallo build notificacion-service & exit /b 1 )
echo Imagenes construidas OK.
goto :eof

:start
call :build
if %errorlevel% neq 0 exit /b 1
echo Iniciando servicios...
podman-compose up -d --no-build
echo.
echo Servicios activos:
echo   Kafka:                localhost:3001
echo   Kafka UI:             http://localhost:3002
echo   tarifa-service:       http://localhost:3003
echo   envio-service:        http://localhost:3004
echo   notificacion-service: http://localhost:3005
goto :eof

:stop
podman-compose stop
goto :eof

:down
podman-compose down
goto :eof

:logs
podman-compose logs -f
goto :eof

:status
podman-compose ps
goto :eof
