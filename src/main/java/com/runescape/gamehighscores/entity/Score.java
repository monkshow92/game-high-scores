package com.runescape.gamehighscores.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer level;

    @NotNull
    private Long xp;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Score(Category category, Player player, Integer level, Long xp) {
        this.level = level;
        this.xp = xp;
        this.player = player;
        this.category = category;
    }

    public Score() {} // for JPA

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean belongsToOverallCategory() {
        return belongsToCategory("Overall");
    }

    public boolean belongsToCategory(String categoryName) {
        return category.getName().equalsIgnoreCase(categoryName);
    }

    public boolean belongsToSameCategory(Score score2) {
        return this.belongsToCategory(score2.getCategory().getName());
    }

}
