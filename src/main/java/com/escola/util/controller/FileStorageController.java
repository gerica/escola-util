package com.escola.util.controller;

import com.escola.util.model.request.FileRequest;
import com.escola.util.service.FileStorageService;
import graphql.GraphQLException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileStorageController {

    FileStorageService storageService;

    @MutationMapping
    public Mono<String> sendFileUpload(@Argument FileRequest request) {
        return storageService.saveFile(request)
                .onErrorResume(e -> Mono.error(new GraphQLException("Falha realizar operação: " + e.getMessage())));
    }

    @QueryMapping
    public Mono<String> fetchFileByUUID(@Argument String uuid) {
        return storageService.getFileAsBase64(uuid)
                .onErrorResume(e -> Mono.error(new GraphQLException("Falha realizar operação: " + e.getMessage())));
    }

    @MutationMapping
    public Mono<Boolean> deleteFileByUUID(@Argument String uuid) {
        return storageService.deleteFile(uuid)
//                .map(wasDeleted -> {
//                    if (wasDeleted) {
//                        return "Arquivo deletado com sucesso.";
//                    } else {
//                        throw new RuntimeException("Arquivo não encontrado.");
//                    }
//                })
                .onErrorResume(RuntimeException.class, e ->
                        Mono.error(new GraphQLException("Falha ao deletar arquivo: " + e.getMessage())));
    }
}
