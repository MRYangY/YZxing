# YZxing
一款仿微信扫一扫界面，基于zxing实现的扫码库。  
### **实现效果** ###  
![image](https://github.com/MRYangY/YZxing/blob/master/app/src/main/res/drawable-xhdpi/screenshot_2.png) ![image](https://github.com/MRYangY/YZxing/blob/master/app/src/main/res/drawable-xhdpi/screenshot_1.png)  

### **使用方式** ###  
  
~~首先通过在build.gradle文件中添加如下编译语句将YZxing-lib库添加到项目中。  
  compile 'com.yangy:YZxing-lib:1.1'~~
（或者在直接把本仓库里面的YZxing库下载下来，添加到项目中。）  
然后在点击跳转到扫码界面的点击事件中，调用如下方法：  
 Intent intent = new Intent(this, ScannerActivity.class);  
        //这里可以用intent传递一些参数，比如扫码聚焦框尺寸大小，支持的扫码类型。  
//        //设置扫码框的宽  
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 400);  
//        //设置扫码框的高  
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 400);  
//        //设置扫码框距顶部的位置  
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 100);  
//        Bundle bundle = new Bundle();  
//        //设置支持的扫码类型  
//        bundle.putSerializable(Constant.EXTRA_SCAN_CODE_TYPE, mHashMap);  
//        intent.putExtras(bundle);  
        startActivityForResult(intent, RESULT_REQUEST_CODE);  

这里可以使用intent传递一些配置参数。支持有设置扫码框的大小，及位置；设置支持的扫码类型。目前支持的自定义配置不多，后续有机会再扩充。 跳转的时候要有startActivityForResult来跳转，这样在扫码成功之后，返回的结果可以在onActivityResult方法中处理代码如下：  
@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == RESULT_OK) {  
        
            switch (requestCode) {  
            
                case RESULT_REQUEST_CODE:  
                
                    if (data == null) return;  
                    
                    String type = data.getStringExtra(Constant.EXTRA_RESULT_CODE_TYPE);  
                    
                    String content = data.getStringExtra(Constant.EXTRA_RESULT_CONTENT);  
                    
                    Toast.makeText(MainActivity.this,"codeType:" + type  
                    
                            + "-----content:" + content,Toast.LENGTH_SHORT).show();  
                            
                    break;  
                    
                default:  
                
                    break;  
                    

            }  
            
        }  
        
        super.onActivityResult(requestCode, resultCode, data);  
        
    }  
     
     
----------------------------------------  
     
     
     
## **YZxing版本更新说明** ##  

目前最新版为v2.2！！

-------------
     
   目前YZxing已经更新到了v2.1  ，更新内容有：  
     1.修改空指针导致的的闪退bug。  
     2.添加从相册获取二维码功能（用户可以选择是否使用该功能）。  
     
     
        
      首先通过在build.gradle文件中添加如下编译语句将YZxing-lib库添加到项目中。  
  compile 'com.yangy:YZxing-lib:2.1'  
（或者在直接把本仓库里面的YZxing库下载下来，添加到项目中。）   

     使用方式与1.1版本一致，注意的是如需使用**从相册获取二维码功能**则需要在intent中添加是否启用scan_from_pic，默认是FALSE：  
     **//        //设置是否启用从相册获取二维码。  
     
//        intent.putExtra(Constant.EXTRA_IS_ENABLE_SCAN_FROM_PIC,true);**     
  
### **从照片获取二维码的效果如下：** ### 

![image](https://github.com/MRYangY/YZxing/blob/master/app/src/main/res/effect-picture/scan_form_pic_%20effect.gif)


## **觉得还不错的就动动手指给个star吧(*^__^*)** ##
   

