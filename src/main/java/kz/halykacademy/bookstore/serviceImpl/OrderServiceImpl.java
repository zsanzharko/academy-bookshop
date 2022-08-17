package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Order;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.OrderEntity;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.exceptions.businessExceptions.CostInvalidException;
import kz.halykacademy.bookstore.exceptions.businessExceptions.UserInvalidException;
import kz.halykacademy.bookstore.repository.OrderRepository;
import kz.halykacademy.bookstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends BaseService<Order, OrderEntity, OrderRepository> implements OrderService {

    private final UserServiceImpl userService;
    private final BookServiceImpl bookService;

    /**
     * @param repository repository for provider
     */
    @Autowired
    public OrderServiceImpl(OrderRepository repository, UserServiceImpl userService, BookServiceImpl bookService) {
        super(OrderEntity.class, Order.class, repository);
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public Order create(Order order) throws BusinessException {
        var orderEntity = convertToEntity(order);
        orderIsCorrect(orderEntity);
        return save(orderEntity);
    }

    @Override
    public List<Order> read() {
        return getAll();
    }

    @Override
    public Order read(Long id) throws BusinessException {
        return findById(id);
    }

    @Override
    public Order update(Order order) throws BusinessException {
        var orderEntity = convertToEntity(order);
        orderIsCorrect(orderEntity);
        return update(orderEntity);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        removeById(id);
    }

    @Override
    protected Order convertToDto(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .user(orderEntity.getUser().getId())
                .status(orderEntity.getStatus())
                .time(orderEntity.getTime())
                .books(orderEntity.getBookEntityList().stream().map(BookEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    protected OrderEntity convertToEntity(Order order) throws NullPointerException, BusinessException {
        if (order == null) throw new NullPointerException("Order can not be null");
        if (order.getUser() == null)
            throw new BusinessException("In order user can not be null", HttpStatus.BAD_REQUEST);
        if (order.getBooks() == null)
            throw new BusinessException("In order books can not be null", HttpStatus.BAD_REQUEST);

        var user = userService.convertToEntity(userService.read(order.getUser()));

        var books = order.getBooks()
                .stream()
                .map(id -> {
                    try {
                        return bookService.read(id);
                    } catch (BusinessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(book -> {
                    try {
                        return bookService.convertToEntity(book);
                    } catch (BusinessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        return OrderEntity.builder()
                .id(order.getId())
                .user(user)
                .status(order.getStatus())
                .bookEntityList(books)
                .time(order.getTime())
                .build();

    }

    private void orderIsCorrect(OrderEntity order) throws CostInvalidException, UserInvalidException {
        var books = order.getBookEntityList();

        // valid user
        if (order.getUser().getRemoved() != null)
            throw new UserInvalidException("Access is denied. User is removed", HttpStatus.FORBIDDEN);

        // valid books
        //todo valid books

        // valid the cost
        BigDecimal costs = books.stream()
                .map(BookEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (costs.intValue() >= 10_000) throw new CostInvalidException(costs.intValue(), 10_000, HttpStatus.FORBIDDEN);

    }
}
