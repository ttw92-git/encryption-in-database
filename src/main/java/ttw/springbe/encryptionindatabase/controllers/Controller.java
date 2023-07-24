package ttw.springbe.encryptionindatabase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttw.springbe.encryptionindatabase.services.TableAService;

@RestController
@RequestMapping("/api")
public class Controller {
    
    @Autowired
    private TableAService tableAService;

	@GetMapping(value="/reset-to-default-and-get-data-a")
	public ResponseEntity<Object> resetToDefaultAndGetDataA() {
		return new ResponseEntity<>(tableAService.resetToNormalAndGetDataA(), HttpStatus.OK);
	}

	@GetMapping(value="/get-data-a")
	public ResponseEntity<Object> getAllDataA() {
		return new ResponseEntity<>(tableAService.getAll(), HttpStatus.OK);
	}

    @GetMapping(value="/update-a1-value5")
	public ResponseEntity<Object> updateA1Value5() {
		tableAService.updateA1Value5();
		return new ResponseEntity<>(tableAService.getAll(), HttpStatus.OK);
	}

	@GetMapping(value="/update-a2-value3")
	public ResponseEntity<Object> updateA2Value3() {
		tableAService.updateA2Value3();
		return new ResponseEntity<>(tableAService.getAll(), HttpStatus.OK);
	}
}
