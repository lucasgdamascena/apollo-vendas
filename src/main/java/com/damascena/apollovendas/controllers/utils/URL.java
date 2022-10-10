package com.damascena.apollovendas.controllers.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class URL {

    public static String decodificarParam(String param) {
        try {
            return URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Long> decodificarLongList(String params) {
        String[] vet = params.split(",");

        List<Long> list = new ArrayList<>();

        for (int i = 0; i < vet.length; i++) {

            try {
                list.add(Long.parseLong(vet[i]));
            } catch (NumberFormatException numberFormatException) {
                list.add(0L);
            }
        }

        return list;
    }
}