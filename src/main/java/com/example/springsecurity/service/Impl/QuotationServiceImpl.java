package com.example.springsecurity.service.Impl;

import com.example.springsecurity.model.dto.QuotationDto;
import com.example.springsecurity.model.entity.Quotation;
import com.example.springsecurity.model.entity.User; // Nhập khẩu lớp User
import com.example.springsecurity.model.payload.request.QuotationForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;
import com.example.springsecurity.repository.QuotationRepository;
import com.example.springsecurity.repository.UserRepository; // Nhập khẩu lớp UserRepository
import com.example.springsecurity.service.QuotationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private UserRepository userRepository; // Khai báo UserRepository

    @Override
    public ResponseData<QuotationDto> newQuo(QuotationForm form) {
        log.info("Creating a new quotation ....");
        Quotation quo = new Quotation();

        // Tìm người dùng dựa trên email
        Optional<User> userOptional = userRepository.findByEmail(form.getEmail());
        if (userOptional.isPresent()) {
            quo.setUser(userOptional.get()); // Gán người dùng vào báo giá
        } else {
            // Xử lý khi không tìm thấy người dùng
            return new ResponseData<>(404, "User not found", null);
        }
        quo.setQuotationNumber(UUID.randomUUID().toString().substring(0,8).toUpperCase()); // Mã đơn hàng
        quo.setCustomerName(form.getCustomerName()); // Tên khách hàng
        quo.setPhoneNumber(form.getPhoneNumber()); // Số điện thoại
        quo.setEmail(form.getEmail()); // Email
        quo.setAddress(form.getAddress()); // Địa chỉ
        quo.setServiceType(form.getServiceType()); // Loại dịch vụ
        quo.setLocation(form.getLocation());
        quo.setAreaSize(form.getAreaSize()); // Diện tích
        quo.setDesignDetails(form.getDesignDetails()); // Chi tiết thiết kế
        quo.setMaterialCost(form.getMaterialCost()); // Chi phí vật liệu
        quo.setLaborCost(form.getLaborCost()); // Chi phí nhân công
        quo.setTransportationCost(form.getTransportationCost()); // Chi phí vận chuyển
        quo.setTotalCost(form.getTotalCost()); // Tổng chi phí
        quo.setPaymentMethod(Quotation.PaymentMethod.valueOf(form.getPaymentMethod().toUpperCase())); // Phương thức thanh toán
        quo.setQuotationDate(form.getQuotationDate()); // Ngày lập báo giá
        quo.setExpirationDate(form.getExpirationDate()); // Ngày hết hạn của báo giá
        quo.setStatus(Quotation.Status.PENDING); // Trạng thái báo giá mặc định là PENDING

        // Lưu báo giá vào cơ sở dữ liệu
        Quotation savedQuotation = quotationRepository.save(quo);
        QuotationDto quotationDto = QuotationDto.toDto(savedQuotation);

        return new ResponseData<>(200, "Create quotation successfully", quotationDto);
    }

    // Người dùng xem danh sách báo giá từ admin gủi xuống.
    @Override
    public ResponseData<List<QuotationDto>> getListQuoByUser() {
        log.info("Retrieving the quotations list ...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElse(null);
        if (currentUser == null) {return new ResponseError<>(401, "User not found with email: " + email);}

        // Lấy danh sách báo giá theo người dùng
        List<Quotation> quotations = quotationRepository.findByUser(currentUser);
        if (quotations.isEmpty()) {
            return new ResponseData<>(404, "No quotations found for this user", null);
        }

        List<QuotationDto> quotationDtos = quotations.stream()
                .map(QuotationDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Quotations retrieved successfully", quotationDtos);
    }

    @Override
    public ResponseData<QuotationDto> approveQuotation(Long quoId){
        log.info("Accepting quotation with quoId "+ quoId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();;

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if( currentUser == null ) {return new ResponseError<>(401, "User not found with email: " + email);}

        // Lấy quo theo ID
        Quotation currentQuo = quotationRepository.findById(quoId).orElse(null);
        if (currentQuo == null) {return new ResponseData<>(404, "Quotation not found", null);}

         // Nếu tồn tại quo
         currentQuo.setStatus(Quotation.Status.APPROVED);

         quotationRepository.save(currentQuo);

        return new ResponseData<>(200,"successfully APPROVED",null);
    }

    @Override
    public ResponseData<QuotationDto> rejectQuotation(Long quoId){
        log.info("Rejecting quotation with quoId "+ quoId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();;

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if( currentUser == null ) {return new ResponseError<>(401, "User not found with email: " + email);}

        // Lấy quo theo ID
        Quotation currentQuo = quotationRepository.findById(quoId).orElse(null);
        if (currentQuo == null) {return new ResponseData<>(404, "Quotation not found", null);}

        // Nếu tồn tại quo
        currentQuo.setStatus(Quotation.Status.REJECTED);

        quotationRepository.save(currentQuo);

        return new ResponseData<>(200,"successfully REJECTED",null);
    }
}
