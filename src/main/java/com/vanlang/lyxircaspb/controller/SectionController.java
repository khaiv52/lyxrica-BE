package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.model.Section;
import com.vanlang.lyxircaspb.service.SectionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {
    @Autowired
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Section>> getAllSections() {
        List<Section> sections = sectionService.getAllSection();
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

//    @PutMapping("/edit/{id}")
//    public ResponseEntity<Section> editSection(@PathVariable Long id, @RequestBody Section section) {
//        Section updatedSection = sectionService.updatedSection(id, section);
//        if(updatedSection != null) {
//            return new ResponseEntity<>(updatedSection, HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            sectionService.deleteSection(id);
            return ResponseEntity.ok("Section deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
