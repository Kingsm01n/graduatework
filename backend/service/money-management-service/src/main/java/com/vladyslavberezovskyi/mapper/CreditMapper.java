package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dao.entity.CreditEntity;
import com.vladyslavberezovskyi.dto.credit.CreateCreditRequest;
import com.vladyslavberezovskyi.dto.credit.CreditResponse;
import com.vladyslavberezovskyi.dto.credit.PatchCreditRequest;
import com.vladyslavberezovskyi.model.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)//TODO map ignores
public interface CreditMapper {
    CreditResponse modelToDto(Credit credit);

    default Page<CreditResponse> modelToDto(@Nullable Page<Credit> credits) {
        if (credits == null) {
            return null;
        }

        return credits.map(this::modelToDto);
    }

    Credit dtoToModel(CreateCreditRequest createCreditRequest);

    Credit dtoToModel(PatchCreditRequest patchCreditRequest);

    Credit entityToModel(CreditEntity creditEntity);

    default Page<Credit> entityToModel(@Nullable Page<CreditEntity> creditEntities) {
        if (creditEntities == null) {
            return null;
        }

        return creditEntities.map(this::entityToModel);
    }

    CreditEntity modelToEntity(Credit credit);

    void update(@MappingTarget CreditEntity creditEntity, Credit credit);
}
