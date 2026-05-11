#!/bin/bash
# Logistica Sistema - Docker Helper Script

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

ok()   { echo -e "${GREEN}✓ $1${NC}"; }
err()  { echo -e "${RED}✗ $1${NC}"; }
info() { echo -e "${BLUE}➜ $1${NC}"; }
warn() { echo -e "${YELLOW}⚠ $1${NC}"; }

show_help() {
    cat << EOF
${BLUE}Logistica Sistema - Docker Helper${NC}

Usage: ./docker.sh [command]

${GREEN}Service Management:${NC}
  start               Start all services (production mode)
  start-dev           Start all services (development mode)
  stop                Stop all services
  restart             Restart all services
  down                Stop and remove containers
  clean               Remove containers and volumes

${GREEN}Build:${NC}
  build               Build all service images
  rebuild             Rebuild all images with no cache

${GREEN}Logs:${NC}
  logs                All services
  logs-envio          envio-service
  logs-tarifa         tarifa-service
  logs-notificacion   notificacion-service
  logs-kafka          kafka

${GREEN}Kafka:${NC}
  kafka-topics        List topics
  kafka-shell         Open Kafka shell

${GREEN}Status:${NC}
  status              Show all containers
  ps                  List running containers
  health              Check reachability of all services

EOF
}

case "$1" in
    start)
        info "Starting all services (production mode)..."
        docker compose up -d
        echo ""
        ok "Services started!"
        info "  Kafka UI:             http://localhost:8080"
        info "  envio-service:        http://localhost:8081"
        info "  tarifa-service:       http://localhost:8082"
        info "  notificacion-service: http://localhost:8083"
        ;;

    start-dev)
        info "Starting all services (development mode)..."
        docker compose -f docker-compose.dev.yml up -d
        echo ""
        ok "Services started (dev mode)!"
        info "  Kafka UI:             http://localhost:8080"
        info "  envio-service:        http://localhost:8081  (H2: /h2-envio)"
        info "  tarifa-service:       http://localhost:8082  (H2: /h2-tarifa)"
        info "  notificacion-service: http://localhost:8083  (H2: /h2-notificacion)"
        ;;

    stop)
        info "Stopping all services..."
        docker compose down
        docker compose -f docker-compose.dev.yml down 2>/dev/null || true
        ok "Done."
        ;;

    restart)
        info "Restarting all services..."
        docker compose restart
        ok "Done."
        ;;

    down)
        info "Removing containers..."
        docker compose down
        docker compose -f docker-compose.dev.yml down 2>/dev/null || true
        ok "Done."
        ;;

    clean)
        warn "This will remove all containers and volumes (data will be lost)!"
        read -p "Are you sure? (y/N) " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            docker compose down -v
            docker compose -f docker-compose.dev.yml down -v 2>/dev/null || true
            ok "Cleanup complete."
        else
            info "Cancelled."
        fi
        ;;

    build)
        info "Building all service images..."
        docker compose build
        ok "Build complete."
        ;;

    rebuild)
        info "Rebuilding all images (no cache)..."
        docker compose build --no-cache
        ok "Rebuild complete."
        ;;

    logs)
        docker compose logs -f
        ;;

    logs-envio)
        docker compose logs -f envio-service
        ;;

    logs-tarifa)
        docker compose logs -f tarifa-service
        ;;

    logs-notificacion)
        docker compose logs -f notificacion-service
        ;;

    logs-kafka)
        docker compose logs -f kafka
        ;;

    kafka-topics)
        info "Kafka topics:"
        docker compose exec kafka kafka-topics --bootstrap-server localhost:9092 --list
        ;;

    kafka-shell)
        info "Opening Kafka shell..."
        docker compose exec kafka bash
        ;;

    status)
        docker compose ps
        ;;

    ps)
        docker compose ps
        ;;

    health)
        info "Checking services..."
        echo ""
        for entry in "envio-service:8081" "tarifa-service:8082" "notificacion-service:8083" "kafka-ui:8080"; do
            name="${entry%%:*}"
            port="${entry##*:}"
            if curl -sf "http://localhost:${port}/" > /dev/null 2>&1; then
                ok "${name}: http://localhost:${port}"
            else
                err "${name}: UNREACHABLE (http://localhost:${port})"
            fi
        done
        ;;

    help|--help|-h|"")
        show_help
        ;;

    *)
        err "Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac
