FROM openjdk:17-alpine as spring
RUN apk add git
RUN git clone https://github.com/mateuszlapies/studia.git
WORKDIR studia/ztp/projekt/backend
RUN git pull
RUN chmod u+x mvnw
RUN ./mvnw package
ENTRYPOINT ["java", "-jar", "target/backend-dev.jar"]

FROM node:16.13.1-alpine as react
RUN apk add git
RUN git clone https://github.com/mateuszlapies/studia.git
WORKDIR studia/ztp/projekt/frontend
RUN yarn install
ENTRYPOINT ["yarn", "start"]