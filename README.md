# Bilgisayar Ağları Notları ve Laboratuvar Çalışmaları

Bu repoda, Bilgisayar Ağları dersi kapsamında yapılan laboratuvar çalışmalarının Java ile geliştirilmiş örnekleri yer almaktadır. Proje Maven ve NetBeans altyapısı kullanılarak hazırlanmıştır.

## İçerik

### 📁 lab1
- **Uygulama:** UpperCase ve toplama işlemi sunucusu/istemcisi
- **Teknoloji:** Java Socket Programlama (Temel TCP/IP)
- **Dosyalar:** `plusClient.java`, `plusServer.java`, `UpperCaseClient.java`, `UpperCaseServer.java`

### 📁 lab2
- **Uygulama:** Basit GUI ile istemci-sunucu haberleşmesi
- **Teknoloji:** Java Swing, TCP Soket
- **Yapı:** Maven projesi

### 📁 lab3
- **Uygulama:** Mesajlaşma uygulaması (GUI destekli)
- **Teknoloji:** Java Swing, Soket
- **Özellikler:** Mesaj tipleri, istemciler arası yönlendirme

### 📁 lab4
- **Uygulama:** Geliştirilmiş mesajlaşma sunucusu
- **Teknoloji:** Maven, çoklu istemci desteği
- **Çıktı:** `myserver-1.0.jar`

### 📁 lab5
- **Uygulama:** UDP istemci/sunucu örneği
- **Teknoloji:** Java DatagramSocket

### 📁 lab6
- **Uygulama:** UDP uygulamasının geliştirilmiş hali
- **Teknoloji:** UDP, Java Soket, Maven

## Terminal üzerinden de çalıştırılabilir. Lablar çalışmıyorda olabilir.

## Geliştirme Ortamı
- Java 8+
- Apache Maven
- NetBeans (bazı projeler `.nbproject` içerir)
- IDE bağımsız çalıştırılabilir yapı

## Başlatma
Her lab klasöründe bağımsız bir proje vardır. Maven projelerinde:

```bash
cd labX
mvn clean install

Uygulamalar target/ klasöründe oluşan .jar dosyaları ile çalıştırılabilir.

Lisans
Bu proje eğitim amaçlıdır.

🛠 Projeyi geliştiren veya katkı sunmak isteyenler için PR'lara açığız!

