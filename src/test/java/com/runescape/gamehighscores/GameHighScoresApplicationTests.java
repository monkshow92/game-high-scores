package com.runescape.gamehighscores;

import com.runescape.gamehighscores.entity.Category;
import com.runescape.gamehighscores.repository.CategoryDao;
import com.runescape.gamehighscores.repository.PlayerDao;
import com.runescape.gamehighscores.repository.ScoreDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameHighScoresApplicationTests {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private ScoreDao scoreDao;

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
        assertThat(categoryDao).isNotNull();
        assertThat(playerDao).isNotNull();
        assertThat(scoreDao).isNotNull();
	}

	@Test
    public void dataShouldBeLoaded() {
	    // 6 Categories
	    assertThat(categoryDao.count()).isEqualTo(6L);
	    // 10 Players
        assertThat(playerDao.count()).isEqualTo(10L);
        // 6 * 10 = 60 Scores
        assertThat(scoreDao.count()).isEqualTo(60L);
    }

    @Test
    public void overallCategoryObtainedByGet() {
	    Category overallCategory = restTemplate.getForObject("/categories/1", Category.class);
	    assertThat(overallCategory.getName()).isEqualToIgnoringCase("Overall");
    }

}
