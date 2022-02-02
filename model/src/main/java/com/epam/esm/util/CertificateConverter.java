package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Component;

@Component
public class CertificateConverter {

    public CertificateDto convertCertificateToCertificateDto(Certificate certificate) {
        return CertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .duration(certificate.getDuration())
                .createDate(certificate.getCreateDate())
                .lastUpdateDate(certificate.getLastUpdateDate())
                .build();
    }

    public Certificate convertCertificateDtoToCertificate(CertificateDto certificateDto) {
        return Certificate.builder()
                .id(certificateDto.getId())
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(certificateDto.getPrice())
                .duration(certificateDto.getDuration())
                .createDate(certificateDto.getCreateDate())
                .lastUpdateDate(certificateDto.getLastUpdateDate())
                .build();
    }
}
