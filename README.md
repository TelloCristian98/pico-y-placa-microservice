# Pico y Placa Microservice Application

This project is a full-stack application that checks the "Pico y Placa" rules for vehicles in Quito, Ecuador. It consists of a Spring Boot backend, a React frontend, and a PostgreSQL database—all containerized using Docker for easy deployment.

## Author
Cristian Tello

- [GitHub Profile](https://github.com/TelloCristian98)
- Email: cristian.andres.t98@gmail.com

## Prerequisites
- [Docker](https://www.docker.com/get-started) and [Docker Compose](https://docs.docker.com/compose/install/) installed on your machine.
- Git installed to clone the repository.

## Project Structure

picoyplaca/
├── docker-compose.yml
├── backend/
│   ├── Dockerfile         # Dockerfile for the Spring Boot backend
│   ├── pom.xml
│   └── src/               # Backend source code
└── frontend/
    └── pico-placa-frontend/
        ├── Dockerfile     # Dockerfile for the React frontend
        ├── package.json
        └── src/           # Frontend source code

## How to Run the Application Using Docker

1. **Clone the Repository:**
   ```bash
   git clone <repository-url>
   cd picoyplaca
   ```

2. **Build and Start Containers with Docker Compose:**

    From the root directory (where `docker-compose.yml` is located), run:

    ```bash
    docker-compose up --build
    ```

    This command will:
    - Build the backend image from `backend/Dockerfile` and run the Spring Boot app on port 8080.
    - Build the frontend image from `frontend/pico-placa-frontend/Dockerfile` and serve the React app via Nginx on port 3000.
    - Start a PostgreSQL container (using the official PostgreSQL image) on port 5432 with the necessary environment variables.

3. **Access the Application:**
    - **Frontend UI:** Open your browser and go to [http://localhost:3000](http://localhost:3000) to use the Pico y Placa checker.
    - **Backend API:** Test the backend API at [http://localhost:8080/api/check](http://localhost:8080/api/check).
    ## Application Details

    ### Backend
    A Spring Boot microservice that receives a license plate (formatted as ABC-1234), a date (YYYY-MM-DD), and a time (HH:MM). It validates the inputs, checks the “Pico y Placa” rules (including weekend logic), logs the request, and returns whether the vehicle is allowed to circulate.

    ### Frontend
    A React application that provides a simple form for users to enter the license plate, date, and time. The form validates inputs and calls the backend API to display the result. The frontend is built and served by Nginx.

    ### Database
    PostgreSQL stores the “Pico y Placa” rules and logs API requests. The database container is configured via environment variables in the docker-compose.yml.

    ## Docker Compose Configuration

    The docker-compose.yml defines three services:

    ### backend
    - Build context: `./backend`
    - Exposed port: `8080`
    - Environment variables for database connectivity are set to point to the db service.

    ### frontend
    - Build context: `./frontend/pico-placa-frontend`
    - Nginx serves the production build on port 80 inside the container, mapped to host port `3000`.

    ### db
    - Uses the official `postgres:14` image.
    - Exposed port: `5432`
    - Configured with environment variables to set the database name, user, and password.
    - Uses a named volume (`pgdata`) for data persistence.

    ## Stopping the Application

    To stop the running containers, press `CTRL+C` in the terminal where Docker Compose is running, then run:
    ```bash
    docker-compose down
    ```
