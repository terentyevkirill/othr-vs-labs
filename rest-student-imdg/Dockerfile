FROM maven:3.6
MAINTAINER terentievkirill@gmail.com
RUN mkdir /usr/src/vs
WORKDIR /usr/src/vs
COPY src /usr/src/vs/src
COPY pom.xml /usr/src/vs
RUN mvn compile
EXPOSE 8080
EXPOSE 5701
CMD [ "mvn", "exec:exec" ]
