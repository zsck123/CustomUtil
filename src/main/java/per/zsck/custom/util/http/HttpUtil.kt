package per.zsck.custom.util.http

import com.fasterxml.jackson.databind.JsonNode
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import per.zsck.custom.util.jackson.JacksonUtil
import java.io.IOException
import java.net.URI

/**
 * @author zsck
 * @date   2023/1/26 - 20:04
 */
@Suppress("unused")
object HttpUtil{
    private val httpBase = HttpBase()

    fun doGetStr(url: String, header: Array<Header>? = null, params: Map<String, Any>? = null): String {
        return httpBase.doGetStr(url, header, params, false)
    }

    fun doGetBytes(url: String, header: Array<Header>? = null, params: Map<String, Any>? = null): ByteArray {
        return httpBase.doGetBytes(url, header, params, false)
    }

    fun doGetJson(url: String, header: Array<Header>? = null, params: Map<String, Any>? = null): JsonNode {
        return httpBase.doGetJson(url, header, params, false)
    }

    fun doPostStr(url: String, entity: HttpEntity? = null, header: Array<Header>? = null): String {
        return httpBase.doPostStr(url, entity, header, false)
    }
    fun doPostJson(url: String, entity: HttpEntity? = null, header: Array<Header> ?= null): JsonNode {
        return httpBase.doPostJson(url, entity, header, false)
    }
}
@Suppress("unused")
open class HttpBase{

    protected open val httpClient: CloseableHttpClient = HttpClients.createDefault()


    @Throws(IOException::class)
    fun doGetStr(url: String, header: Array<Header>? = getHeader(), params: Map<String, Any>? = null, isDefault: Boolean = true): String {
        val httpGet = HttpGet(getUri(url, params))
        httpGet.setHeaders(header)
        return doHttpRequestStr(httpGet, isDefault)
    }

    @Throws(IOException::class)
    fun doGetBytes(url: String, header: Array<Header>? = getHeader(), params: Map<String, Any>? = null, isDefault: Boolean = true): ByteArray {
        val httpGet = HttpGet( getUri(url, params) )
        httpGet.setHeaders(header)
        return doHttpRequestBytes(httpGet, isDefault)
    }

    @Throws(IOException::class)
    fun doPostStr(url: String, entity: HttpEntity? = null, header: Array<Header>? = getHeader(), isDefault: Boolean = true): String {
        val httpPost = HttpPost(url)
        httpPost.setHeaders(header)
        entity?.let { httpPost.entity = it }
        return doHttpRequestStr(httpPost, isDefault)
    }

    @Throws(IOException::class)
    fun doGetJson(url: String, header: Array<Header>? = getHeader(), params: Map<String, Any>? = null, isDefault: Boolean = true): JsonNode {
        return JacksonUtil.readTree(doGetStr(url, header, params, isDefault))
    }
    @Throws(IOException::class)
    fun doPostJson(url: String, entity: HttpEntity? = null, header: Array<Header>? = getHeader(), isDefault: Boolean = true): JsonNode {
        return JacksonUtil.readTree(doPostStr(url, entity, header, isDefault ))
    }

    private fun doHttpRequestStr(httpRequestBase: HttpRequestBase, isDefault: Boolean = true): String{
        val httpClient : CloseableHttpClient = if (isDefault) this.httpClient else HttpClients.createDefault()
        try {
            httpClient.execute(httpRequestBase).use { exec -> return EntityUtils.toString(exec.entity) }
        }finally {//若不使用默认httpClient则自动关闭
            if ( !isDefault ){
                httpClient.close()
            }
        }
    }
    private fun doHttpRequestBytes(httpRequestBase: HttpRequestBase, isDefault: Boolean = true): ByteArray{
        val httpClient : CloseableHttpClient = if (isDefault) this.httpClient else HttpClients.createDefault()
        try {
            httpClient.execute(httpRequestBase).use { exec -> return EntityUtils.toByteArray(exec.entity) }
        }finally {//若不使用默认httpClient则自动关闭
            if ( !isDefault ){
                httpClient.close()
            }
        }
    }

    private fun getUri(url: String, params: Map<String, Any>? = null): URI {
        val uriBuilder = URIBuilder(url)

        params?.let {
            it.forEach { (key, value) -> uriBuilder.addParameter(key, value.toString()) }
        }

        return uriBuilder.build()
    }



    /**
     * 待子类重写
     */
    open fun getHeader(): Array<Header>?{
        return null
    }
}