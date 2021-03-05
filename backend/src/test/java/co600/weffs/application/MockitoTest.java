package co600.weffs.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Used to remove boilerplate initMocks and RunWith annotations.
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

  @BeforeEach
  void mockitoTestSetUpAll() {
    MockitoAnnotations.initMocks(this);
  }

}
