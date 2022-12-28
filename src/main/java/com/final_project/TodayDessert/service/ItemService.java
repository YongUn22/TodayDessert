package com.final_project.TodayDessert.service;

import com.final_project.TodayDessert.dto.*;
import com.final_project.TodayDessert.entity.Item;
import com.final_project.TodayDessert.entity.ItemImg;
import com.final_project.TodayDessert.repository.ItemImgRepository;
import com.final_project.TodayDessert.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0) {
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();

    }

    @Transactional(readOnly = true)
    public void deleteItem(Long itemId){
        itemImgRepository.deleteImg(itemId);
        itemRepository.deleteById(itemId);


    }

    @Transactional(readOnly = true)
    public Page<Item> getStoreItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getStoreItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

//    2022-11-27 추가
    @Transactional(readOnly = true)
    public Page<MainItemDto> getButterbarPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getButterbarPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getCannelePage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getCannelePage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getCookiesPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getCookiesPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getFinancierPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getFinancierPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMacaronPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMacaronPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getSconePage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getSconePage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getVeganPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getVeganPage(itemSearchDto, pageable);
    }

}
