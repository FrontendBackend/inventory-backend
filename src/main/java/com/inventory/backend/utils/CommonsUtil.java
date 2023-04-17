package com.inventory.backend.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonsUtil {
    
    public static boolean isNullOrZeroLong(Long valor) {

		boolean rpta = false;

		if (valor != null && valor != 0L) {
			rpta = true;
		}

		return rpta;
	}
}
