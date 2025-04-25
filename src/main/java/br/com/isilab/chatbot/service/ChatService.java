package br.com.isilab.chatbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.isilab.chatbot.dto.AnswerDTO;
import br.com.isilab.chatbot.dto.PromptDTO;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.http.client.spring.restclient.SpringRestClient;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel.OpenAiChatModelBuilder;
import dev.langchain4j.model.openai.OpenAiImageModel.OpenAiImageModelBuilder;

@Service
public class ChatService {

	@Value("${OPENAI_KEY}")
	private String openAIKey;

	private ChatLanguageModel languageModel;

	public ChatService() {
		
		languageModel = new OpenAiChatModelBuilder().apiKey(openAIKey).modelName("o4-mini")
				.httpClientBuilder(SpringRestClient.builder()).build();
	}

	public AnswerDTO chatWithOpenAI(PromptDTO dto) {
		List<ChatMessage> messages = List.of(SystemMessage.from("Você deve sempre responder em formato Markdown."),
				UserMessage.from(dto.message()));

		String resposta = languageModel.chat(messages).toString();
		return new AnswerDTO(resposta);

	}
	
	public AnswerDTO generateImage(PromptDTO dto) throws Exception {
		ImageModel imageModel = new OpenAiImageModelBuilder()
									   .apiKey(openAIKey)
									   .modelName("dall-e-3")
									   .httpClientBuilder(SpringRestClient.builder())
									   .build();
		return new AnswerDTO(imageModel.generate(dto.message()).content().url().toURL().toString());
	}

}
