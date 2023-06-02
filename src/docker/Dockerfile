#################################################################
#
# Microservice Container Template
# (C) MetaMagic Global Inc, NJ, USA, 2022
# Apache 2.0 License
#
# Container Name: ms-webflux-service
#
# Microservices Containers - SpringBoot 2.7.0 / Java 17
#
# Version 0.1
#################################################################
# Base Version
#FROM metamagic/java:8
FROM metamagic/java:17

LABEL author="Araf Karsh Hamid <araf.karsh@metamagic.in>"

RUN mkdir /Softwares/service
RUN mkdir /Softwares/service/libs
RUN mkdir /home/poduser/libs

ARG SPRINGBOOT_JAR=ms-webflux-service-spring-boot.jar
ARG MAVEN_JAR=ms-webflux-service-thin.jar

# Copy Service (Install)
# COPY --chown=poduser:poduser StartService /Softwares/service/
COPY --chown=poduser:poduser application.properties /home/poduser/
COPY --chown=poduser:poduser application.properties /Softwares/service/
# SpringBoot Fat Jar
COPY --chown=poduser:poduser ${SPRINGBOOT_JAR} /Softwares/service/

# Maven Thin Jar
#COPY --chown=poduser:poduser ${MAVEN_JAR} /Softwares/service/
#COPY --chown=poduser:poduser /libs /Softwares/service/libs/
#COPY --chown=poduser:poduser /libs /home/poduser/libs/

# Change the Mode
RUN chmod -R 750 /Softwares/service/*

# Expose Microservice Port
EXPOSE 9090

USER poduser

# Start Microservice
#ENTRYPOINT ["java","-jar","/Softwares/service/ms-webflux-service-thin.jar"]
ENTRYPOINT ["java","-jar","/Softwares/service/ms-webflux-service-spring-boot.jar"]

