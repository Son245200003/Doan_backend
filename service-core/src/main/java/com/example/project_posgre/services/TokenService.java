package com.example.project_posgre.services;

import com.example.project_posgre.models.Token;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {
    public long save(Token token);
    public String delete(Token token);
    Token findByPhonenumber(String phonenumber);
}
