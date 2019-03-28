package com.epam.java.currecyexchanger.io.api;

import com.epam.java.currecyexchanger.model.entity.Deal;

import java.util.List;

public interface IDealReader {

    List<Deal> dealsReader(String uri);
}
