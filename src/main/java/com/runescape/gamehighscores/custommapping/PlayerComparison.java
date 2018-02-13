package com.runescape.gamehighscores.custommapping;

import com.runescape.gamehighscores.entity.Score;

public class PlayerComparison {

    private String categoryName;
    private Integer level1;
    private Long xp1;
    private Integer level2;
    private Long xp2;
    private String comparison;

    public PlayerComparison(Score score1, Score score2) {
        this.categoryName = score1.getCategory().getName();
        this.level1 = score1.getLevel();
        this.xp1 = score1.getXp();
        this.level2 = score2.getLevel();
        this.xp2 = score2.getXp();
        calculateComparison();
    }

    public void calculateComparison() {
        if (level1.compareTo(level2) == 0) {
            if (xp1.compareTo(xp2) == 0) {
                this.comparison = "eq";
            } else if (xp1.compareTo(xp2) > 0) {
                this.comparison = "gt";
            } else {
                this.comparison = "lt";
            }
        } else if (level1.compareTo(level2) > 0) {
            this.comparison = "gt";
        } else {
            this.comparison = "lt";
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getLevel1() {
        return level1;
    }

    public void setLevel1(Integer level1) {
        this.level1 = level1;
    }

    public Long getXp1() {
        return xp1;
    }

    public void setXp1(Long xp1) {
        this.xp1 = xp1;
    }

    public Integer getLevel2() {
        return level2;
    }

    public void setLevel2(Integer level2) {
        this.level2 = level2;
    }

    public Long getXp2() {
        return xp2;
    }

    public void setXp2(Long xp2) {
        this.xp2 = xp2;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

}
