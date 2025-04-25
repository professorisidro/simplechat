package br.com.isilab.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.isilab.chatbot.dto.AnswerDTO;
import br.com.isilab.chatbot.dto.PromptDTO;
import br.com.isilab.chatbot.service.ChatService;

@RestController
public class ChatController {

	@Value("${isiflix.message}")
	private String msg;

	@Autowired
	private ChatService service;

	@GetMapping("/hello")
	public String sayHello() {
		return msg;
	}

	@PostMapping("/chat")
	public AnswerDTO chatWithOpenAI(@RequestBody PromptDTO dto) {
		return service.chatWithOpenAI(dto);
	}

	@PostMapping("/image")
	public AnswerDTO generateImage(@RequestBody PromptDTO dto) {
		try {
			return service.generateImage(dto);
		} catch (Exception ex) {
			return new AnswerDTO(ex.getMessage());
		}
	}
}
