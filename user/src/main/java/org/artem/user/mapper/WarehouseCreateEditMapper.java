package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.dto.nova.post.PostOfficeDto;
import org.artem.user.dto.nova.post.GetWarehousesRequestDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseCreateEditMapper implements Mapper<PostOfficeDto, GetWarehousesRequestDto> {


    @Override
    public GetWarehousesRequestDto map(PostOfficeDto fromObject, GetWarehousesRequestDto toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public GetWarehousesRequestDto map(PostOfficeDto object) {
        GetWarehousesRequestDto GetWarehousesRequestDto = new GetWarehousesRequestDto();
        copy(object, GetWarehousesRequestDto);

        return GetWarehousesRequestDto;
    }

    private void copy(PostOfficeDto object, GetWarehousesRequestDto requestDto) {
        requestDto.setRef(object.getRef());
        requestDto.setFindByString(object.getFindByString());
        requestDto.setCityRef(object.getCityRef());
        requestDto.setCategoryOfWarehouse(object.getCategoryOfWarehouse());

        if (requestDto.getCategoryOfWarehouse() == null) {
            requestDto.setCategoryOfWarehouse("Warehouse");
        }
    }
}