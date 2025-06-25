# MODIFICARE: Folosim o imagine de build cu Java 24
FROM maven:3.9.8-eclipse-temurin-24 AS build

# Copiem tot codul sursă în containerul de build
COPY . .

# Rulăm comanda Maven pentru a compila codul și a crea fișierul .jar
RUN mvn clean package -DskipTests


# MODIFICARE: Folosim o imagine finală tot cu Java 24 (JRE - mai mică)
FROM eclipse-temurin:24-jre

# Copiem doar fișierul .jar rezultat din etapa de build
COPY --from=build /target/*.jar app.jar

# Expunem portul pe care rulează aplicația Spring Boot
EXPOSE 8080

# Specificăm comanda care va porni aplicația noastră când containerul pornește
ENTRYPOINT ["java","-jar","app.jar"]
