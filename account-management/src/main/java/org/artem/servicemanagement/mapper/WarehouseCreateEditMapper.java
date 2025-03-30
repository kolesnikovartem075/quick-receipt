package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.nova.post.GetWarehousesRequestDto;
import org.artem.servicemanagement.dto.nova.post.PostOfficeRequestDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseCreateEditMapper implements Mapper<PostOfficeRequestDto, GetWarehousesRequestDto> {


    @Override
    public GetWarehousesRequestDto map(PostOfficeRequestDto fromObject, GetWarehousesRequestDto toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public GetWarehousesRequestDto map(PostOfficeRequestDto object) {
        GetWarehousesRequestDto GetWarehousesRequestDto = new GetWarehousesRequestDto();
        copy(object, GetWarehousesRequestDto);

        return GetWarehousesRequestDto;
    }

    private void copy(PostOfficeRequestDto object, GetWarehousesRequestDto requestDto) {
        requestDto.setRef(object.getRef());
        requestDto.setFindByString(object.getFindByString());
        requestDto.setCityRef(object.getCityRef());
        requestDto.setCategoryOfWarehouse(object.getCategoryOfWarehouse());

        if (requestDto.getCategoryOfWarehouse() == null) {
            requestDto.setCategoryOfWarehouse("Warehouse");
        }
    }
}