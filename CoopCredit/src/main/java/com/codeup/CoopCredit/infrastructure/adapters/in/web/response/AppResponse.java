package com.codeup.CoopCredit.infrastructure.adapters.in.web.response;

public record AppResponse<T>(
        String status,  // "success" o "error"
        T data,
        Meta meta
) {

    // Constructor estático para respuestas exitosas sin metadatos
    public static <T> AppResponse<T> ok(T data) {
        return new AppResponse<>("success", data, null);
    }

    // Constructor estático para respuestas exitosas con metadatos
    public static <T> AppResponse<T> withMeta(T data, Meta meta) {
        return new AppResponse<>("success", data, meta);
    }

    // Constructor estático para respuestas de error
    public static <T> AppResponse<T> error(String message, String traceId) {
        return new AppResponse<>("error", null, new Meta(message, traceId, "1.0.0", null));
    }

    // Subrecord Meta: información adicional
    public record Meta(
            String message,
            String traceId,
            String version,
            Pagination page // opcional
    ) {}

    // Subrecord Pagination: para respuestas paginadas
    public record Pagination(
            int page,
            int size,
            long totalElements,
            int totalPages
    ) {}
}
