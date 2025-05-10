# Temel imaj: Java 17 içeren küçük boyutlu bir Linux imajı
FROM openjdk:17-jdk-slim

# Çalışma dizinini oluştur
WORKDIR /app

# Maven ile build edilmiş JAR dosyasını konteynıra kopyala
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Uygulama başlatıldığında çalışacak komut
ENTRYPOINT ["java", "-jar", "app.jar"]
