# Stage 1: Build the React app
FROM node:18-alpine as build
WORKDIR /app
# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm install
# Copy the rest of the app and build for production
COPY . .
RUN npm run build

# Stage 2: Serve the built app using Nginx
FROM nginx:stable-alpine
# Copy the build output to Nginx's html folder
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]