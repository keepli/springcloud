package cn.itcast.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * Ordered是可选项，它里面有个方法getOrder，可以设置优先级
 */
@Component //需要交给spring来管理，否则过滤器无效
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("--------------全局过滤器MyGlobalFilter------------------");
        String token = exchange.getRequest ( ).getQueryParams ( ).getFirst ( "token" );
        if (StringUtils.isBlank ( token )){
            //如果是token为null或者""
            //设置响应状态码为未授权
            ServerHttpResponse response = exchange.getResponse ( );
            response.setStatusCode ( HttpStatus.UNAUTHORIZED );
            return response.setComplete ();
        }
        return chain.filter ( exchange );
    }

    //Ordered中的方法
    @Override
    public int getOrder() {
        //值越小越先执行
        return 1;
    }
}
