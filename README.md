# CV PDF Oluşturucu

Java ve iText kütüphanesi kullanarak profesyonel özgeçmiş PDF'i oluşturan bir uygulama.

## Özellikler

- 📄 A4 boyutunda profesyonel CV formatı
- 🖼️ Profil fotoğrafı desteği
- 🔤 Türkçe karakter desteği (NotoSans fontu)
- 📝 Kişisel bilgiler, özet, yetenekler, iş deneyimi ve eğitim bölümleri
- 🎨 Temiz ve okunabilir tasarım

## Gereksinimler

- Java 17 veya üzeri
- Maven
- iText PDF kütüphanesi (5.x)

## Kurulum

### 1. Bağımlılıkları Ekleyin

`pom.xml` dosyanıza aşağıdaki bağımlılığı ekleyin:

```xml
<dependencies>
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.3</version>
    </dependency>
</dependencies>
```

### 2. Font Dosyasını Ekleyin

Türkçe karakter desteği için NotoSans fontunu indirin ve aşağıdaki konuma yerleştirin:

```
src/main/resources/fonts/NotoSans-Regular.ttf
```

Font indirme linki: [Google Fonts - Noto Sans](https://fonts.google.com/noto/specimen/Noto+Sans)

### 3. Profil Fotoğrafı Ekleyin (Opsiyonel)

Profil fotoğrafınızı aşağıdaki konuma yerleştirin:

```
src/main/resources/pp.jpg
```

## Kullanım

### Projeyi Çalıştırma

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.example.ResumePdfGenerator"
```

veya IDE'nizden `ResumePdfGenerator` sınıfının `main` metodunu çalıştırın.

### Kişiselleştirme

`createCv` metodundaki bilgileri kendinize göre düzenleyin:

```java
// Kişisel bilgiler
nameCell.addElement(new Paragraph("Adınız Soyadınız", TITLE_FONT));
nameCell.addElement(new Paragraph("Ünvanınız", NORMAL_FONT));
nameCell.addElement(new Paragraph("E-posta: email@mail.com | Tel: +90 5xx xxx xx xx", SMALL_FONT));

// Özet
document.add(new Paragraph("Kendi özet yazınız...", NORMAL_FONT));

// Yetenekler
PdfPTable skills = simpleListTable(new String[] {
    "Yetenek 1", "Yetenek 2", "Yetenek 3"
});

// İş deneyimi
document.add(experienceBlock(
    "Pozisyon", "Şirket Adı", "Tarih Aralığı",
    new String[] {
        "Görev 1",
        "Görev 2",
        "Görev 3"
    }
));
```

## Proje Yapısı

```
src/
├── main/
│   ├── java/
│   │   └── org/example/
│   │       └── ResumePdfGenerator.java
│   └── resources/
│       ├── fonts/
│       │   └── NotoSans-Regular.ttf
│       └── pp.jpg
└── test/
```

## Çıktı

Program çalıştırıldığında proje kök dizininde `ozgecmis.pdf` dosyası oluşturulur.

## Kod Yapısı

- **initFonts()**: Font ayarlarını yapılandırır
- **createCv()**: Ana CV oluşturma metodu
- **simpleListTable()**: Madde işaretli liste tablosu oluşturur
- **experienceBlock()**: İş deneyimi bloğu oluşturur
- **loadImage()**: Profil fotoğrafını yükler

## Sorun Giderme

### Font Hatası
```
Font bulunamadı. 'src/main/resources/fonts/NotoSans-Regular.ttf' ekleyin.
```
**Çözüm**: NotoSans-Regular.ttf dosyasını belirtilen konuma ekleyin.

### Fotoğraf Görünmüyor
- `pp.jpg` dosyasının `src/main/resources/` dizininde olduğundan emin olun
- Dosya formatının JPG olduğunu kontrol edin
- Dosya boyutunun makul olduğundan emin olun (< 2MB)

## Lisans

Bu proje eğitim amaçlı hazırlanmıştır.

## Katkıda Bulunma

- Fork edin
- Feature branch oluşturun (`git checkout -b feature/yeniOzellik`)
- Değişikliklerinizi commit edin (`git commit -m 'Yeni özellik eklendi'`)
- Branch'inizi push edin (`git push origin feature/yeniOzellik`)
- Pull Request oluşturun
