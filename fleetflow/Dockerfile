
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Dossier de travail dans le container
WORKDIR /app

# Copie le fichier pom.xml (dépendances Maven)
COPY pom.xml .

# Télécharge les dépendances (optimisation du cache Docker)
RUN mvn dependency:go-offline -B

# Copie tout le code source
COPY src ./src

# Compile et crée le fichier JAR (sans les tests)
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copie uniquement le JAR depuis l'étape de build
COPY --from=build /app/target/fleetflow-1.0.0.jar app.jar

# Expose le port 8080
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]