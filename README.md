# rocketmq-demo
### 1、削峰，模拟用户点赞，使用mq拉消息，每次定量拉去，防止大量请求同时打到数据库；

### 2、新用户注册赠送优惠券，模拟赠送优惠券耗时操作，使用mq进行异步操作体现优势；

### 3、用户下订单，发送延时消息，超时未支付取消订单，返还使用的优惠券；

### 4、用户订单支付，模拟发送打印订单，仓库出库，物流发货操作，使用顺序消息进行发送；

**H2 console**

http://localhost:8888/console/login.do

**swagger-ui**

http://localhost:8888/swagger-ui.html