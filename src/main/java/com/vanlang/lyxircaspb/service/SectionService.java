package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.Section;

import java.util.List;

public interface SectionService {
    List<Section> getAllSection();
    void deleteSection(Long sectionId);
    Section updatedSection(Long sectionId, Section section);
    public List<Section> getSectionsByCategoryIdAndLevel(Long categoryId, int level);
}
