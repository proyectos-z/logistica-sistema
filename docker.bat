@echo off
REM Logistica Sistema - Docker Helper Script for Windows

setlocal enabledelayedexpansion

if "%1"=="" goto :show_help
if "%1"=="help" goto :show_help
if "%1"=="--help" goto :show_help
if "%1"=="-h" goto :show_help

if "%1"=="start" goto :start
if "%1"=="start-dev" goto :start_dev
if "%1"=="stop" goto :stop
if "%1"=="restart" goto :restart
if "%1"=="down" goto :down
if "%1"=="clean" goto :clean
if "%1"=="build" goto :build
if "%1"=="rebuild" goto :rebuild
if "%1"=="logs" goto :logs
if "%1"=="logs-envio" goto :logs_envio
if "%1"=="logs-tarifa" goto :logs_tarifa
if "%1"=="logs-notificacion" goto :logs_notificacion
if "%1"=="logs-kafka" goto :logs_kafka
if "%1"=="kafka-topics" goto :kafka_topics
if "%1"=="status" goto :status
if "%1"=="ps" goto :ps
if "%1"=="health" goto :health

echo Unknown command: %1
goto :show_help

:show_help
echo Logistica Sistema - Docker Helper Script
echo.
echo Usage: docker.bat [command]
echo.
echo Service Management:
echo   start               Start all services (production mode)
echo   start-dev           Start all services (development mode)
echo   stop                Stop all services
echo   restart             Restart all services
echo   down                Stop and remove containers
echo   clean               Stop containers and remove volumes
echo.
echo Build Commands:
echo   build               Build all service images
echo   rebuild             Rebuild all images with no cache
echo.
echo Logs:
echo   logs                View logs from all services
echo   logs-envio          View envio-service logs
echo   logs-tarifa         View tarifa-service logs
echo   logs-notificacion   View notificacion-service logs
echo   logs-kafka          View kafka logs
echo.
echo Kafka:
echo   kafka-topics        List Kafka topics
echo.
echo Status:
echo   status              Show status of all services
echo   ps                  List running containers
echo   health              Check health of all services
echo.
goto :eof

:start
echo Starting all services (production mode)...
docker compose up -d
echo.
echo Services started!
echo   Kafka UI:             http://localhost:8080
echo   envio-service:        http://localhost:8081
echo   tarifa-service:       http://localhost:8082
echo   notificacion-service: http://localhost:8083
goto :eof

:start_dev
echo Starting all services (development mode)...
docker compose -f docker-compose.dev.yml up -d
echo.
echo Services started (dev mode)!
echo   Kafka UI:             http://localhost:8080
echo   envio-service:        http://localhost:8081  (H2: /h2-envio)
echo   tarifa-service:       http://localhost:8082  (H2: /h2-tarifa)
echo   notificacion-service: http://localhost:8083  (H2: /h2-notificacion)
goto :eof

:stop
echo Stopping all services...
docker compose down
docker compose -f docker-compose.dev.yml down 2>nul
echo Done.
goto :eof

:restart
echo Restarting all services...
docker compose restart
goto :eof

:down
echo Stopping and removing containers...
docker compose down
docker compose -f docker-compose.dev.yml down 2>nul
goto :eof

:clean
echo WARNING: This will remove all containers and volumes (data will be lost)!
set /p confirm="Are you sure? (y/N): "
if /i "%confirm%"=="y" (
    docker compose down -v
    docker compose -f docker-compose.dev.yml down -v 2>nul
    echo Cleanup complete.
) else (
    echo Cancelled.
)
goto :eof

:build
echo Building all service images...
docker compose build
goto :eof

:rebuild
echo Rebuilding all images (no cache)...
docker compose build --no-cache
goto :eof

:logs
docker compose logs -f
goto :eof

:logs_envio
docker compose logs -f envio-service
goto :eof

:logs_tarifa
docker compose logs -f tarifa-service
goto :eof

:logs_notificacion
docker compose logs -f notificacion-service
goto :eof

:logs_kafka
docker compose logs -f kafka
goto :eof

:kafka_topics
echo Kafka topics:
docker compose exec kafka kafka-topics --bootstrap-server localhost:9092 --list
goto :eof

:status
docker compose ps
goto :eof

:ps
docker compose ps
goto :eof

:health
echo Checking services...
echo.
curl -sf http://localhost:8081/ >nul 2>&1 && (
    echo envio-service:        OK (http://localhost:8081)
) || (
    echo envio-service:        UNREACHABLE
)
curl -sf http://localhost:8082/ >nul 2>&1 && (
    echo tarifa-service:       OK (http://localhost:8082)
) || (
    echo tarifa-service:       UNREACHABLE
)
curl -sf http://localhost:8083/ >nul 2>&1 && (
    echo notificacion-service: OK (http://localhost:8083)
) || (
    echo notificacion-service: UNREACHABLE
)
curl -sf http://localhost:8080/ >nul 2>&1 && (
    echo kafka-ui:             OK (http://localhost:8080)
) || (
    echo kafka-ui:             UNREACHABLE
)
goto :eof
