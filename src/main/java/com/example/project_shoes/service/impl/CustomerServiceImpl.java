package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.CustomerDTO;
import com.example.project_shoes.entity.Customer;
import com.example.project_shoes.repository.CustomerRepository;
import com.example.project_shoes.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findByIsDeleteFalse();
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findByIdAndIsDeleteFalse(id);
        return customer != null ? convertToDTO(customer) : null;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        
        // Mã hóa mật khẩu
        String plainPassword = customerDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        System.out.println("Saving new customer with email: " + customerDTO.getEmail());
        System.out.println("Original password length: " + (plainPassword != null ? plainPassword.length() : 0));
        System.out.println("Encoded password: " + encodedPassword);
        
        customer.setPassword(encodedPassword);
        
        // Thiết lập thời gian tạo
        if (customer.getId() == null) {
            customer.setCreatedDate(LocalDateTime.now());
        }
        
        // Mặc định là active và không xóa
        customer.setIsActive(true);
        customer.setIsDelete(false);
        
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findByIdAndIsDeleteFalse(customerDTO.getId());
        if (existingCustomer == null) {
            return null;
        }
        
        Customer customer = convertToEntity(customerDTO);
        
        // Giữ nguyên mật khẩu hiện tại nếu không cung cấp
        if (customerDTO.getPassword() == null || customerDTO.getPassword().isEmpty()) {
            customer.setPassword(existingCustomer.getPassword());
        } else {
            customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        }
        
        // Cập nhật thời gian
        customer.setCreatedDate(existingCustomer.getCreatedDate());
        customer.setUpdatedDate(LocalDateTime.now());
        
        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }

    @Override
    public boolean delete(Long id) {
        Customer customer = customerRepository.findByIdAndIsDeleteFalse(id);
        if (customer != null) {
            // Soft delete
            customer.setIsDelete(true);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public CustomerDTO findByUsername(String username) {
        Customer customer = customerRepository.findByUsernameAndIsDeleteFalse(username);
        return customer != null ? convertToDTO(customer) : null;
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmailAndIsDeleteFalse(email);
        return customer != null ? convertToDTO(customer) : null;
    }

    @Override
    public CustomerDTO findByEmailAndPassword(String email, String password) {
        System.out.println("Finding customer with email: " + email);
        Customer customer = customerRepository.findByEmailAndIsDeleteFalse(email);
        if (customer != null) {
            System.out.println("Customer found, checking password");
            System.out.println("Input password: " + password);
            System.out.println("Stored password hash: " + customer.getPassword());
            
            // Thử kiểm tra mật khẩu bằng cả hai cách
            boolean matches = passwordEncoder.matches(password, customer.getPassword());
            System.out.println("Password matches using encoder: " + matches);
            
            if (matches) {
                return convertToDTO(customer);
            }
        } else {
            System.out.println("No customer found with email: " + email);
        }
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        Customer customer = customerRepository.findByEmailAndIsDeleteFalse(email);
        return customer != null;
    }

    @Override
    public boolean existsByUsername(String username) {
        Customer customer = customerRepository.findByUsernameAndIsDeleteFalse(username);
        return customer != null;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setAddress(customer.getAddress());
        dto.setAvatar(customer.getAvatar());
        dto.setCreatedBy(customer.getCreatedBy());
        dto.setCreatedDate(customer.getCreatedDate());
        dto.setEmail(customer.getEmail());
        
        // Boolean handling để tránh vấn đề null trong Java và MySQL
        dto.setIsActive(customer.getIsActive() != null ? customer.getIsActive() : false);
        dto.setIsDelete(customer.getIsDelete() != null ? customer.getIsDelete() : false);
        
        dto.setName(customer.getName());
        // Không trả về mật khẩu trong DTO
        dto.setPassword(null);
        dto.setPhone(customer.getPhone());
        dto.setUpdatedBy(customer.getUpdatedBy());
        dto.setUpdatedDate(customer.getUpdatedDate());
        dto.setUsername(customer.getUsername());
        
        return dto;
    }

    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setAddress(dto.getAddress());
        customer.setAvatar(dto.getAvatar());
        customer.setCreatedBy(dto.getCreatedBy());
        customer.setCreatedDate(dto.getCreatedDate());
        customer.setEmail(dto.getEmail());
        
        // Boolean handling
        customer.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : false);
        customer.setIsDelete(dto.getIsDelete() != null ? dto.getIsDelete() : false);
        
        customer.setName(dto.getName());
        // Mật khẩu sẽ được mã hóa riêng trong các phương thức save và update
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            customer.setPassword(dto.getPassword());
        }
        customer.setPhone(dto.getPhone());
        customer.setUpdatedBy(dto.getUpdatedBy());
        customer.setUpdatedDate(dto.getUpdatedDate());
        customer.setUsername(dto.getUsername() != null ? dto.getUsername() : dto.getEmail());
        
        return customer;
    }
} 