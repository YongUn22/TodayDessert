package com.final_project.TodayDessert.repository;

import com.final_project.TodayDessert.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);

    @Modifying
    @Transactional
    @Query("delete from ItemImg"+" where itemId = :itemId")
    void deleteImg(@Param("itemId") Long itemId);
}
