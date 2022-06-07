package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.api.app.vo.AppIndexCarouselVO;
import com.tsunglin.tsunglin00.entity.Carousel;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;

import java.util.List;

public interface CarouselService {

    /**
     * 返回固定數量的輪播圖對象(首頁調用)
     *
     * @param number
     * @return
     */
    List<AppIndexCarouselVO> getCarouselsForIndex(int number);

    /**
     * 後台分頁
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Long[] ids);
}
