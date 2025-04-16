FROM node:lts-alpine
WORKDIR /opt/app
COPY package*.json ./
RUN npm ci
COPY .. .
CMD ["npm", "start"]
EXPOSE 9999