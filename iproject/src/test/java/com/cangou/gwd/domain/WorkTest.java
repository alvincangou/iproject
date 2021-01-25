package com.cangou.gwd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cangou.gwd.web.rest.TestUtil;

public class WorkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Work.class);
        Work work1 = new Work();
        work1.setId(1L);
        Work work2 = new Work();
        work2.setId(work1.getId());
        assertThat(work1).isEqualTo(work2);
        work2.setId(2L);
        assertThat(work1).isNotEqualTo(work2);
        work1.setId(null);
        assertThat(work1).isNotEqualTo(work2);
    }
}
