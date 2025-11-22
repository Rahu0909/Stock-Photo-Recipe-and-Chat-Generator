package com.ai.stockbotrecipegenerator.service;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final OpenAiImageModel openAiImageModel;

    public ImageService(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    public ImageResponse generateImage(String prompt,
                                       String quality,
                                       int n,
                                       // number of images
                                       int width,
                                       int height) {

        return openAiImageModel.call(
                new ImagePrompt(
                        prompt,
                        OpenAiImageOptions.builder()
                                .model("dall-e-3")   // or "dall-e-2" / your preferred model
                                .quality(quality)    // e.g., "hd" or "standard"
                                .N(n)                // <-- Capital N, not 'n'
                                .width(width)        // width in pixels (256/512/1024)
                                .height(height)      // height in pixels (256/512/1024)
                                .build()
                )
        );
    }
}
