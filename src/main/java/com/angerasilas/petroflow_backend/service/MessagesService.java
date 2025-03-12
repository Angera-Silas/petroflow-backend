package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.MessagesDto;

public interface MessagesService {
    MessagesDto createMessage(MessagesDto messagesDto);
    MessagesDto getMessageById(Long id);
    List<MessagesDto> getAllMessages();
    MessagesDto updateMessage(Long id, MessagesDto messagesDto);
    void deleteMessage(Long id);
}