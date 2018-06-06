package guru.springframework.services;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractControllerTest {
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
