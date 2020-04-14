# 使用说明
fileServe-FastDfs

这是一个分布式的文件存储系统。提供可视化界面操作。[演示地址](http://file.ambitlu.work/)
文件上传可使用jwt身份验证后使用。也在利用在请求投中加入X-SID和X-Signature方式。

X-SID是随机生成的字符。X-Signature是对X-SID利用私钥和RSA加密后的密文。

服务端通过RsaUtil.deWithRSAPublicKey(Signature, publicKey);进行解密。
具体使用方法在util包中。自行查看。

使用步骤。
1.下载源文件
```html
git clone https://github.com/AmbitionLover/fileServe-FastDfs.git
```

2.配置FastDfs
首先你要有FastDfs的服务。[点击这里配置一个FastDfs](https://qambi.gitee.io/server/20200314-9bf72771/)
在这个文件中。fileServe-FastDfs/src/main/resources/fdfs_client.conf
```properties
connect_timeout=60
network_timeout=60
charset=UTF-8
http.tracker_http_port= //配置port    
tracker_server=//你的FastDfs服务网址
```

3.web前端界面。如果你需要联系我，我给你发。