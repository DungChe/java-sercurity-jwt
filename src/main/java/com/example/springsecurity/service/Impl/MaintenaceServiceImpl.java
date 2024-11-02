package com.example.springsecurity.service.Impl;

import com.example.springsecurity.model.dto.MaintenanceDto;
import com.example.springsecurity.model.entity.Maintenance;
import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.request.MaintenanceForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;
import com.example.springsecurity.repository.MaintenaceRepository;
import com.example.springsecurity.repository.OrderRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.MaintenaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MaintenaceServiceImpl implements MaintenaceService {

    @Autowired
    private MaintenaceRepository maintenaceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseData<MaintenanceDto> createMaintenance(MaintenanceForm form, Long orderId){
        log.info("Dang tao don bao tri");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null ){ return new ResponseError<>(400,"Order not found");}
        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null ){ return new ResponseError<>(400,"User not found");}

        Maintenance mt = new Maintenance();
        mt.setTitle("Hoa don bao tri");
        mt.setUser(order.getUser());
        mt.setPrice(form.getPrice());
        mt.setConstructionStaff(form.getConstructionStaff());
        mt.setStartDate(LocalDate.now());
        mt.setEndDate(form.getEndDate());
        mt.setContent(form.getContent());

        Maintenance saveMt = maintenaceRepository.save(mt);
        log.info("Saved Maintenance: {}", saveMt);

        return new ResponseData<>(200,"Created successfully", MaintenanceDto.toDto(saveMt));
    }

    @Override
    public ResponseData<List<MaintenanceDto>> getAll(){
        List<MaintenanceDto> orders = maintenaceRepository.findAll().stream()
                .map(MaintenanceDto::toDto)
                .collect(Collectors.toList());
        return new ResponseData<>(200, "List maintain retrieved successfully", orders);
    }
    @Override
    public ResponseData<MaintenanceDto> change(Long id, MaintenanceForm form){

        Maintenance mt = maintenaceRepository.findById(id).orElse(null);
        if( mt == null ) return new  ResponseError<>(200,"Bill Maintenance not found");

        mt.setPrice(form.getPrice());
        mt.setEndDate(form.getEndDate());
        mt.setContent(form.getContent());
        mt.setConstructionStaff(mt.getConstructionStaff());
        return new ResponseData<>(200, "Change buill succesfully", null);

    }

}
