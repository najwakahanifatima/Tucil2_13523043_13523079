# Kompresi Gambar dengan Metode Quadtree

Program ini merupakan program sederhana berbasis Java yang mengimplementasikan *algoritma divide and conquer* untuk melakukan kompresi gambar menggunakan struktur data Quadtree.  Program ini memungkinkan pengguna untuk mengontrol berbagai parameter kompresi seperti metode perhitungan error, ambang batas (threshold), ukuran blok minimum, dan target persentase kompresi. Selain itu, program ini juga menyediakan visualisasi proses kompresi gambar dalam bentuk gif.

## Requirement
1. Java Development Kit (JDK)
2. Ruang penyimpanan yang cukup untuk menyimpan gambar input dan output
3. Sistem operasi yang mendukung Java (Windows, macOS, Linux)

## Cara Mengkompilasi Program dan Menjalankan Program
1. Pastikan java sudah terinstall. Untuk mengecek instalasi, buka terminal atau command prompt lalu jalankan perintah berikut. <br>
   ```
   javac -version
   ```
2. Jalankan perintah di bawah ini.
   ```
   java -cp bin Main
   ```
4. Jika melakukan perubahan pada kode program, compile ulang dengan  menjalankan run.bat
   ```
   ./run.bat
   ```

## Alur Program
Program akan meminta input dengan alur berikut:
1. Alamat absolut gambar yang akan dikompresi
3. Pilihan metode perhitungan error (Variance/MAD/MPD/Entropy/SSIM)
4. Threshold (ambang batas) sesuai dengan metode yang dipilih
5. Ukuran blok minimum
6. Target persentase kompresi berupa floating point (0.0 - 1.0, di mana 1.0 = 100%), masukkan 0 jika ingin menonaktifkan mode ini
7. Alamat absolut untuk menyimpan gambar hasil kompresi
8. Pilihan untuk membuat GIF visualisasi proses kompresi (y/n)
9. Jika 'y', program akan meminta input alamat absolut untuk menyimpan file GIF
Selanjutnya, program akan menjalankan algoritma kompresi dan menampilkan detail hasil kompresi gamber serta menyimpan hasil kompresi pada alamat absolut yang sebelumnya telah dimasukkan.

## Identitas Pembuat
<div>
    <table align="center">
      <tr>
        <td>NIM</td>
        <td>Nama</td>
      </tr>
      <tr>
        <td>13522043</td>
        <td>Najwa Kahani Fatima</td>
      </tr>
      <tr>
        <td>13522079</td>
        <td>Nayla Zahira</td>
      </tr>
    </table>
</div>
