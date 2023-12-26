package br.com.yanvelasco.screemmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}