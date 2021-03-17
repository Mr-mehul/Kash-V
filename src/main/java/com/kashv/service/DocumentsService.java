package com.kashv.service;

import com.kashv.domain.Documents;
import com.kashv.domain.User;
import com.kashv.model.DocumentsDTO;
import com.kashv.repos.DocumentsRepository;
import com.kashv.repos.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class DocumentsService {

    private final DocumentsRepository documentsRepository;
    private final UserRepository userRepository;

    public DocumentsService(final DocumentsRepository documentsRepository,
            final UserRepository userRepository) {
        this.documentsRepository = documentsRepository;
        this.userRepository = userRepository;
    }

    public List<DocumentsDTO> findAll() {
        return documentsRepository.findAll()
                .stream()
                .map(documents -> mapToDTO(documents, new DocumentsDTO()))
                .collect(Collectors.toList());
    }

    public DocumentsDTO get(final Long docId) {
        return documentsRepository.findById(docId)
                .map(documents -> mapToDTO(documents, new DocumentsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final DocumentsDTO documentsDTO) {
        final Documents documents = new Documents();
        mapToEntity(documentsDTO, documents);
        return documentsRepository.save(documents).getDocId();
    }

    public void update(final Long docId, final DocumentsDTO documentsDTO) {
        final Documents documents = documentsRepository.findById(docId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(documentsDTO, documents);
        documentsRepository.save(documents);
    }

    public void delete(final Long docId) {
        documentsRepository.deleteById(docId);
    }

    private DocumentsDTO mapToDTO(final Documents documents, final DocumentsDTO documentsDTO) {
        documentsDTO.setDocId(documents.getDocId());
        documentsDTO.setDocUserId(documents.getDocUserId() == null ? null : documents.getDocUserId().getId());
        return documentsDTO;
    }

    private Documents mapToEntity(final DocumentsDTO documentsDTO, final Documents documents) {
        if (documentsDTO.getDocUserId() != null && 
                (documents.getDocUserId() == null || !documents.getDocUserId().getId().equals(documentsDTO.getDocUserId()))) {
            final User docUserId = userRepository.findById(documentsDTO.getDocUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "docUserId not found"));
            documents.setDocUserId(docUserId);
        }
        return documents;
    }

}
 