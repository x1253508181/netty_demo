package com.itheima.netty.handler.server;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author xb
 * @date 2023/3/2 21:57
 */
@Slf4j
public class MyHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {

        HttpMethod method = fullHttpRequest.method();
        if (HttpMethod.GET.equals(method)) {
            parseGet(fullHttpRequest);
        } else if (HttpMethod.POST.equals(method)) {
            parsePost(fullHttpRequest);
        } else {
            log.error("{} method is not supported ,please change http method for get or post!", method);
        }

    }

    private void parseGet(FullHttpRequest fullHttpRequest) {
        //通过请求url解析获得参数数据
        parseKVstr(fullHttpRequest.uri(), true);

    }

    private void parsePost(FullHttpRequest fullHttpRequest) {
        //获取content-type
        String contentType = getContentType(fullHttpRequest);//text/plain;charset=UTF-8

        switch (contentType) {
            case "application/json":
                parseJson(fullHttpRequest);
                break;
            case "application/x-www-form-urlencoded":
                parseForm(fullHttpRequest);
                break;
            case "multipart/form-data":
                parseMultipart(fullHttpRequest);
                break;
            default:
        }


    }

    private void parseMultipart(FullHttpRequest fullHttpRequest) {
    }

    private void parseForm(FullHttpRequest fullHttpRequest) {
        //两个部分有数uri,body
        parseKVstr(fullHttpRequest.uri(), true);
        parseKVstr(fullHttpRequest.content().toString(StandardCharsets.UTF_8), false);
    }

    private void parseJson(FullHttpRequest fullHttpRequest) {
        String jsonstr = fullHttpRequest.content().toString(StandardCharsets.UTF_8);
        //使用json工具反序列化
        JSONObject jsonObject = JSONObject.parseObject(jsonstr);
        //打印 json数据
        jsonObject.forEach((key, value) -> log.info("json key={},json value= {}", key, value));
    }

    private String getContentType(FullHttpRequest fullHttpRequest) {
        HttpHeaders headers = fullHttpRequest.headers();
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        String[] split = contentType.split(";");
        return split[0];
    }

    private void parseKVstr(String url, boolean hasPath) {
        // 通过QueryStringDecoder解析字符串

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(url, StandardCharsets.UTF_8, hasPath);
        Map<String, List<String>> parameters = queryStringDecoder.parameters();

        if (parameters != null && parameters.size() > 0) {
            parameters.forEach((key, value) -> log.info("参数名:{},参数值:{}", key, value));
        }

    }


}