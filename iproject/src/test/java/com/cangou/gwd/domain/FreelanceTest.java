package com.cangou.gwd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cangou.gwd.web.rest.TestUtil;

public class FreelanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Freelance.class);
        Freelance freelance1 = new Freelance();
        freelance1.setId(1L);
        Freelance freelance2 = new Freelance();
        freelance2.setId(freelance1.getId());
        assertThat(freelance1).isEqualTo(freelance2);
        freelance2.setId(2L);
        assertThat(freelance1).isNotEqualTo(freelance2);
        freelance1.setId(null);
        assertThat(freelance1).isNotEqualTo(freelance2);
    }
}
