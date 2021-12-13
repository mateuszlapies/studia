FROM openjdk as spring
RUN brew install maven
RUN git clone https://github.com/mateuszlapies/studia.git
WORKDIR studia/ztp/projekt/backend
RUN mvn compile
RUN mvn package
ENTRYPOINT ["java", "-jar", "target/backend-dev"]

#FROM node as react
#RUN git clone https://github.com/mateuszlapies/studia.git
#WORKDIR studia/ztp/projekt/frontend
#RUN yarn install
#ENTRYPOINT ["yarn", "start]