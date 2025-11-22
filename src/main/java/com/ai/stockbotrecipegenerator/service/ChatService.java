package com.ai.stockbotrecipegenerator.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // simple one-line call (if ChatModel.call(String) returns String in your version)
    public String getResponse(String prompt) {
        return chatModel.call(prompt);
    }

    // low-level call with options -> returns the ChatResponse and extract text with getText()
    public String getResponseOptions(String prompt) {
        ChatResponse response = chatModel.call(
                new Prompt(prompt,
                        OpenAiChatOptions.builder()
                                .model("gpt-4o")          // use model(...)
                                .temperature(0.4)        // use temperature(...)
                                .build()
                )
        );

        // extract the assistant text (Generation -> AssistantMessage -> getText())
        return response
                .getResult()        // Generation
                .getOutput()        // AssistantMessage
                .getText();         // the actual assistant text
    }
}
