package com.LGBank.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;

@ConfigurationProperties(prefix = "accounts")
public record CardInforContactDto(String message, HashMap<String, String> contactDetails, List<String> onCallSupport )  {
}
