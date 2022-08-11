package kz.halykacademy.bookstore.exceptions.businessExceptions;

import lombok.Getter;

@Getter
public class CostInvalidException extends BusinessException {

    private final String message;
    private final Integer cost;
    private final Integer limit;

    public CostInvalidException(Integer cost, Integer limit) {
        super(String.format(
                "Access is closed due to exceeding the amount of [%s] on your order. Maximum value [%s]",
                cost, limit));
        this.cost = cost;
        this.limit = limit;

        this.message = String.format(
                "Access is closed due to exceeding the amount of [%s] on your order. Maximum value [%s]",
                cost, limit);
    }
}
