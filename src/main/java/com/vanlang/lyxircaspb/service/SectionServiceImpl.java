package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.Section;
import com.vanlang.lyxircaspb.repository.CategoryItemRepository;
import com.vanlang.lyxircaspb.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final CategoryItemRepository categoryItemRepository;

    public SectionServiceImpl(SectionRepository sectionRepository, SectionRepository sectionRepository1, CategoryItemRepository categoryItemRepository) {
        this.sectionRepository = sectionRepository1;
        this.categoryItemRepository = categoryItemRepository;
    }

    @Override
    public List<Section> getAllSection() {
        return sectionRepository.findAll();
    }

    @Override
    public void deleteSection(Long sectionId) {
        Optional<Section> sectionOpt = sectionRepository.findById(sectionId);
        if (sectionOpt.isEmpty()) {
            throw new EntityNotFoundException("Section with ID " + sectionId + " not found");
        }

        Section section = sectionOpt.get();
        // Xóa tất cả CategoryItems thuộc Section
        section.getCategoryItems().forEach(categoryItem -> categoryItemRepository.deleteById(categoryItem.getId()));

        // Xóa Section
        sectionRepository.deleteById(sectionId);
    }


    @Override
    public Section updatedSection(Long sectionId, Section section) {
        if(sectionRepository.existsById(sectionId)) {
            section.setId(sectionId);
            return sectionRepository.save(section);
        } else {
            throw new EntityNotFoundException("Section with ID " + sectionId + " not found");
        }
    }

    @Override
    public List<Section> getSectionsByCategoryIdAndLevel(Long categoryId, int level) {
        return sectionRepository.findSectionsByParentCategoryIdAndLevel(categoryId, level);
    }
}
