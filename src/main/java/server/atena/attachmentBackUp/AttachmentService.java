//package server.atena.attachmentBackUp;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardCopyOption;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class AttachmentService {
//
//    @Value("${attachment.upload.dir}")
//    private String uploadDir;
//
//    @Transactional
//    public String uploadAttachment(MultipartFile file) throws IOException {
//        // Tworzymy unikalną nazwę pliku
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//        // Tworzymy ścieżkę do katalogu uploadu
//        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
//
//        // Tworzymy ścieżkę do pliku
//        Path filePath = uploadPath.resolve(fileName);
//
//        // Kopiujemy plik do katalogu uploadu
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        return fileName;
//    }
//
//    public String getUploadDir() {
//        return uploadDir;
//    }
//}
