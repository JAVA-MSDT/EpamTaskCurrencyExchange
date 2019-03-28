package com.epam.java.currecyexchanger.io.api;

import com.epam.java.currecyexchanger.model.entity.Deal;

import java.util.List;

public interface IDealWriter {
    void dealsWriter(String uri, List<Deal> dealList);
}
