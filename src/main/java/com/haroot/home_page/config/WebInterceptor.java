package com.haroot.home_page.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class WebInterceptor implements HandlerInterceptor {

    /**
     * Controllerの処理終了後のインターセプター
     */
    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {
        // NOTE: リダイレクトだと、デフォルトでモデルをクエリパラメータに付与してしまうため除外する
        if (modelAndView != null && !isRedirectView(modelAndView)) {
            modelAndView.addObject("uri", request.getRequestURI());
        }
    }

    private boolean isRedirectView(ModelAndView modelAndView) {
        return modelAndView != null && modelAndView.getView() instanceof RedirectView;
    }
}
