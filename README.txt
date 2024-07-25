1. 什么是RestTemplate

RestTemplate是一个基于Rest规范，执行HTTP请求的客户端。

2. getForObject和getForEntity的区别

getForObject: 返回的对象是响应体中数据转换成的对象
getForEntity: 返回的对象是ResponseEntity对象，包含了响应的一些重要信息，例如响应头、状态码、响应体等
