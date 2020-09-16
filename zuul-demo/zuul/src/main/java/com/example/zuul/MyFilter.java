package com.example.zuul;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Component
public class MyFilter extends ZuulFilter {
    private static Logger log= LoggerFactory.getLogger(MyFilter.class);
    @Override
    public String filterType() {
        return "pre"; //定义 filter 的类型，有 pre、route、post、error 四种
    }
    @Override
    public int filterOrder() {
        return 0;//定义 filter 的顺序，数字越小表示顺序越高，越先执行
    }
    @Override
    public boolean shouldFilter() {
        return true;//表示是否需要执行该 filter，true 表示执行，false 表示不执行
    }
    @Override
    public Object run() throws ZuulException {
        //filter 需要执行的具体操作
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        System.out.println(token);
        if(token==null){
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty -- qinfei 180410126");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        log.info("ok");
        return null;
    }
}