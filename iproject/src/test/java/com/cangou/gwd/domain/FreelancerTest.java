package com.cangou.gwd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cangou.gwd.web.rest.TestUtil;

public class FreelancerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Freelancer.class);
        Freelancer freelancer1 = new Freelancer();
        freelancer1.setId(1L);
        Freelancer freelancer2 = new Freelancer();
        freelancer2.setId(freelancer1.getId());
        assertThat(freelancer1).isEqualTo(freelancer2);
        freelancer2.setId(2L);
        assertThat(freelancer1).isNotEqualTo(freelancer2);
        freelancer1.setId(null);
        assertThat(freelancer1).isNotEqualTo(freelancer2);
    }
}
