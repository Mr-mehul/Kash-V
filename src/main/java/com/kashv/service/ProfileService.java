package com.kashv.service;

import com.kashv.domain.Profile;
import com.kashv.domain.User;
import com.kashv.model.FileDataDTO;
import com.kashv.model.ProfileDTO;
import com.kashv.repos.ProfileRepository;
import com.kashv.repos.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final UserRepository userRepository;

	private final FileDataService fileDataService;

	public ProfileService(final ProfileRepository profileRepository, final UserRepository userRepository,
			final FileDataService fileDataService) {
		this.profileRepository = profileRepository;
		this.userRepository = userRepository;
		this.fileDataService = fileDataService;
	}

	public List<ProfileDTO> findAll() {
		return profileRepository.findAll().stream().map(profile -> mapToDTO(profile, new ProfileDTO()))
				.collect(Collectors.toList());
	}

	public ProfileDTO get(final Long id) {
		return profileRepository.findById(id).map(profile -> mapToDTO(profile, new ProfileDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public ProfileDTO create(ProfileDTO profileDTO) {
		final Profile profile = new Profile();
		mapToEntity(profileDTO, profile);
		profileDTO.setId(profileRepository.save(profile).getId());
		return profileDTO;
	}

	public void update(final Long id, ProfileDTO profileDTO) {
		final Profile profile = profileRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(profileDTO, profile);
		profileRepository.save(profile);
	}

	public void delete(final Long id) {
		profileRepository.deleteById(id);
	}

	private Profile mapToEntity(final ProfileDTO profileDTO, final Profile profile) {
		profile.setDob(profileDTO.getDob());
		profile.setGender(profileDTO.getGender());
		profile.setAddress(profileDTO.getAddress());
		profile.setEmail(profileDTO.getEmail());
		profile.setFullName(profileDTO.getFullName());
		profile.setPincode(profileDTO.getPincode());
		if (profileDTO.getUserId() != null
				&& (profile.getUserId() == null || !profile.getUserId().getId().equals(profileDTO.getUserId()))) {
			final User userId = userRepository.findById(profileDTO.getUserId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userId not found"));
			profile.setUserId(userId);
		}
		return profile;
	}

	// mehul code

	public boolean existByUserId(final Long userId) {
		Optional<Profile> data = profileRepository.findByUserId_Id(userId);
		return data.isEmpty();
	}

	public ProfileDTO getByUserId(final Long id) {
		return profileRepository.findByUserId_Id(id).map(profile -> mapToDTO(profile, new ProfileDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private ProfileDTO mapToDTO(final Profile profile, final ProfileDTO profileDTO) {
		profileDTO.setId(profile.getId());
		profileDTO.setDob(profile.getDob());
		profileDTO.setGender(profile.getGender());
		profileDTO.setAddress(profile.getAddress());
		profileDTO.setPincode(profile.getPincode());
		profileDTO.setEmail(profile.getEmail());
		profileDTO.setFullName(profile.getFullName());
		profileDTO.setFiles(profile.getProfileIdFileDatas() == null ? null
				: profile.getProfileIdFileDatas().stream()
						.map(fileData -> fileDataService.mapToDTO(fileData, new FileDataDTO()))
						.collect(Collectors.toSet()));
		profileDTO.setUserId(profile.getUserId() == null ? null : profile.getUserId().getId());
		return profileDTO;
	}
}