### 基于SpringBoot + Vue的智慧互助养老服务平台.

智慧互助养老平台、社区养老互助系统、居家养老服务平台

###### 管理员：
用户管理、公告管理、社区交流、敏感词管理、模块管理、消息回复内容管理、社区新闻、留言板信息、首页图片管理、报警处理、帮助服务信息、积分物品上架、志愿者积分兑换、健康记录、健身养生课程。

###### 志愿者：
账户注册、账户登录、个人信息、密码修改、积分物品兑换、我的兑换记录、我的接单、服务看板。

###### 用户：
小程序账户注册登录、个人信息修改、社区交流、我的贴子、发布贴子、健康记录、健康养生观看、发布报警信息、发布服务内容、消息内容回复。

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

[志愿者]
fkkk
1234qwer

[用户]
微信登录注册

#### 项目截图
暂无

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/b1bdf7c4-6c3b-4691-a679-b71f6afa2ca3.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/66c89817-3c7b-4157-bd08-f04a5af93f9a.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/afe5bf62-cd34-4d03-945a-fe884e4beadd.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/61dc940f-b7eb-4dfd-8a3f-95cb74241ff8.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/15961156-7f9c-4cbe-9b73-79584d1958b3.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/25dabc3f-144b-4296-ae53-e08ab6d2db23.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/c3e47bb5-a502-4e52-aabc-9d7085414ab9.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/dd9c095e-5221-474d-a559-b67a6d48169a.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/f7c45165-aa33-49d2-980e-e85a060b0f8b.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/db4baa3f-b87b-493b-878c-317afaa45874.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/ee107209-b7b3-4458-934f-b4ba09d900e2.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1d0326bb-7067-4dbc-9b90-f2a080592d90.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/2ab6ce7e-c9ee-420f-a90f-b9c758b2467e.png) |  |

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/10c58a27-df59-4cd1-ba88-cf0c4d078624.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/2ecb2ca5-c753-4e06-babc-b044539d5c98.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/9e27bfc5-70c3-4e20-9106-bd57719506c2.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1b8914cd-8f68-4fcd-aa32-5a3a245a4c88.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/7f865e86-86b8-426b-8382-ddca7605c2fa.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/fd1696fc-b26a-4bd2-a370-fbb5fb45c36d.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/7c496848-0a06-4b2a-9887-8ffe6dff63e8.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/da8d73d2-b47f-487d-9ef7-2b993733d5c5.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/3d3dc80a-2bae-4130-979b-c4ee9fce1a61.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/bbe48ae1-cc2b-4c77-9b50-14fa6bcee3e3.png) |

#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png)

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)

<p><img align="center" src="https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/%E5%90%88%E4%BD%9C%E7%89%A9%E6%96%99%E6%A0%B7%E5%BC%8F%20(3).png" alt="fankekeke" /></p>
