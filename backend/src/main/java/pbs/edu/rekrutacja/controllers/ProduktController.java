package pbs.edu.rekrutacja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.edu.rekrutacja.models.Produkt;
import pbs.edu.rekrutacja.services.ProduktService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProduktController {

    private final ProduktService produktService;

    @Autowired
    public ProduktController(ProduktService produktService) {
        this.produktService = produktService;
    }

    @GetMapping
    public ResponseEntity<List<Produkt>> getAllProdukts() {
        List<Produkt> produkts = produktService.getAllProdukts();
        return new ResponseEntity<>(produkts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produkt> getProduktById(@PathVariable Long id) {
        Produkt produkt = produktService.getProduktById(id);
        if (produkt != null) {
            return new ResponseEntity<>(produkt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Produkt> saveProdukt(@RequestBody Produkt produkt) {
        Produkt savedProdukt = produktService.saveProdukt(produkt);
        return new ResponseEntity<>(savedProdukt, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdukt(@PathVariable Long id) {
        produktService.deleteProdukt(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}