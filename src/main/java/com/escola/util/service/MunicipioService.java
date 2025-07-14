package com.escola.util.service;


import com.escola.util.model.entity.User;
import com.escola.util.model.response.MunicipioResponse;
import com.escola.util.security.BaseException;

import java.io.IOException;
import java.util.List;

public interface MunicipioService {

    List<MunicipioResponse> findAll() throws BaseException, IOException;

    MunicipioResponse findByID(String codigo, User user) throws BaseException, IOException;

}
