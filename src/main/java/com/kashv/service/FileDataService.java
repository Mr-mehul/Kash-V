package com.kashv.service;
import com.kashv.domain.CreditDecision;
import com.kashv.domain.Documents;
import com.kashv.domain.FileData;
import com.kashv.domain.Profile;
import com.kashv.model.FileDataDTO;
import com.kashv.repos.CreditDecisionRepository;
import com.kashv.repos.DocumentsRepository;
import com.kashv.repos.FileDataRepository;
import com.kashv.repos.ProfileRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class FileDataService {

    private final FileDataRepository fileDataRepository;
    private final ProfileRepository profileRepository;
    private final DocumentsRepository documentsRepository;
    private final CreditDecisionRepository creditDecisionRepository;

    public FileDataService(final FileDataRepository fileDataRepository,
            final ProfileRepository profileRepository,
            final DocumentsRepository documentsRepository,
            final CreditDecisionRepository creditDecisionRepository) {
        this.fileDataRepository = fileDataRepository;
        this.profileRepository = profileRepository;
        this.documentsRepository = documentsRepository;
        this.creditDecisionRepository = creditDecisionRepository;
    }

    public List<FileDataDTO> findAll() {
        return fileDataRepository.findAll()
                .stream()
                .map(fileData -> mapToDTO(fileData, new FileDataDTO()))
                .collect(Collectors.toList());
    }

    public FileDataDTO get(final Long id) {
        return fileDataRepository.findById(id)
                .map(fileData -> mapToDTO(fileData, new FileDataDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final FileDataDTO fileDataDTO) {
        final FileData fileData = new FileData();
        mapToEntity(fileDataDTO, fileData);
        return fileDataRepository.save(fileData).getId();
    }

    //Mehul Has Changed This method
    public FileDataDTO update(final Long id, final FileDataDTO fileDataDTO) {
		final FileData fileData = fileDataRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(fileDataDTO, fileData);
		fileDataRepository.save(fileData);
		return fileDataDTO;
	}

    public void delete(final Long id) {
        fileDataRepository.deleteById(id);
    }

    //Mehul Has changed this method
    public FileDataDTO mapToDTO(final FileData fileData, final FileDataDTO fileDataDTO) {
        fileDataDTO.setId(fileData.getId());
        fileDataDTO.setOrignalFileName(fileData.getOrignalFileName());
        fileDataDTO.setKey(fileData.getKey());
        fileDataDTO.setPublicUrl(fileData.getPublicUrl());
        fileDataDTO.setProfileId(fileData.getProfileId() == null ? null : fileData.getProfileId().getId());
        fileDataDTO.setAadharCard(fileData.getAadharCard() == null ? null : fileData.getAadharCard().getDocId());
        fileDataDTO.setPanCard(fileData.getPanCard() == null ? null : fileData.getPanCard().getCreditId());
        fileDataDTO.setDrivingLicense(fileData.getDrivingLicense() == null ? null : fileData.getDrivingLicense().getDocId());
        fileDataDTO.setCurrentAddressProof(fileData.getCurrentAddressProof() == null ? null : fileData.getCurrentAddressProof().getDocId());
        fileDataDTO.setPermanentAddressProof(fileData.getPermanentAddressProof() == null ? null : fileData.getPermanentAddressProof().getDocId());
        fileDataDTO.setBankStatement(fileData.getBankStatement() == null ? null : fileData.getBankStatement().getDocId());
        return fileDataDTO;
    }

    private FileData mapToEntity(final FileDataDTO fileDataDTO, final FileData fileData) {
        fileData.setOrignalFileName(fileDataDTO.getOrignalFileName());
        fileData.setKey(fileDataDTO.getKey());
        fileData.setPublicUrl(fileDataDTO.getPublicUrl());
        if (fileDataDTO.getProfileId() != null && 
                (fileData.getProfileId() == null || !fileData.getProfileId().getId().equals(fileDataDTO.getProfileId()))) {
            final Profile profileId = profileRepository.findById(fileDataDTO.getProfileId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "profileId not found"));
            fileData.setProfileId(profileId);
        }
        if (fileDataDTO.getAadharCard() != null && 
                (fileData.getAadharCard() == null || !fileData.getAadharCard().getDocId().equals(fileDataDTO.getAadharCard()))) {
            final Documents aadharCard = documentsRepository.findById(fileDataDTO.getAadharCard())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "aadharCard not found"));
            fileData.setAadharCard(aadharCard);
        }
        if (fileDataDTO.getPanCard() != null && 
                (fileData.getPanCard() == null || !fileData.getPanCard().getCreditId().equals(fileDataDTO.getPanCard()))) {
            final CreditDecision panCard = creditDecisionRepository.findById(fileDataDTO.getPanCard())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "panCard not found"));
            fileData.setPanCard(panCard);
        }
        if (fileDataDTO.getDrivingLicense() != null && 
                (fileData.getDrivingLicense() == null || !fileData.getDrivingLicense().getDocId().equals(fileDataDTO.getDrivingLicense()))) {
            final Documents drivingLicense = documentsRepository.findById(fileDataDTO.getDrivingLicense())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "drivingLicense not found"));
            fileData.setDrivingLicense(drivingLicense);
        }
        if (fileDataDTO.getCurrentAddressProof() != null && 
                (fileData.getCurrentAddressProof() == null || !fileData.getCurrentAddressProof().getDocId().equals(fileDataDTO.getCurrentAddressProof()))) {
            final Documents currentAddressProof = documentsRepository.findById(fileDataDTO.getCurrentAddressProof())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "currentAddressProof not found"));
            fileData.setCurrentAddressProof(currentAddressProof);
        }
        if (fileDataDTO.getPermanentAddressProof() != null && 
                (fileData.getPermanentAddressProof() == null || !fileData.getPermanentAddressProof().getDocId().equals(fileDataDTO.getPermanentAddressProof()))) {
            final Documents permanentAddressProof = documentsRepository.findById(fileDataDTO.getPermanentAddressProof())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "permanentAddressProof not found"));
            fileData.setPermanentAddressProof(permanentAddressProof);
        }
        if (fileDataDTO.getBankStatement() != null && 
                (fileData.getBankStatement() == null || !fileData.getBankStatement().getDocId().equals(fileDataDTO.getBankStatement()))) {
            final Documents bankStatement = documentsRepository.findById(fileDataDTO.getBankStatement())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bankStatement not found"));
            fileData.setBankStatement(bankStatement);
        }
        return fileData;
    }

}