package com.pix.poc.domain.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.vo.Document;

import java.util.List;

public interface AccountRepository {

    List<Account> getAccountsByDocument(Document document);
}
