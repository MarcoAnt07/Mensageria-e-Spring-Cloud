package io.gtihub.marcoant07.application.ex;

public class ErroComunicacaoMicroservicesException extends Exception {

    private Integer status;

    public ErroComunicacaoMicroservicesException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
