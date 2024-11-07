package com.example.springsecurity.service.Impl;

import com.example.springsecurity.model.dto.DesignRecordDto;
import com.example.springsecurity.model.entity.DesignRecord;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.request.DesignRecordForm;
import com.example.springsecurity.model.payload.request.FeedBackDesignRecordForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;
import com.example.springsecurity.repository.DesignRecordRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.DesignRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DesignRecordServiceImpl implements DesignRecordService {

    @Autowired
    private DesignRecordRepository designRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseData<DesignRecordDto> createDesignCord(DesignRecordForm form, Long customerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User engineer = userRepository.findByEmail(email).orElse(null);

        DesignRecord newDesignRecord = new DesignRecord();
        newDesignRecord.setEngineer(engineer); // Gán kỹ sư gửi bản vẽ
        newDesignRecord.setCustomer(userRepository.findById(customerId).orElse(null)); // Lấy khách hàng từ ID
        newDesignRecord.setEngineerNotes(form.getEngineerNotes());
        newDesignRecord.setCreationDate(LocalDate.now()); // Ngày lập hồ sơ
        newDesignRecord.setUpdateDate(LocalDate.now()); // Ngày cập nhật hồ sơ

        // Xử lý upload ảnh
        if ( !form.getDrawingFile().isEmpty() ) {
            log.info("Image received: {}", form.getDrawingFile().getOriginalFilename());
            try {
                // Xác định đường dẫn lưu file, sử dụng đường dẫn tuyệt đối từ thư mục gốc của dự án
                String uploadDir = System.getProperty("user.dir") + "/public/uploads";
                Path uploadPath = Paths.get(uploadDir);

                // Kiểm tra và tạo thư mục nếu chưa có
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);  // Tạo thư mục nếu chưa có
                    log.info("Directory created: {}", uploadDir);
                }

                // Đặt tên file theo tên sách hoặc tên gốc của file
                String fileName =  form.getDrawingFile().getOriginalFilename(); // lấy tên file luôn
                Path filePath = uploadPath.resolve(fileName);
                // Lưu file vào thư mục
                form.getDrawingFile().transferTo(filePath.toFile());

                // Chuyển đường dẫn file thành String và lưu vào Book entity
                newDesignRecord.setDrawingFile(filePath.toString());
            } catch (IOException e) {
                log.error("Failed to upload image: {}", e.getMessage());
                return new ResponseError<>(500, "Failed to upload image");
            }
        } else {
            log.warn("No image provided in the form.");
        }

        DesignRecord savedRecord = designRecordRepository.save(newDesignRecord);
        DesignRecordDto designRecordDto = DesignRecordDto.toDto(savedRecord);

        return new ResponseData<>(200, "Design record created successfully", designRecordDto);
    }

    @Override
    public ResponseData<DesignRecordDto> updateDesignRecord(Long recordId, DesignRecordForm form) {
        // Tìm bản vẽ theo ID
        DesignRecord designRecord = designRecordRepository.findById(recordId).orElse(null);
        if (designRecord == null) {return new ResponseError<>(404, "Không tìm thấy hồ sơ thiết kế");}

        // Xóa file cũ nếu có, ếu k null ko rỗng
        String oldFile = designRecord.getDrawingFile();
        if (oldFile != null && !oldFile.isEmpty()) {
            File fileToDelete = new File(oldFile);
            if (fileToDelete.exists()) {
                boolean isDeleted = fileToDelete.delete();
                if (!isDeleted) {
                    return new ResponseError<>(400, "Không thể xóa file bản vẽ cũ");
                }
            }
        }

        // Xử lý upload file mới nếu có
        if (form.getDrawingFile() != null && !form.getDrawingFile().isEmpty()) {
            try {
                // Xác định thư mục upload
                String uploadDir = System.getProperty("user.dir") + "/public/uploads";
                Path uploadPath = Paths.get(uploadDir);

                // Kiểm tra và tạo thư mục nếu chưa tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Đặt tên file theo tên gốc của file
                String fileName = form.getDrawingFile().getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);

                // Lưu file vào thư mục
                form.getDrawingFile().transferTo(filePath.toFile());

                // Cập nhật đường dẫn file trong đối tượng `designRecord`
                designRecord.setDrawingFile(filePath.toString());
            } catch (IOException e) {
                log.error("Không thể upload file: {}", e.getMessage());
                return new ResponseError<>(500, "Lỗi upload file");
            }
        }

        // Cập nhật ghi chú của kỹ sư nếu có
        if (form.getEngineerNotes() != null) {
            designRecord.setEngineerNotes(form.getEngineerNotes());
        }

        designRecord.setUpdateDate(LocalDate.now());

        // Lưu lại thông tin đã cập nhật
        DesignRecord savedRecord = designRecordRepository.save(designRecord);
        DesignRecordDto designRecordDto = DesignRecordDto.toDto(savedRecord);
        return new ResponseData<>(200, "Cập nhật hồ sơ thiết kế thành công", designRecordDto);
    }


    @Override
    public ResponseData<List<DesignRecordDto>> getAllDesignRecordsForCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {return new ResponseError<>(404, "User not found");}

        List<DesignRecord> designRecords = designRecordRepository.findAllByCustomer(currentUser);

        List<DesignRecordDto> designRecordDtos = designRecords.stream()
                .map(DesignRecordDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Design records retrieved successfully", designRecordDtos);
    }

    @Override
    public ResponseData<DesignRecordDto> getDesignRecordDetails(Long recordId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {return new ResponseError<>(404, "User not found");}

        DesignRecord designRecord = designRecordRepository.findById(recordId).orElse(null);
        if (designRecord == null) {return new ResponseError<>(404, "Design record not found");}

        if (!designRecord.getCustomer().getId().equals(currentUser.getId())) {
            return new ResponseError<>(403, "Access denied. You are not the customer for this design record.");}

        DesignRecordDto designRecordDto = DesignRecordDto.toDto(designRecord);
        return new ResponseData<>(200, "Design record details retrieved successfully", designRecordDto);
    }

    @Override
    public ResponseData<DesignRecordDto> updateCustomerFeedback(Long recordId, FeedBackDesignRecordForm form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {return new ResponseError<>(404, "User not found");}

        DesignRecord designRecord = designRecordRepository.findById(recordId).orElse(null);
        if (designRecord == null) {return new ResponseError<>(404, "Design record not found");}

        if (!designRecord.getCustomer().getId().equals(currentUser.getId())) {return new ResponseError<>(403, "Access denied. You are not the customer for this design record.");}

        designRecord.setCustomerFeedback(form.getCustomerFeedback());
        designRecord.setUpdateDate(LocalDate.now());

        DesignRecord savedRecord = designRecordRepository.save(designRecord);
        return new ResponseData<>(200, "Customer feedback updated successfully");
    }

    @Override
    public ResponseData<DesignRecordDto> deleteRecord(Long recordId){
        DesignRecord designRecord = designRecordRepository.findById(recordId).orElse(null);
            if(designRecord == null ){return new ResponseError<>(400,"design record not found");}

        designRecordRepository.delete(designRecord);

        return new ResponseData<>(200," delete design record successfully");
    }


}
