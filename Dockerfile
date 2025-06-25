# Etapa 1: Construirea aplicației folosind Maven și Java 17
# Aceasta este o "mașină de lucru" temporară care are unelte de build.
FROM maven:3.8.5-openjdk-17 AS build

# Copiem tot codul sursă în containerul de build
COPY . .

# Rulăm comanda Maven pentru a compila codul și a crea fișierul .jar
# -DskipTests sare peste rularea testelor pentru a face build-ul mai rapid
RUN mvn clean package -DskipTests


# Etapa 2: Crearea imaginii finale de producție
# Pornim de la o imagine Java "slim", care este mult mai mică și mai sigură.
FROM openjdk:17.0.1-jdk-slim

# Copiem doar fișierul .jar rezultat din etapa de build
COPY --from=build /target/*.jar app.jar

# Expunem portul pe care rulează aplicația Spring Boot
EXPOSE 8080

# Specificăm comanda care va porni aplicația noastră când containerul pornește
ENTRYPOINT ["java","-jar","app.jar"]