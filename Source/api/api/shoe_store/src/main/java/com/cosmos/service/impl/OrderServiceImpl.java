package com.cosmos.service.impl;

import com.cosmos.entity.*;
import com.cosmos.entity.keyID.Order_Detail_Id;
import com.cosmos.model.OrderRequest;
import com.cosmos.model.OrderResponse;
import com.cosmos.model.ProductResponse;
import com.cosmos.repository.CartRepository;
import com.cosmos.repository.OrderDetailRepository;
import com.cosmos.repository.OrderRepository;
import com.cosmos.repository.ProductRepository;
import com.cosmos.security.service.UserPrinciple;
import com.cosmos.service.OrderService;
import com.cosmos.services.DynamicTemplatePersonalization;
import com.cosmos.services.EmailService;
import com.cosmos.services.config.SendGridProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${date.expirationms}")
    private Integer expirationms;
    private final SendGridProperties sendGridProperties;
    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public void CreateOrder(OrderRequest orderRequest, Customers customer) {
        List<Cart> listCartDetails = cartRepository.getAllByCustomer_Id(customer.getId());
        Orders orders = new Orders();
        orders.setCustomers(customer);
        Date now = new Date();
        orders.setNamecustomer(orderRequest.getNameCustomer());
        orders.setCreateday(now);
        orders.setAddress(orderRequest.getAddress());
        orders.setPhoneNumber(orderRequest.getPhoneNumber());
        orders.setIspaid(orderRequest.getIspaid());
        float totalprice = 0;
        for (Cart cart : listCartDetails) {
            Product product = productRepository.getById(cart.getId().getProductid());
            totalprice += product.getPrice() * cart.getNumber();
        }
        orders.setTotalprice(totalprice);
        orders.setStatus(1);

        Orders savedOrders = orderRepository.save(orders);
        for (Cart cart : listCartDetails) {
            Order_Detail detail = new Order_Detail();
            Order_Detail_Id idDetail = new Order_Detail_Id(savedOrders.getId(), cart.getProduct().getId());
            detail.setOrder_detail_id(idDetail);
            detail.setNumber(cart.getNumber());
            Product product = productRepository.getById(cart.getId().getProductid());
            detail.setProduct(product);
            detail.setOrder(orders);
            detail.setPrice(product.getPrice());
            orderDetailRepository.save(detail);
            cartRepository.deleteById(cart.getId());
        }
    }

    @Transactional
    @Override
    public OrderResponse addToOrder() {
        OrderResponse order = new OrderResponse();
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        order.setNameCustomer(userPrinciple.getCustomer().getName());
        order.setPhone(userPrinciple.getCustomer().getPhone());
        order.setAddress(userPrinciple.getCustomer().getAddress());

        List<Cart> cartList = cartRepository.getAllByCustomer_Id(userPrinciple.getCustomer().getId());
        float totalprice = 0;
        List<ProductResponse> tempList = new ArrayList<>();
        for (Cart cart : cartList) {
            ProductResponse temp = new ProductResponse();
            temp.setProductId(cart.getId().getProductid());
            temp.setNumber(cart.getNumber());
            temp.setPrice(cart.getProduct().getPrice());
            temp.setProductName(cart.getProduct().getName());
            temp.setDescription(cart.getProduct().getDescription());
            totalprice += cart.getProduct().getPrice() * cart.getNumber();
            tempList.add(temp);
        }
        order.setProduct(tempList);
        order.setTotalprice(totalprice);
        return order;
    }

    @Transactional
    @Override
    public void changeOrderStatus(Employee employee, Integer status, Integer orderId) {
        Orders orders = orderRepository.getById(orderId);
        List<Order_Detail> detailList = orderDetailRepository.getAllByOrderId(orderId);

        if (status == 2) {
            boolean flag = false;
            for (Order_Detail detail : detailList) {
                Product product = productRepository.getById(detail.getProduct().getId());
                int numberBefore = product.getNumber();
                if (numberBefore < detail.getNumber()) {
                    flag = false;
                } else {
                    int numberAfter = numberBefore - detail.getNumber();
                    product.setNumber(numberAfter);
                    if (numberAfter == 0) {
                        product.setStatus(0);

                    }

                    productRepository.save(product);
                    flag = true;
                }
            }

            if (flag)
                orders.setEmployee(employee);
            orders.setStatus(status);
            Orders savedOrders = orderRepository.save(orders);
            String email = savedOrders.getCustomers().getEmail();
            DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
            personalization.addDynamicTemplateData("code", String.valueOf(savedOrders.getId()));
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            personalization.addDynamicTemplateData("name", savedOrders.getCustomers().getName());
            personalization.addDynamicTemplateData("address", savedOrders.getCustomers().getAddress());
            personalization.addDynamicTemplateData("date", DateFor.format(new Date()));

            emailService
                    .sendHtmlMessage(email, sendGridProperties.getDynamicTemplateId()
                                    .getConfirm(),
                            personalization);
        }
        if (status == 0) {
            for (Order_Detail detail : detailList) {
                Product product = productRepository.getById(detail.getProduct().getId());
                int numberBefore = product.getNumber();

                int numberAfter = numberBefore + detail.getNumber();
                product.setNumber(numberAfter);
                if (numberAfter != 0) {
                    product.setStatus(1);

                }

                productRepository.save(product);


            }

            orders.setEmployee(employee);
            orders.setStatus(status);
            orderRepository.save(orders);
        }

        if (status == 4){
            orders.setEmployee(employee);
            orders.setStatus(status);
            orders.setIspaid(1);
            orderRepository.save(orders);
        }

        else {
            orders.setEmployee(employee);
            orders.setStatus(status);
            orderRepository.save(orders);
        }
    }

    @Transactional
    @Override
    public OrderResponse getOrderById(Integer orderId) {

        Orders orders = orderRepository.getById(orderId);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setIdorder(orders.getId());
        orderResponse.setTotalprice(orders.getTotalprice());
        orderResponse.setPhone(orders.getPhoneNumber());
        orderResponse.setAddress(orders.getAddress());
        orderResponse.setNameCustomer(orders.getCustomers().getName());
        orderResponse.setStatus(orders.getStatus());
        orderResponse.setIspaid(orders.getIspaid());
        orderResponse.setEmployee(orders.getEmployee());
        orderResponse.setCustomer(orders.getCustomers());
        orderResponse.setCreatedate(orders.getCreateday());
        List<ProductResponse> productList = new ArrayList<>();

        for (Order_Detail detail : orders.getOrderDetailList()) {
            ProductResponse product_order = new ProductResponse();
            product_order.setProductId(detail.getProduct().getId());
            product_order.setProductName(detail.getProduct().getName());
            product_order.setNumber(detail.getNumber());
            product_order.setPrice(detail.getPrice());
            product_order.setDescription(detail.getProduct().getDescription());
            product_order.setListimages(detail.getProduct().getImages());
            productList.add(product_order);
        }

        orderResponse.setProduct(productList);
        return orderResponse;
    }

    @Transactional
    @Override
    public List<OrderResponse> getAllOrder() {
        List<Orders> ordersList = orderRepository.findAll(Sort.by(Sort.Order.desc("createday"), Sort.Order.desc("status")));
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Orders orders : ordersList) {

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setIdorder(orders.getId());
            orderResponse.setTotalprice(orders.getTotalprice());
            orderResponse.setPhone(orders.getPhoneNumber());
            orderResponse.setAddress(orders.getAddress());
            orderResponse.setNameCustomer(orders.getCustomers().getName());
            orderResponse.setStatus(orders.getStatus());
            orderResponse.setIspaid(orders.getIspaid());
            orderResponse.setEmployee(orders.getEmployee());
            orderResponse.setCustomer(orders.getCustomers());
            orderResponse.setCreatedate(orders.getCreateday());
            List<ProductResponse> productList = new ArrayList<>();

            for (Order_Detail detail : orders.getOrderDetailList()) {
                ProductResponse product_order = new ProductResponse();
                product_order.setProductId(detail.getProduct().getId());
                product_order.setProductName(detail.getProduct().getName());
                product_order.setNumber(detail.getNumber());
                product_order.setPrice(detail.getPrice());
                product_order.setDescription(detail.getProduct().getDescription());
                product_order.setListimages(detail.getProduct().getImages());
                productList.add(product_order);
            }

            orderResponse.setProduct(productList);
            orderResponseList.add(orderResponse);
        }

        return orderResponseList;

    }

    @Transactional
    @Override
    public List<OrderResponse> getAllOrderOfCustomer(Integer customerId) {
        List<Orders> ordersList = orderRepository.findAllByIdCustomer(customerId);
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Orders orders : ordersList) {

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setIdorder(orders.getId());
            orderResponse.setTotalprice(orders.getTotalprice());
            orderResponse.setPhone(orders.getPhoneNumber());
            orderResponse.setAddress(orders.getAddress());
            orderResponse.setNameCustomer(orders.getCustomers().getName());
            orderResponse.setStatus(orders.getStatus());
            orderResponse.setIspaid(orders.getIspaid());
            orderResponse.setEmployee(orders.getEmployee());
            orderResponse.setCustomer(orders.getCustomers());
            orderResponse.setCreatedate(orders.getCreateday());
            List<ProductResponse> productList = new ArrayList<>();

            for (Order_Detail detail : orders.getOrderDetailList()) {
                ProductResponse product_order = new ProductResponse();
                product_order.setProductId(detail.getProduct().getId());
                product_order.setProductName(detail.getProduct().getName());
                product_order.setNumber(detail.getNumber());
                product_order.setPrice(detail.getPrice());
                product_order.setDescription(detail.getProduct().getDescription());
                product_order.setListimages(detail.getProduct().getImages());
                productList.add(product_order);
            }

            orderResponse.setProduct(productList);
            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }


}
