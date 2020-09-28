package cn.itcast.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component //需要交给spring来管理，否则过滤器无效
public class MyParamGatewayFilterFactory extends AbstractGatewayFilterFactory<MyParamGatewayFilterFactory.NameValueConfig> {

    public MyParamGatewayFilterFactory() {
        super( MyParamGatewayFilterFactory.NameValueConfig.class);
    }

    public static class NameValueConfig{
        //对应在配置过滤器的时候指定的参数名
        private String param;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }

    public List<String> shortcutFieldOrder() {
        //param要跟静态内部类的成员变量名一样
        return Arrays.asList("param");
    }

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter ( ) {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // http://localhost:10010/api/user/8?name=itcast   config.param ==> name
                //获取请求参数中param对应的参数名的值
                ServerHttpRequest request = exchange.getRequest ( );
                if (request.getQueryParams ().containsKey ( config.param )){
                    List<String> strings = request.getQueryParams ( ).get ( config.param );
                    for (String string : strings) {
                        System.out.printf ("------------局部过滤器--------%s = %s------", config.param, string );
                    }
                }
                return chain.filter(exchange);
            }
        };
    }

}
