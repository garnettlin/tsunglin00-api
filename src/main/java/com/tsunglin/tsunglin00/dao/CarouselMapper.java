package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.Carousel;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarouselMapper {
    int deleteByPrimaryKey(Integer carouselId);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectByPrimaryKey(Integer carouselId);

    int updateByPrimaryKeySelective(Carousel record);

    int updateByPrimaryKey(Carousel record);
    //列表
    List<Carousel> findCarouselList(PageQueryUtil pageUtil);
    //總數
    int getTotalCarousels(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

    List<Carousel> findCarouselsByNum(@Param("number") int number);
}