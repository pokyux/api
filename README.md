# Kininaru的API服务器
由`Spring`提供技术支持
## 目前可使用
- `https://api.shiftregister.top/hello`  
一个打招呼的 API, 可以用来确保API服务器在线.
  
- `https://api.shiftregister.top/mail/token?address=[email address]`  
发送验证码邮件, 返回值为JSON.
  - `"status": 0` 发送成功
  - `"status": 1` 缺少参数 (邮件地址)
  - `"status": 2` 发送过于频繁 (60s内只能给相同的地址发送一次邮件)
  - `"status": 3` 邮件系统内部错误
    
- `https://api.shiftregister.top/mail/check-token?address=[email address]&token=[token]`  
查看验证码正确性, 返回值为JSON.
  - `"status": 0` 验证码与邮箱匹配
  - `"status": 1` 验证码与邮箱不匹配
  - `"status": 2` 缺少邮箱参数
  - `"status": 3` 缺少验证码参数
  - `"status": 4` 没有发送到这个邮箱的验证码