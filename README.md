## CV PDF Generator (Java + iText 5)

Java 17 ve iText 5 kullanarak Türkçe karakter desteğiyle özgeçmiş (CV) PDF'i oluşturan basit bir konsol uygulaması.

### Özellikler
- Türkçe karakterler için gömülü TTF font (Noto Sans)
- Fotoğraf ekleme (classpath üzerinden)
- Başlık, özet, yetenekler ve deneyim blokları
- A4 sayfa, kenar boşlukları ve basit iki sütunlu düzenler

### Proje Yapısı
```text
src/main/java/org/example/ResumePdfGenerator.java   // PDF üretim akışı
src/main/resources/fonts/NotoSans-Regular.ttf       // Gömülü font
src/main/resources/PP.jpg                           // Profil fotoğrafı (bkz. Notlar)
pom.xml                                             // Maven yapılandırması
ozgecmis.pdf                                       // Üretilen çıktı (çalıştırma sonrası)
```

### Gereksinimler
- Java 17+
- Maven 3.8+

### Kurulum ve Çalıştırma
```bash
# bağımlılıkları indir ve derle
mvn -q clean package

# uygulamayı çalıştır (exec-maven-plugin ile)
mvn -q exec:java

# çıktı
# PDF başarıyla oluşturuldu: ozgecmis.pdf
```

Çalıştırma sonrası proje kök dizininde `ozgecmis.pdf` oluşur.

### Özelleştirme
- Metin içerikleri: `org.example.ResumePdfGenerator` içindeki `createCv` metodunda; ad-soyad, pozisyon, iletişim, özet ve deneyim maddelerini düzenleyin.
- Font: `src/main/resources/fonts/NotoSans-Regular.ttf` yolundaki dosyayı değiştirerek farklı bir TTF kullanabilirsiniz.
- Fotoğraf: `src/main/resources` altındaki görsel dosyasını değiştirin ve sınıf içindeki `loadImage(...)` çağrısındaki classpath yolunu güncelleyin.

### Notlar ve İpuçları
- Kaynak yolları (classpath) dosya adının büyük/küçük harf duyarlılığına takılabilir. Kod şu an `loadImage("/pp.jpg", ...)` çağırıyor; proje kaynağı ise `PP.jpg`. Gerekirse ya dosyayı `pp.jpg` olarak yeniden adlandırın ya da kodu `"/PP.jpg"` olacak şekilde güncelleyin.
- Font bulunamazsa uygulama hata verir. Fontun gerçekten `src/main/resources/fonts/NotoSans-Regular.ttf` altında olduğundan emin olun.

### Bağımlılıklar
- iText 5 (`com.itextpdf:itextpdf:5.5.13.3`) — PDF oluşturma

### Lisans
Bu depo kişisel/demonstrasyon amaçlıdır. Aksi belirtilmedikçe kod için varsayılan telif hakkı sahibi depoyu oluşturan kişidir. iText lisans şartları için ilgili kütüphane lisansına bakınız.

