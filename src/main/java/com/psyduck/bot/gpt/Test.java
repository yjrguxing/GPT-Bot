package com.psyduck.bot.gpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.theokanning.openai.service.OpenAiService.*;

public class Test {
    public static void main(String[] args) {
        //            SpringApplication.run(GPTBotApplication.class, args);
        String token = "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";
        ObjectMapper mapper = defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 20081));
        OkHttpClient client = defaultClient(token, Duration.ofSeconds(10L))
                .newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        OpenAiService service = new OpenAiService(api);


//            System.out.println("\nCreating completion...");
//            CompletionRequest completionRequest = CompletionRequest.builder()
//                    .model("ada")
//                    .prompt("Somebody once told me the world is gonna roll me")
//                    .echo(true)
//                    .user("testing")
//                    .n(3)
//                    .build();
//            service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
//
//            System.out.println("\nCreating Image...");
//            CreateImageRequest request = CreateImageRequest.builder()
//                    .prompt("A cow breakdancing with a turtle")
//                    .build();
//
//            System.out.println("\nImage is located at:");
//            System.out.println(service.createImage(request).getData().get(0).getUrl());

        System.out.println("Streaming chat completion...");
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are a dog and will speak as such.");
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .logitBias(new HashMap<>())
                .build();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(System.out::println);

        service.shutdownExecutor();
    }
}
