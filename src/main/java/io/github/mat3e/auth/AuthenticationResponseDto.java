package io.github.mat3e.auth;

class AuthenticationResponseDto {
    private final String token;

    AuthenticationResponseDto(String token) {
        this.token = token;
    }

    /**
     * musi być publiczny
     *
     * Domyślna konfiguracja object mappera działą tak, że korzysta tylko i wyłącznie
     * z publicznych getterów.
     *
     */
    public String getToken() {
        return token;
    }
}
