//package server.atena.attachmentBackUp;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/attachment")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AttachmentController {
//
//	@Autowired
//	private AttachmentService attachmentService;
//
//	@PostMapping("/upload")
//	public ResponseEntity<String> uploadAttachment(@RequestParam("file") MultipartFile file) {
//		try {
//			String attachmentPath = attachmentService.uploadAttachment(file);
//			// Zapisz informacje o załączniku w bazie danych - użyj odpowiedniego
//			// repozytorium
//			// np. attachmentRepository.save(new Attachment(attachmentPath));
//			return ResponseEntity.ok("Plik załącznika został pomyślnie przesłany.");
//		} catch (IOException e) {
//			e.printStackTrace(); // Dodaj obsługę błędów
//            throw new RuntimeException("Błąd podczas przetwarzania załącznika.", e);
//			//return ResponseEntity.status(500).body("Błąd podczas przesyłania pliku załącznika.");
//		}
//	}
//
//	@GetMapping("/download/{attachmentPath}")
//	public ResponseEntity<byte[]> downloadAttachment(@PathVariable String attachmentPath) throws IOException {
//		// Uzyskaj pełną ścieżkę do pliku
//		Path filePath = Path.of(attachmentService.getUploadDir()).resolve(attachmentPath).normalize();
//
//		// Wczytaj plik do tablicy bajtów
//		byte[] fileContent = Files.readAllBytes(filePath);
//
//		// Zwróć odpowiedź HTTP z plikiem
//		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + attachmentPath)
//				.body(fileContent);
//	}
//}
