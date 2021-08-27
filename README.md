# PersonList
一个有加密功能的Java通讯录

- 需要连接mysql数据库

程序实现功能：
1. 服务器
    - 接收客户端发送的消息。
    - 登录。验证客户端发来的用户名和密码，与数据库中的信息对比。
    - 注册。将新的用户名和密码插入数据库中。
    - 登录成功后继续监听客户端接下来的消息。
    - 增加联系人。将增加的联系人添加到数据库中。
    - 删除联系人。从数据库中删除联系人。
    - 查询联系人。根据收到的条件查询数据库中对应的信息。
    - 修改联系人。更新数据库中对应的信息。
    - 备份通讯录的功能。将该用户保存的通讯录另存到一个excel文件中。
    - 加密。用户的密码采用MD5加密保存在user表。将加密后的联系人信息保存在person表中。

2. 客户端
    - 可以与服务器端通信。
    - 登录
     + 可以将用户名和MD5加密后的密码发送给服务器。
     + 接收来自服务器的确认（“ok”登录成功“error”登录失败）。
    - 注册：将用户名和MD5加密后的密码发送给服务器。
    - 通讯录：
     + 查询联系人。将对应的命令和查询条件发送到服务器。
     + 添加联系人。将命令和新的联系人信息（以用户的加密密码为加密秘钥进行了AES加密。）发送到数据库。
     + 删除联系人。将命令和对应的联系人id发送到服务器。
     + 更新联系人。将命令和对应的更新的联系人信息（以用户的加密密码为加密秘钥进行了AES加密。）发送到数据库。
     + 备份通讯录。将命令发送到服务器。
