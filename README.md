

具体调用
=====
```

 ClickController mClickController = new ClickController(mTv);
 String mReplaceText= "[__Fill.Replace__]";//后台约束输入框的字段
       //添加链接
        mTv.setMovementMethod(LinkMovementMethod
                .getInstance());
            mTv.setText(Html.fromHtml(source, new URLImageGetter(source, this, mTv), new HtmlTagHandler(mTv, rl, this,mClickController,mReplaceText)));
```
```
source为html代码
mTv为显示的textView
rl为包裹textview的RelativeLayout布局
```
主要功能如下：
======
1.在TextView中加载带图文的html代码

2.在指定位置插入EditText控件

3.获取每一个EditText上输入内容


 Effect drawing:
 ![Ring-menu](https://github.com/huang8023wei/HtmlLoadEditText-master/blob/master/IMG_0509.JPG)
 ![Ring-menu-active](https://github.com/huang8023wei/HtmlLoadEditText-master/blob/master/IMG_0510.JPG)
