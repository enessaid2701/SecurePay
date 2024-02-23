# SecurePay Projesi Dokümantasyonu

# Kısa Tanım
SecurePay, müşterilerin çevrimiçi alışveriş yapmasını ve web üzerinden güvenli bir şekilde kredi kartı ile ödeme yapmasını sağlayan bir ödeme portalıdır. Bu proje, yeni müşteri kaydı yapma, müşterinin kredi kartı bilgilerini şifreli olarak saklama, yeni bir ödeme yapma, müşterinin tüm ödemelerini listeleme ve aylık ödeme istatistiklerini sorgulama gibi işlevleri içerir.

# Tasarım
Proje, MVC (Model-View-Controller) mimarisini temel alır. İş mantığı ve veritabanı erişimi için servis katmanları kullanılarak, temiz ve modüler bir kod yapılanması hedeflenmiştir.

# Kullanılan Teknolojiler
Java 11
Spring Framework (Spring Boot kullanılmıştır)
Spring Data JPA
Spring Security
Mysql veritabanı
JWT (JSON Web Token) kütüphanesi
Lombok
Springdoc OpenAPI (Swagger)

# Proje Başlatma
Proje başlatmak için aşağıdaki adımları izleyin:

Proje dosyalarını bir IDE'de (IntelliJ IDEA, Eclipse vb.) açın.
Veritabanı bağlantı ayarlarını application.properties dosyasında yapılandırın.
Proje ana dizininde terminali açın ve mvn spring-boot:run komutunu çalıştırarak uygulamayı başlatın.
Tarayıcınızda http://localhost:8080/swagger-ui.html adresine giderek Swagger arayüzünü kullanarak API'leri göz atın ve test edin.

# Şifreleme Türleri
Kredi kartı bilgileri veritabanında spring security' nin içerisinde bulunan BCryptPasswordEncoder algoritmasıyla şifrelenmiştir.

# Yetkilendirme
API'ler, Bearer Token kullanarak güvenli hale getirilmiştir.