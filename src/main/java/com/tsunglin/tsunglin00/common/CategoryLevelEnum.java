package com.tsunglin.tsunglin00.common;

public enum CategoryLevelEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_ONE(1, "一級分類"),
    LEVEL_TWO(2, "二級分類"),
    LEVEL_THREE(3, "三級分類");

    private int level;

    private String name;

    CategoryLevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public static CategoryLevelEnum getNewBeeMallOrderStatusEnumByLevel(int level) {
        for (CategoryLevelEnum categoryLevelEnum : CategoryLevelEnum.values()) {
            if (categoryLevelEnum.getLevel() == level) {
                return categoryLevelEnum;
            }
        }
        return DEFAULT;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
