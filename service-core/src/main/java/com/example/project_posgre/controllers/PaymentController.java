package com.example.project_posgre.controllers;

import com.example.project_posgre.services.Impl.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private VnPayService vnPayService;

    // B1: Tạo link thanh toán, FE gọi API này và redirect
    @GetMapping("/create-vnpay")
    public Map<String, Object> createVnpayPayment(
            @RequestParam int amount,
            @RequestParam String orderInfo,
            HttpServletRequest request) throws Exception {
        // Đề xuất lấy IP từ request header hoặc service check IP public
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
// Nếu vẫn là 127.0.0.1 thì lấy thử public IP từ https://icanhazip.com/


        String paymentUrl = vnPayService.createPaymentUrl(amount, orderInfo, ip,  request);
        Map<String, Object> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return response;
    }


    // B2: VNPAY sẽ gọi về returnUrl này khi user bấm "Quay về Merchant"
    @GetMapping("/vnpay-return")
    public String vnpayReturn(@RequestParam Map<String, String> params) throws Exception {
        if (checkVnpaySign(params)) {
            String responseCode = params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // Thành công
                return "Thanh toán thành công!";
            } else {
                // Thất bại hoặc hủy giao dịch
                return "Thanh toán thất bại: " + params.get("vnp_Message");
            }
        } else {
            return "Sai checksum!";
        }
    }

    // B3: VNPAY gọi về IPN URL (server to server)
    @GetMapping("/vnpay-ipn")
    public String vnpayIpn(@RequestParam Map<String, String> params) throws Exception {
        if (checkVnpaySign(params)) {
            String responseCode = params.get("vnp_ResponseCode");
            // Xử lý cập nhật đơn hàng vào DB tại đây
            if ("00".equals(responseCode)) {
                // Đơn hàng đã thanh toán thành công
                return "OK";
            } else {
                // Thanh toán thất bại/hủy
                return "FAIL";
            }
        } else {
            return "INVALID CHECKSUM";
        }
    }

    // Kiểm tra chữ ký VNPAY trả về
    private boolean checkVnpaySign(Map<String, String> params) throws Exception {
        String vnp_HashSecret = "QK78SNTOVW0I6S1ML7BDSJW9WSAH0T5M";
        String vnp_SecureHash = params.remove("vnp_SecureHash");
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (i < fieldNames.size() - 1) hashData.append("&");
            }
        }
        String myChecksum = VnPayService.hmacSHA512(vnp_HashSecret, hashData.toString());
        return myChecksum.equalsIgnoreCase(vnp_SecureHash);
    }
}

