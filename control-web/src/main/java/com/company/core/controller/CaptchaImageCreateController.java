package com.company.core.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;

@Controller
public class CaptchaImageCreateController {
	
	private Logger logger = LoggerFactory.getLogger(CaptchaImageCreateController.class);

    private Producer captchaProducer = null;

    public static final String CAPTCHA_KEY = "captcha_key";

    @Autowired
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @RequestMapping(value = {"/image/captcha-image"})
    public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        
        String capText = captchaProducer.createText();
        HttpSession session = request.getSession(true);
        session.setAttribute(CAPTCHA_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
       
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
            logger.debug("session:【{}】,验证码：【{}】",request.getSession().getId(),capText);
        } finally {
            out.close();
        }
        return null;
    }
}
