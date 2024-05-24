package woodo.practice.organizationservice.service.impl;

import lombok.AllArgsConstructor;
import woodo.practice.organizationservice.repository.OrganizationRepository;
import woodo.practice.organizationservice.dto.OrganizationDto;
import woodo.practice.organizationservice.entity.Organization;
import woodo.practice.organizationservice.mapper.OrganizationMapper;
import woodo.practice.organizationservice.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        // convert OrganizationDto into Organization jpa entity
        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);

        Organization savedOrganization = organizationRepository.save(organization);

        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}
