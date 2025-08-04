package com.escola.util.service;

import com.escola.util.model.request.FileRequest;
import reactor.core.publisher.Mono;

public interface FileStorageService {

    /**
     * Salva um arquivo a partir de um conteúdo Base64.
     *
     * @param request O objeto FileRequest contendo o conteúdo Base64.
     * @return Um Mono com o caminho do arquivo salvo.
     */
    Mono<String> saveFile(FileRequest request);

    /**
     * Encontra um arquivo pelo seu nome único, converte-o para Base64 e retorna a string.
     *
     * @param uniqueFileName O nome único do arquivo.
     * @return Um Mono com a string Base64 do arquivo.
     */
    Mono<String> getFileAsBase64(String uniqueFileName);

    /**
     * Exclui um arquivo do sistema de arquivos.
     *
     * @param uniqueFileName O nome único do arquivo a ser excluído.
     * @return Um Mono que emite 'true' se o arquivo foi excluído com sucesso, 'false' se não foi encontrado.
     */
    Mono<Boolean> deleteFile(String uniqueFileName);
}
