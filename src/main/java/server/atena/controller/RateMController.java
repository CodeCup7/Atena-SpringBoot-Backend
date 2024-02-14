package server.atena.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.RateM;
import server.atena.models.SearchCriteria;
import server.atena.service.RateMService;

@RestController
@RequestMapping("/api/rateM")
@CrossOrigin(origins = "http://localhost:3000")
public class RateMController {

	private final RateMService service;

	@Value("${attachment.upload.dir}")
	private String uploadDir;

	@Autowired
	public RateMController(RateMService service) {
		this.service = service;
	}

	@PostMapping("/add")
	public ResponseEntity<RateM> add(@RequestPart("rateM") String rateMJson, @RequestPart("file") MultipartFile file)
			throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		RateM rateM = objectMapper.readValue(rateMJson, RateM.class);

		RateM addedRateM = service.add(rateM);

		String fileName = String.valueOf(addedRateM.getId()) + "_" + file.getOriginalFilename();
		Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
		Path filePath = uploadPath.resolve(fileName);
		
		try (InputStream fileInputStream = file.getInputStream()) {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Aktualizacja DB ścieżka do pliku
		addedRateM.setAttachmentPath(fileName);
		service.update(addedRateM);

		return new ResponseEntity<>(addedRateM, HttpStatus.OK);
	}

	@PostMapping("/search")
	public List<RateM> searchNotes(@RequestBody List<SearchCriteria> params) {
		return service.searchRates(params);
	}

	@GetMapping("/getAllRates")
	public ResponseEntity<Iterable<RateM>> getAllRates() {
		Iterable<RateM> rates = service.getAllRates();
		return ResponseEntity.ok(rates);
	}

	// Pobiera wszystkie RateM które nie są przypisane do coachingu
	@GetMapping("/getAllRateNoNote")
	public ResponseEntity<Iterable<RateM>> getAllRateNoNote() {
		Iterable<RateM> rates = service.getAllRateNoNote();
		return ResponseEntity.ok(rates);
	}
	
	// Pobiera wszystkie oceny agenta do modyfikcaji listy coachingowej
	@GetMapping("/getAllRateNoNoteByAgent/{id}")
	public ResponseEntity<Iterable<RateM>> getAllRateNoNoteByAgent(@PathVariable Long id) {
		Iterable<RateM> rates = service.getAllRateNoNoteByAgent(id);
		return ResponseEntity.ok(rates);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<RateM> getById(@PathVariable Long id) {
		RateM RateM = service.getById(id);
		return ResponseEntity.ok(RateM);
	}
	
    @GetMapping("/getAttachment")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

	@PostMapping("/update")
	public void update(@RequestPart("rateM") String rateMJson, @RequestPart("file") MultipartFile file) throws IOException, InterruptedException {
		ObjectMapper objectMapper = new ObjectMapper();
		RateM rateM = objectMapper.readValue(rateMJson, RateM.class);
		
		if(file != null) { // tylko gdy wraz z aktualizacją nastepuje podmiana pliku
			
			String fileName = String.valueOf(rateM.getId()) + "_" + file.getOriginalFilename();
			Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
			Path filePath = uploadPath.resolve(fileName);
			
			String fileNameToDelete = String.valueOf(rateM.getAttachmentPath());
			Path deletePath = Path.of(uploadDir).toAbsolutePath().normalize();
			Path fileDeletePath = deletePath.resolve(fileNameToDelete);
			
			// Usunięcie starego pliku !!!!!!!!!!!!!!!
		    Files.delete(fileDeletePath);
		    
			try (InputStream fileInputStream = file.getInputStream()) {
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Aktualizacja DB ścieżka do pliku
			rateM.setAttachmentPath(fileName);
		}
		
		service.update(rateM);
	}
	
	@PostMapping("/updateList/{noteId}")
	public ResponseEntity<String> updateList(@RequestBody String json_rateCC, @PathVariable BigInteger noteId) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<RateM> rateMList = objectMapper.readValue(json_rateCC, new TypeReference<List<RateM>>() {
		});
		
		service.updateList(rateMList, noteId);
		return new ResponseEntity<>("Lista została pomyślnie zaktualizowana.", HttpStatus.OK);
	}
	
	@PostMapping("/deleteList")
	public ResponseEntity<String> deleteList(@RequestBody String json_rateCC) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<RateM> rateMList = objectMapper.readValue(json_rateCC, new TypeReference<List<RateM>>() {
		});
		
		service.deleteList(rateMList);
		return new ResponseEntity<>("Lista została pomyślnie zaktualizowana.", HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}
	
	@PostMapping("/export")
	public ResponseEntity<Resource> exportToFile(@RequestBody String json_rateM) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			RateM rateM = objectMapper.readValue(json_rateM, RateM.class);

			String fileName = String.valueOf(rateM.getId());
			Path filePath = Path.of(uploadDir).resolve(fileName + ".json"); // Dodaj ".json" do nazwy pliku

			Files.writeString(filePath, json_rateM); // Zapisz dane JSON do pliku

			Resource resource = new UrlResource(filePath.toUri());

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
