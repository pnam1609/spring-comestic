package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.InvoiceDTO;
import com.example.ecommercespring.entity.*;
import com.example.ecommercespring.repository.InvoiceRepository;
import com.example.ecommercespring.repository.OrderRepository;
import com.example.ecommercespring.repository.ShipperRepository;
import com.example.ecommercespring.repository.UserRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShipperRepository shipperRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<InvoiceDTO> getAll(){
        return invoiceRepository.findAll().stream().map(InvoiceDTO::new).collect(Collectors.toList());
    }

//    @Override
//    public InvoiceDTO getById(Long id){
//        Invoice invoice = invoiceRepository.findById(id).orElse(null);
//        return new InvoiceDTO(invoice);
//    }

    @Override
    public Response addNew(InvoiceDTO invoiceDTO){
        User user = userRepository.findById(invoiceDTO.getEmployeeId()).orElse(null);
        if(user == null)
            return new Response(false,"Mã nhân viên không tồn tại");
        OrderUser orderUser = orderRepository.findById(invoiceDTO.getOrderId()).orElse(null);
        if(orderUser == null)
            return new Response(false,"Mã phiếu đặt không tồn tại");
        Shipper shipper = shipperRepository.findById(invoiceDTO.getShipperId()).orElse(null);
        if(shipper == null)
            return new Response(false,"Mã nhân viên giao hàng không tồn tại");
        Invoice invoice = invoiceDTO.toEntity();
        invoice.setUser(user);
        invoice.setOrderUser(orderUser);
        invoice.setShipper(shipper);
        invoiceRepository.save(invoice);
        return new Response(true,"Tạo hóa đơn thành công");
    }

    @Override
    public ResponseEntity<?> getStatistic(Integer year){
        int yearCurrent = Calendar.getInstance().get(Calendar.YEAR);
        if(year > yearCurrent){
            return ResponseEntity.badRequest().body(new Response(false, "Không thể thống kê thời gian trong tương lai"));
        }

        List<Integer> integerList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        int month = 1;
        if(year == yearCurrent){
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }else{
            month = 12;
        }
        for (int i = 1; i<= month; i++){
            cal.set(year,i,1);
            Date firstDateOfMonth = new Date(cal.getTimeInMillis());
            cal.set(year,i+1,1);
            Date lastDateOfMonth = new Date(cal.getTimeInMillis());

            try{
                Integer sum = invoiceRepository.findAll().stream()
                        .filter(invoice -> invoice.getDateCreated().compareTo(firstDateOfMonth) > 0
                                && invoice.getDateCreated().compareTo(lastDateOfMonth) < 0)
                        .mapToInt(value -> value.getTotalPrice())
                        .sum();
                integerList.add(sum);
            }catch (Exception e ){
                integerList.add(0);
            }
        }
        return ResponseEntity.ok(integerList);
    }
}
