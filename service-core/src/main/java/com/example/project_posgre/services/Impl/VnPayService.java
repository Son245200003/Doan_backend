package com.example.project_posgre.services.Impl;

import com.example.project_posgre.component.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VnPayService {

    @Value("${vnpay.tmncode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashsecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.url}")
    private String vnp_Url;

    @Value("${vnpay.returnurl}")
    private String vnp_ReturnUrl;

    @Value("${vnpay.ipnurl}")
    private String vnp_IpnUrl;
    private final VNPayUtils vnPayUtils;
    public String createPaymentUrl(int amountt, String orderInfo, String ipAddress, HttpServletRequest httpRequest) throws Exception {
        String version = "2.1.0";
        String command = "pay";
        String orderType = "other";
        long amount = amountt * 100; // Số tiền cần nhân với 100
        String bankCode = "NCB";

        String transactionReference = vnPayUtils.getRandomNumber(8); // Mã giao dịch
        String clientIpAddress = vnPayUtils.getIpAddress( httpRequest);

        String terminalCode = vnp_TmnCode;

        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", version);
        params.put("vnp_Command", command);
        params.put("vnp_TmnCode", terminalCode);
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            params.put("vnp_BankCode", bankCode);
        }
        params.put("vnp_TxnRef", transactionReference);
        params.put("vnp_OrderInfo", "Thanh toan don hang:" + transactionReference);
        params.put("vnp_OrderType", orderType);

        String locale = "vn";
        if (locale != null && !locale.isEmpty()) {
            params.put("vnp_Locale", locale);
        } else {
            params.put("vnp_Locale", "vn");
        }

        params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        params.put("vnp_IpAddr", clientIpAddress);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String createdDate = dateFormat.format(calendar.getTime());
        params.put("vnp_CreateDate", createdDate);

        calendar.add(Calendar.MINUTE, 15);
        String expirationDate = dateFormat.format(calendar.getTime());
        params.put("vnp_ExpireDate", expirationDate);

        List<String> sortedFieldNames = new ArrayList<>(params.keySet());
        Collections.sort(sortedFieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder queryData = new StringBuilder();

        for (Iterator<String> iterator = sortedFieldNames.iterator(); iterator.hasNext();) {
            String fieldName = iterator.next();
            String fieldValue = params.get(fieldName);

            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                queryData.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (iterator.hasNext()) {
                    hashData.append('&');
                    queryData.append('&');
                }
            }
        }

        String secureHash = vnPayUtils.hmacSHA512(vnp_HashSecret, hashData.toString());
        queryData.append("&vnp_SecureHash=").append(secureHash);


        return vnp_Url + "?" + queryData.toString();
    }


    public static String hmacSHA512(String key, String data) throws Exception {
         try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            final SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

}
