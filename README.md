# Gözlük E‑Ticaret Sitesi

> Spring Boot 3.4.3 • Java 17 • PostgreSQL • Thymeleaf • Docker

Bu repoda **gözlük satışı** odaklı, e‑posta doğrulamalı ve sepet yönetimli
bir e‑ticaret uygulamasının tam kaynak kodu yer alır. Katmanlı mimari,
Docker ile dağıtım, JWT ile güvenlik (hazır) ve yönetici paneli içerir.



![Ekran Görüntüsü (219)](https://github.com/user-attachments/assets/ab856406-9e4c-4943-abc5-335706ce7696)




---

## İçindekiler

1. [Özellikler](#özellikler)
2. [Teknolojiler](#teknolojiler)
3. [Hızlı Başlangıç](#hızlı-başlangıç)
4. [Geliştirme Ortamı](#geliştirme-ortamı)
5. [Docker ile Çalıştırma](#docker-ile-çalıştırma)
6. [Dizin Yapısı](#dizin-yapısı)
7. [Çevre Değişkenleri](#çevre-değişkenleri)
8. [Katkıda Bulunma](#katkıda-bulunma)


---

## Özellikler

| Modül                  | Açıklama                                                     |
| ---------------------- | ------------------------------------------------------------ |
| **Ürün Kataloğu**      | Marka, kategori, stok takibi & çoklu görsel desteği          |
| **Sepet**              | Oturum tabanlı sepet; miktar arttır/azalt                    |
| **Sipariş**            | Adres seçimi, kayıtlı kart seçimi, sipariş durumu takibi     |
| **Kullanıcı Yönetimi** | BCrypt şifreleme, e‑posta aktivasyonu, profil güncelleme     |
| **Yönetici Paneli**    | Ürün CRUD, sipariş durumu güncelleme, stok uyarıları         |
| **Güvenlik**           | Spring Security + JWT (hazırlanmış), CSRF koruma             |
| **Docker Desteği**     | Tek `jar` → `Dockerfile`; `docker‑compose.yml` ile konteyner |

---

## Teknolojiler

* **Spring Boot 3.4.3** (REST & MVC)
* **Java 17** (OpenJDK)
* **Spring Data JPA** + **Hibernate**
* **PostgreSQL 15**
* **Thymeleaf** + Bootstrap 5
* **Spring Security** + JWT
* **Lombok**, **MapStruct**
* **Docker / Docker Compose**

---

## Hızlı Başlangıç

### 1 ⟩ Depoyu klonlayın

```bash
git clone https://github.com/<kerimtetik>/eticaret.git
cd gozluk-eticaret
```

### 2 ⟩ Çevre değişkenlerinizi oluşturun

`.env.example` dosyasını kopyalayıp SMTP & DB bilgilerinizi girin.

```bash
cp .env.example .env
```

### 3 ⟩ Geliştirme modunda çalıştırın

```bash
./mvnw spring-boot:run   # <http://localhost:8080>
```

> İlk çalıştırmada PostgreSQL’e bağlanır ve tabloları otomatik oluşturur.

---

## Geliştirme Ortamı

| Gereksinim        | Sürüm     | Not                                                     |
| ----------------- | --------- | ------------------------------------------------------- |
| **Java**          | 17+       | [https://adoptium.net/](https://adoptium.net/) önerilir |
| **Maven Wrapper** | v3.9      | Repo içinde hazır (`./mvnw`)                            |
| **PostgreSQL**    | 15        | `spring.datasource.*` parametreleri `.env`’de           |
| **Node & npm**    | opsiyonel | CSS/JS derleme için                                     |

IDE olarak **Eclipse**  tavsiye edilir.

---

## Docker ile Çalıştırma

```bash
# Jar üret
./mvnw clean package -DskipTests

# Görüntüyü oluştur
docker build -t gozluk-app .

# Uygulama + Postgres
docker compose --env-file .env up -d
```

* Uygulama: [http://localhost:8080](http://localhost:8080)
* pgAdmin kurulumu için `docker-compose.yml` içinde servis ekleyebilirsiniz.

---

## Dizin Yapısı

```
src/main/java/com/gozluketicaret/demo
├── controller/        # MVC + REST
├── service/           # İş kuralları
├── repository/        # JPA arayüzleri
├── model/             # @Entity sınıfları
└── config/            # SecurityConfig, MailConfig…

src/main/resources
├── templates/         # Thymeleaf sayfaları
├── static/            # CSS, JS, img
└── application.properties
```

---

## Çevre Değişkenleri

| Anahtar                      | Açıklama                                    |
| ---------------------------- | ------------------------------------------- |
| `SPRING_DATASOURCE_URL`      | `jdbc:postgresql://localhost:5432/eticaret` |
| `SPRING_DATASOURCE_USERNAME` | DB kullanıcı adı                            |
| `SPRING_DATASOURCE_PASSWORD` | DB parolası                                 |
| `SPRING_MAIL_USERNAME`       | SMTP e‑posta                                |
| `SPRING_MAIL_PASSWORD`       | SMTP parola veya App Password               |
| `JWT_SECRET`                 | Token imzalama anahtarı                     |

`.env.example` tüm anahtarları gösterir; gizli bilgileri **repo dışında** tutun.

---

