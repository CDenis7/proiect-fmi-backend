# Etapa 1: Folosim o imagine de build validă cu Java 24 de la SAP
# Această imagine este oficială și bine întreținută.
FROM maven:3-sapmachine-24 AS build

# Copiem tot codul sursă în containerul de build
COPY . .

# Rulăm comanda Maven pentru a compila codul și a crea fișierul .jar
RUN mvn clean package -DskipTests


# Etapa 2: Folosim o imagine de rulare validă și mică (JRE) cu Java 24
# Eclipse Temurin este un furnizor de încredere pentru OpenJDK.
FROM eclipse-temurin:24-jre

# Copiem doar fișierul .jar rezultat din etapa de build
COPY --from=build /target/*.jar app.jar

# Expunem portul pe care rulează aplicația Spring Boot
EXPOSE 8080

# Specificăm comanda care va porni aplicația noastră când containerul pornește
ENTRYPOINT ["java","-jar","app.jar"]
