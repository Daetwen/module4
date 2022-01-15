package com.epam.esm.util;

import com.epam.esm.builder.CertificateBuilder;
import com.epam.esm.builder.CertificateDtoBuilder;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Component;

@Component
public class CertificateConverter {

    public CertificateDto convertCertificateToCertificateDto(Certificate certificate) {
        CertificateDtoBuilder certificateDtoBuilder = new CertificateDtoBuilder();
        certificateDtoBuilder.setId(certificate.getId());
        certificateDtoBuilder.setName(certificate.getName());
        certificateDtoBuilder.setDescription(certificate.getDescription());
        certificateDtoBuilder.setPrice(certificate.getPrice());
        certificateDtoBuilder.setDuration(certificate.getDuration());
        certificateDtoBuilder.setCreateDate(certificate.getCreateDate());
        certificateDtoBuilder.setLastUpdateDate(certificate.getLastUpdateDate());
        return certificateDtoBuilder.build();
    }

    public Certificate convertCertificateDtoToCertificate(CertificateDto certificateDto) {
        CertificateBuilder certificateBuilder = new CertificateBuilder();
        certificateBuilder.setId(certificateDto.getId());
        certificateBuilder.setName(certificateDto.getName());
        certificateBuilder.setDescription(certificateDto.getDescription());
        certificateBuilder.setPrice(certificateDto.getPrice());
        certificateBuilder.setDuration(certificateDto.getDuration());
        certificateBuilder.setCreateDate(certificateDto.getCreateDate());
        certificateBuilder.setLastUpdateDate(certificateDto.getLastUpdateDate());
        return certificateBuilder.build();
    }
}
